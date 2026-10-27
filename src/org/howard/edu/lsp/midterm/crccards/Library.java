package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a library system that maintains a collection of books using a HashMap,
 * coordinates borrowing/returning operations, and enforces business rules and limits.
 * 
 * @author Student
 */
public class Library {
    private Map<String, Book> books;

    /**
     * Constructs a new, empty Library.
     */
    public Library() {
        this.books = new HashMap<>();
    }

    /**
     * Adds a book to the library. Duplicate ISBNs are prohibited and preserve the original book.
     * 
     * @param book the book to add
     * @throws IllegalArgumentException if a book with the same ISBN already exists
     */
    public void addBook(Book book) {
        if (book != null && books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("A book with ISBN " + book.getIsbn() + " already exists.");
        }
        if (book != null) {
            books.put(book.getIsbn(), book);
        }
    }

    /**
     * Finds and returns a book by its ISBN.
     * 
     * @param isbn the ISBN to search for
     * @return the matching Book instance, or null if absent
     */
    public Book findBook(String isbn) {
        return books.get(isbn);
    }

    /**
     * Borrows a book specified by its ISBN. Enforces existence, availability, and a 3-book limit.
     * 
     * @param isbn the ISBN of the book to borrow
     * @throws IllegalArgumentException if the ISBN is not found in the library
     * @throws IllegalStateException    if the book is already borrowed or the 3-book limit is reached
     */
    public void borrowBook(String isbn) {
        Book book = findBook(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book with ISBN " + isbn + " not found in library.");
        }
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book with ISBN " + isbn + " is already borrowed.");
        }
        if (getBorrowedBookCount() >= 3) {
            throw new IllegalStateException("Maximum borrowing limit of 3 books has been reached.");
        }
        book.borrowBook();
    }

    /**
     * Returns a book specified by its ISBN. Enforces existence and availability status.
     * 
     * @param isbn the ISBN of the book to return
     * @throws IllegalArgumentException if the ISBN is not found in the library
     * @throws IllegalStateException    if the book is already available
     */
    public void returnBook(String isbn) {
        Book book = findBook(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book with ISBN " + isbn + " not found in library.");
        }
        if (book.isAvailable()) {
            throw new IllegalStateException("Book with ISBN " + book.getIsbn() + " is already available.");
        }
        book.returnBook();
    }

    /**
     * Returns a list of all currently available books in the library.
     * 
     * @return a list of available books, or an empty list if none
     */
    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    /**
     * Returns a list of all books matching the specified author exactly (case-sensitive).
     * 
     * @param author the author name to search for
     * @return a list of matching books (including borrowed ones), or an empty list if none
     */
    public List<Book> getBooksByAuthor(String author) {
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equals(author)) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }

    /**
     * Returns all books in the library sorted by ISBN in ascending lexicographical order.
     * 
     * @return a sorted list of all books, or an empty list if the library is empty
     */
    public List<Book> getBooksSortedByIsbn() {
        List<Book> allBooks = new ArrayList<>(books.values());
        allBooks.sort(Comparator.comparing(Book::getIsbn));
        return allBooks;
    }

    /**
     * Returns the current number of borrowed books in the library.
     * 
     * @return the count of borrowed books
     */
    public int getBorrowedBookCount() {
        int count = 0;
        for (Book book : books.values()) {
            if (!book.isAvailable()) {
                count++;
            }
        }
        return count;
    }
}
