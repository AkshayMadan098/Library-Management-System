package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookCopy;
import org.example.exception.BookNotAvailableException;
import org.example.exception.BookNotFoundException;

import java.util.List;

public interface IBookCatalog {
    void addBook(Book book) throws BookNotFoundException;

    void removeBook(String isbn) throws BookNotFoundException;

    void addBookCopy(String isbn, String copyId) throws BookNotFoundException;

    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByAuthor(String author);

    Book findBookByIsbn(String isbn) throws BookNotFoundException;

    BookCopy getAvailableCopy(String isbn) throws BookNotAvailableException;
}