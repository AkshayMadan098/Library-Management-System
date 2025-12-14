package org.example.domain;

import java.util.Date;

public class BorrowingTransaction {
    private final Patron patron;
    private final BookCopy bookCopy;
    private final Date borrowDate;
    private Date returnDate;

    public BorrowingTransaction(Patron patron, BookCopy bookCopy, Date borrowDate) {
        this.patron = patron;
        this.bookCopy = bookCopy;
        this.borrowDate = borrowDate;
        this.returnDate = null;
    }

    public Patron getPatron() {
        return patron;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isBorrowed() {
        return returnDate == null;
    }
}