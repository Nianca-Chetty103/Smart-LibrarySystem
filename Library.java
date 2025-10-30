/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dja.project;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;

/**
 *
 * @author nianc
 */
public class Library {
   
    HashMap<String, Book> books = new HashMap<>();
    HashMap<String, Users> users = new HashMap<>();

    // Add a new book to the library
    public void addBook(String title, int copies) {
        if (books.containsKey(title)) {
            System.out.println("Book already exists: " + title);
        } else {
            books.put(title, new Book(title, copies));
            System.out.println("Book added: " + title + " (" + copies + " copies)");
        }
    }

    // Register a new user
    public void addUser(String userId) {
        if (users.containsKey(userId)) {
            System.out.println("User already exists: " + userId);
        } else {
            users.put(userId, new Users(userId));
            System.out.println("User added: " + userId);
        }
    }
     
    public List<String> borrowedBooks(String userId) {
        Users user = users.get(userId);
        List<String> borrowedList = new ArrayList<>();

        if (user == null) {
            System.out.println("User not found: " + userId);
            return borrowedList;
        }

        for (Book b : user.borrowedBooks) {
            borrowedList.add(b.title);
        }

        return borrowedList;
     }
    // Borrow a book
    public void borrowBook(String userId, String title) {
        Users user = users.get(userId);
        Book book = books.get(title);

        if (user == null) {
            System.out.println("User not found: " + userId);
            return;
        }
        if (book == null) {
            System.out.println("Book not found: " + title);
            return;
        }

        // Check if user already borrowed the book
        if (user.borrowedBooks.contains(book)) {
            System.out.println(userId + " already borrowed " + title);
            return;
        }

        // If copies are available, borrow immediately
        if (book.available > 0) {
            book.available--;
            user.borrowedBooks.add(book);
            System.out.println(userId + " borrowed " + title);
        } else {
            // Otherwise, join the waitlist if not already there
            if (book.waitlist.contains(user)) {
                System.out.println(userId + " is already on the waitlist for " + title);
            } else {
                book.waitlist.add(user);
                System.out.println(title + " unavailable. " + userId + " added to waitlist.");
            }
        }
    }

    // Return a book
    public void returnBook(String userId, String title) {
        Users user = users.get(userId);
        Book book = books.get(title);

        if (user == null) {
            System.out.println("User not found: " + userId);
            return;
        }
        if (book == null) {
            System.out.println("Book not found: " + title);
            return;
        }

        // Check if user actually borrowed the book
        if (!user.borrowedBooks.contains(book)) {
            System.out.println(userId + " did not borrow " + title);
            return;
        }

        // Remove book from user's borrowed list
        user.borrowedBooks.remove(book);

        // Handle waitlist or increase available copies
        if (book.waitlist.isEmpty()) {
            book.available++;
            System.out.println(userId + " returned " + title + ". Copy added back to library.");
        } else {
            Users nextUser = book.waitlist.poll(); // Dequeue first waiting user
            nextUser.borrowedBooks.add(book);
            System.out.println(userId + " returned " + title + ". It has been assigned to " + nextUser.userId + ".");
        }
    }

    // Display borrowed books of a user
    public void viewBorrowedBooks(String userId) {
        Users user = users.get(userId);
        if (user == null) {
            System.out.println("User not found: " + userId);
            return;
        }

        System.out.println("\nBooks borrowed by " + userId + ":");
        if (user.borrowedBooks.isEmpty()) {
            System.out.println("No books currently borrowed.");
        } else {
            for (Book b : user.borrowedBooks) {
                System.out.println("- " + b.title);
            }
        }
    }
 
    public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
        Library library = new Library();
        boolean running = true;

        System.out.println(" Welcome to the Library System!");

        while (running) {
            System.out.println("\n---- MENU ----");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Check Book Availability");
            System.out.println("6. View Borrowed Books");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter number of copies: ");
                    int copies = sc.nextInt();
                    library.addBook(title, copies);
                }
                case 2 -> {
                    System.out.print("Enter user ID: ");
                    String userId = sc.nextLine();
                    library.addUser(userId);
                }
                case 3 -> {
                    System.out.print("Enter user ID: ");
                    String userId = sc.nextLine();
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();
                    library.borrowBook(userId, title);
                }
                case 4 -> {
                    System.out.print("Enter user ID: ");
                    String userId = sc.nextLine();
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();
                    library.returnBook(userId, title);
                }
                case 5 -> {
                    System.out.print("Enter book title to check: ");
                    String title = sc.nextLine();
                    library.checkAvailability(title);
                }
                case 6 -> {
                    System.out.print("Enter user ID: ");
                    String userId = sc.nextLine();
                    library.viewBorrowedBooks(userId);
                }
                case 7 -> {
                    System.out.println("Exiting Library System. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }
}

