package org.howard.edu.lsp.midterm.crccards;

/**
 * Represents a book with an ISBN, title, author, and availability status.
 * Supports borrowing, returning, and formatted string representation.
 * 
 * @author Student
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean available;

    /**
     * Constructs a new Book with the specified ISBN, title, and author.
     * New books start available by default.
     * 
     * @param isbn   the unique ISBN of the book
     * @param title  the title of the book
     * @param author the author of the book
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    /**
     * Returns the ISBN of the book.
     * 
     * @return the ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the title of the book.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of the book.
     * 
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns whether the book is currently available.
     * 
     * @return true if available, false otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Borrows the book by marking it as unavailable.
     * 
     * @throws IllegalStateException if the book is already borrowed
     */
    public void borrowBook() {
        if (!available) {
            throw new IllegalStateException("Book is already borrowed.");
        }
        available = false;
    }

    /**
     * Returns the book by marking it as available.
     * 
     * @throws IllegalStateException if the book is already available
     */
    public void returnBook() {
        if (available) {
            throw new IllegalStateException("Book is already available.");
        }
        available = true;
    }

    /**
     * Returns a string representation of the book in the format:
     * ISBN Title by Author [STATUS]
     * 
     * @return formatted string representation of the book
     */
    @Override
    public String toString() {
        String status = available ? "[AVAILABLE]" : "[BORROWED]";
        return isbn + " " + title + " by " + author + " " + status;
    }
}
