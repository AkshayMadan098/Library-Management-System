package org.example.service;

import org.example.domain.Book;

import java.util.List;

public interface PLibraryFacade {
    void addBook(String title, String author, String isbn, int publicationYear);

    void addBookCopy(String isbn, String copyId);

    void addPatron(String name, String id, String contactInfo);

    List<Book> searchBooks(String query, String searchType);

    void borrowBook(String patronId, String isbn) throws Exception;

    void returnBook(String patronId, String copyId) throws Exception;

    void removeBook(String isbn);
}