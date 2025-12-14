package org.example;

import org.example.service.IBookCatalog;
import org.example.service.PLendingService;
import org.example.service.PLibraryFacade;
import org.example.service.PPatronRegistry;
import org.example.service.impl.BookCatalog;
import org.example.service.impl.LendingService;
import org.example.service.impl.LibraryFacade;
import org.example.service.impl.PatronRegistry;
import org.example.ui.ConsoleUI;
import org.example.util.ConsoleLogger;
import org.example.util.Logger;

public class App {
    public static void main(String[] args) {
        Logger logger = new ConsoleLogger();
        IBookCatalog bookCatalog = new BookCatalog(logger);
        PPatronRegistry patronRegistry = new PatronRegistry(logger);
        PLendingService lendingService = new LendingService(bookCatalog, patronRegistry, logger);
        PLibraryFacade library = new LibraryFacade(bookCatalog, patronRegistry, lendingService, logger);
        ConsoleUI ui = new ConsoleUI(library);
        ui.start();
    }
}
