package org.example.service.impl;

import org.example.domain.Patron;
import org.example.exception.PatronNotFoundException;
import org.example.service.PPatronRegistry;
import org.example.util.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class PatronRegistry implements PPatronRegistry {
    private final Map<String, Patron> patronsById = new HashMap<>();
    private final Logger logger;

    public PatronRegistry(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void addPatron(Patron patron) {
        patronsById.putIfAbsent(patron.getId(), patron);
        logger.log("Patron added: " + patron.getName() + " (ID: " + patron.getId() + ")", Level.INFO);
    }

    @Override
    public Patron findPatronById(String patronId) throws PatronNotFoundException {
        Patron patron = patronsById.get(patronId);
        if (patron == null)
            throw new PatronNotFoundException("Patron ID " + patronId + " not found.");
        return patron;
    }
}