package org.example.service;

import org.example.domain.Patron;
import org.example.exception.PatronNotFoundException;

public interface PPatronRegistry {
    void addPatron(Patron patron);

    Patron findPatronById(String patronId) throws PatronNotFoundException;
}