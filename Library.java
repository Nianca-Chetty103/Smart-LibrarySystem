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
        Library library = new Library();

        // Add some books
        library.addBook("Data Structures", 2);
        library.addBook("Operating Systems", 1);

        // Add some users
        library.addUser("U1");
        library.addUser("U2");
        library.addUser("U3");

        // Borrow operations
        library.borrowBook("U1", "Data Structures");
        library.borrowBook("U2", "Data Structures");
        library.borrowBook("U3", "Data Structures"); // Waitlist

        // Return operation (U1 returns the book)
        library.returnBook("U1", "Data Structures"); // Should assign to U3

        // View borrowed books
        library.viewBorrowedBooks("U3");
    }
}
