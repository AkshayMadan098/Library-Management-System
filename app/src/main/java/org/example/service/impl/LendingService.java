package org.example.service.impl;

import org.example.domain.BookCopy;
import org.example.domain.BorrowingTransaction;
import org.example.domain.Patron;
import org.example.domain.Status;
import org.example.service.IBookCatalog;
import org.example.service.PLendingService;
import org.example.service.PPatronRegistry;
import org.example.util.Logger;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class LendingService implements PLendingService {
    private final IBookCatalog bookCatalog;
    private final PPatronRegistry patronRegistry;
    private final List<BorrowingTransaction> transactions = new ArrayList<>();
    private final Logger logger;

    public LendingService(IBookCatalog bookCatalog, PPatronRegistry patronRegistry, Logger logger) {
        this.bookCatalog = bookCatalog;
        this.patronRegistry = patronRegistry;
        this.logger = logger;
    }

    @Override
    public void checkoutBook(String patronId, String isbn) throws Exception {
        Patron patron = patronRegistry.findPatronById(patronId);
        BookCopy copy = bookCatalog.getAvailableCopy(isbn);
        copy.setStatus(Status.BORROWED);
        transactions.add(new BorrowingTransaction(patron, copy, new Date()));
        logger.log("Book checkout: " + copy.getBook().getTitle() + " (Copy ID: " +
                copy.getCopyId() + ") borrowed by " + patron.getName(), Level.INFO);
    }

    @Override
    public void returnBook(String patronId, String copyId) throws Exception {
        Patron patron = patronRegistry.findPatronById(patronId);
        BorrowingTransaction tx = transactions.stream()
                .filter(t -> t.getPatron().getId().equals(patronId) &&
                        t.getBookCopy().getCopyId().equals(copyId) &&
                        t.isBorrowed())
                .findFirst()
                .orElseThrow(() -> new Exception("No active transaction for copy " + copyId));
        tx.setReturnDate(new Date());
        tx.getBookCopy().setStatus(Status.AVAILABLE);
        logger.log("Book returned: " + tx.getBookCopy().getBook().getTitle() +
                " (Copy ID: " + copyId + ") by " + patron.getName(), Level.INFO);
    }

    @Override
    public List<BorrowingTransaction> getBorrowingHistory(String patronId) {
        return transactions.stream()
                .filter(t -> t.getPatron().getId().equals(patronId))
                .collect(Collectors.toList());
    }
}