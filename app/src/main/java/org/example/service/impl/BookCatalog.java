package org.example.service.impl;

import org.example.domain.Book;
import org.example.domain.BookCopy;
import org.example.domain.Status;
import org.example.exception.BookNotAvailableException;
import org.example.exception.BookNotFoundException;
import org.example.service.IBookCatalog;
import org.example.util.Logger;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class BookCatalog implements IBookCatalog {
    private final Map<String, Book> booksByIsbn = new HashMap<>();
    private final Map<String, List<BookCopy>> copiesByIsbn = new HashMap<>();
    private final Logger logger;

    public BookCatalog(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void addBook(Book book) throws BookNotFoundException {
        if (booksByIsbn.containsKey(book.getIsbn())) {
            throw new BookNotFoundException("Book with ISBN " + book.getIsbn() + " already exists.");
        }
        booksByIsbn.put(book.getIsbn(), book);
        copiesByIsbn.put(book.getIsbn(), new ArrayList<>());
        logger.log("Book added: " + book, Level.INFO);
    }

    @Override
    public void removeBook(String isbn) throws BookNotFoundException {
        if (!booksByIsbn.containsKey(isbn)) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
        }
        booksByIsbn.remove(isbn);
        copiesByIsbn.remove(isbn);
        logger.log("Book removed: " + isbn, Level.INFO);
    }

    @Override
    public void addBookCopy(String isbn, String copyId) throws BookNotFoundException {
        Book book = findBookByIsbn(isbn);
        List<BookCopy> copies = copiesByIsbn.get(isbn);
        if (copies.stream().anyMatch(c -> c.getCopyId().equals(copyId))) {
            throw new BookNotFoundException("Copy ID " + copyId + " already exists.");
        }
        copies.add(new BookCopy(copyId, book));
        logger.log("Added copy " + copyId + " for book " + book.getTitle(), Level.INFO);
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        return booksByIsbn.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        return booksByIsbn.values().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Book findBookByIsbn(String isbn) throws BookNotFoundException {
        Book book = booksByIsbn.get(isbn);
        if (book == null)
            throw new BookNotFoundException("ISBN " + isbn + " not found.");
        return book;
    }

    @Override
    public BookCopy getAvailableCopy(String isbn) throws BookNotAvailableException {
        List<BookCopy> copies = copiesByIsbn.get(isbn);
        if (copies == null || copies.isEmpty()) {
            throw new BookNotAvailableException("No copies for ISBN " + isbn);
        }
        return copies.stream()
                .filter(c -> c.getStatus() == Status.AVAILABLE)
                .findFirst()
                .orElseThrow(() -> new BookNotAvailableException("No available copies for ISBN " + isbn));
    }
}