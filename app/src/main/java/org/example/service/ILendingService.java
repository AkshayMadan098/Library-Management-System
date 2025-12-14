package org.example.service;

import org.example.domain.BorrowingTransaction;

import java.util.List;

public interface PLendingService {
    void checkoutBook(String patronId, String isbn) throws Exception;

    void returnBook(String patronId, String copyId) throws Exception;

    List<BorrowingTransaction> getBorrowingHistory(String patronId);
}