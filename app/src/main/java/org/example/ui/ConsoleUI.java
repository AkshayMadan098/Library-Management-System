package org.example.ui;

import org.example.service.PLibraryFacade;

import java.util.Scanner;

public class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private final PLibraryFacade library;

    public ConsoleUI(PLibraryFacade library) {
        this.library = library;
    }

    public void start() {
        System.out.println("Library Management System");
        addSampleData();
        while (true) {
            System.out.println("\n============ MENU ============");
            System.out.println("1. Add Book");
            System.out.println("2. Add Book Copy");
            System.out.println("3. Add Patron");
            System.out.println("4. Search Books");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Remove Book");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        addBookCopy();
                        break;
                    case 3:
                        addPatron();
                        break;
                    case 4:
                        searchBooks();
                        break;
                    case 5:
                        borrowBook();
                        break;
                    case 6:
                        returnBook();
                        break;
                    case 7:
                        removeBook();
                        break;
                    case 8:
                        System.out.println("Thank you for using the Library Management System!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addSampleData() {

        library.addBook("The White Tiger", "Aravind Adiga", "9788172238476", 2008);
        library.addBookCopy("9788172238476", "copy1");

        library.addBook("The God of Small Things", "Arundhati Roy", "9780006550686", 1997);
        library.addBookCopy("9780006550686", "copy1");

        library.addBook("Train to Pakistan", "Khushwant Singh", "9780143065882", 1956);
        library.addBookCopy("9780143065882", "copy1");

        library.addBook("A Suitable Boy", "Vikram Seth", "9780060175634", 1993);
        library.addBookCopy("9780060175634", "copy1");

        library.addBook("Midnight's Children", "Salman Rushdie", "9780099582076", 1981);
        library.addBookCopy("9780099582076", "copy1");

        library.addPatron("Rajesh Sharma", "P001", "rajesh.sharma@example.com");
        library.addPatron("Priya Patel", "P002", "priya.patel@example.com");
        library.addPatron("Ananya Singh", "P003", "ananya.singh@example.com");
        library.addPatron("Vikram Malhotra", "P004", "vikram.malhotra@example.com");
    }

    private void addBook() {
        System.out.println("\n==== Add New Book ====");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Publication Year: ");
        int year = Integer.parseInt(scanner.nextLine());

        library.addBook(title, author, isbn, year);
        System.out.println("Book added successfully!");
    }

    private void addBookCopy() {
        System.out.println("\n==== Add Book Copy ====");
        System.out.print("ISBN of existing book: ");
        String isbn = scanner.nextLine();
        System.out.print("Copy ID: ");
        String copyId = scanner.nextLine();

        library.addBookCopy(isbn, copyId);
        System.out.println("Book copy added successfully!");
    }

    private void addPatron() {
        System.out.println("\n==== Add New Patron ====");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Contact Info: ");
        String contact = scanner.nextLine();

        library.addPatron(name, id, contact);
        System.out.println("Patron added successfully!");
    }

    private void searchBooks() {
        System.out.println("\n==== Search Books ====");
        System.out.print("Search by (title/author/isbn): ");
        String type = scanner.nextLine();
        System.out.print("Search term: ");
        String query = scanner.nextLine();

        System.out.println("\nSearch Results:");
        library.searchBooks(query, type).forEach(System.out::println);
    }

    private void borrowBook() throws Exception {
        System.out.println("\n==== Borrow Book ====");
        System.out.print("Patron ID: ");
        String patronId = scanner.nextLine();
        System.out.print("Book ISBN: ");
        String isbn = scanner.nextLine();

        library.borrowBook(patronId, isbn);
        System.out.println("Book borrowed successfully!");
    }

    private void returnBook() throws Exception {
        System.out.println("\n==== Return Book ====");
        System.out.print("Patron ID: ");
        String patronId = scanner.nextLine();
        System.out.print("Book Copy ID: ");
        String copyId = scanner.nextLine();

        library.returnBook(patronId, copyId);
        System.out.println("Book returned successfully!");
    }

    private void removeBook() {
        System.out.println("\n==== Remove Book ====");
        System.out.print("ISBN of book to remove: ");
        String isbn = scanner.nextLine();

        library.removeBook(isbn);
        System.out.println("Book removed successfully!");
    }
}