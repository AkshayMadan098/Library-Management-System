package org.example.service.impl;

import org.example.domain.Book;
import org.example.exception.BookNotFoundException;
import org.example.service.IBookCatalog;
import org.example.service.PLendingService;
import org.example.service.PLibraryFacade;
import org.example.service.PPatronRegistry;
import org.example.util.Logger;
import org.example.domain.Patron;

import java.util.List;
import java.util.logging.Level;

public class LibraryFacade implements PLibraryFacade {
    private final IBookCatalog bookCatalog;
    private final PPatronRegistry patronRegistry;
    private final PLendingService lendingService;
    private final Logger logger;

    public LibraryFacade(IBookCatalog bookCatalog, PPatronRegistry patronRegistry,
            PLendingService lendingService, Logger logger) {
        this.bookCatalog = bookCatalog;
        this.patronRegistry = patronRegistry;
        this.lendingService = lendingService;
        this.logger = logger;
    }

    @Override
    public void addBook(String title, String author, String isbn, int publicationYear) {
        try {
            Book book = new Book(title, author, isbn, publicationYear);
            bookCatalog.addBook(book);
        } catch (BookNotFoundException e) {
            logger.log(e.getMessage(), Level.WARNING);
        }
    }

    @Override
    public void addBookCopy(String isbn, String copyId) {
        try {
            bookCatalog.addBookCopy(isbn, copyId);
        } catch (BookNotFoundException e) {
            logger.log(e.getMessage(), Level.WARNING);
        }
    }

    @Override
    public void addPatron(String name, String id, String contactInfo) {
        Patron patron = new Patron(name, id, contactInfo);
        patronRegistry.addPatron(patron);
    }

    @Override
    public List<Book> searchBooks(String query, String searchType) {
        switch (searchType.toLowerCase()) {
            case "title":
                return bookCatalog.findBooksByTitle(query);
            case "author":
                return bookCatalog.findBooksByAuthor(query);
            case "isbn":
                try {
                    return List.of(bookCatalog.findBookByIsbn(query));
                } catch (BookNotFoundException e) {
                    logger.log(e.getMessage(), Level.WARNING);
                    return List.of();
                }
            default:
                logger.log("Invalid search type: " + searchType, Level.WARNING);
                return List.of();
        }
    }

    @Override
    public void borrowBook(String patronId, String isbn) throws Exception {
        try {
            lendingService.checkoutBook(patronId, isbn);
        } catch (Exception e) {
            logger.log("Failed to borrow book: " + e.getMessage(), Level.WARNING);
            throw e;
        }
    }

    @Override
    public void returnBook(String patronId, String copyId) throws Exception {
        try {
            lendingService.returnBook(patronId, copyId);
        } catch (Exception e) {
            logger.log("Failed to return book: " + e.getMessage(), Level.WARNING);
            throw e;
        }
    }

    @Override
    public void removeBook(String isbn) {
        try {
            bookCatalog.removeBook(isbn);
        } catch (BookNotFoundException e) {
            logger.log(e.getMessage(), Level.WARNING);
        }
    }
}