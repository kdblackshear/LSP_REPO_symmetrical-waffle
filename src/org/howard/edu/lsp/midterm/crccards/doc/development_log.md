Conversation with Gemini AI:

ME: Can you help me with this implementation, explaining you solution: [Question 1 description and Driver code]

GEMINI: Here is the complete implementation of the Book and Library classes according to your specifications and CRC card design.

Both classes belong to the package org.howard.edu.lsp.midterm.crccards and include comprehensive Javadoc documentation as required.

Part 1: [Book.java code]

Part 2: [Library.java code]

Solution Explanation & Design Decisions
Internal Data Structure (Library):

We utilized a HashMap<String, Book> where the key is the book's unique isbn and the value is the Book object. This provides 
O(1) time complexity for lookups (findBook), duplication checks (addBook), and retrieval by ISBN.

Encapsulation & State Validation:

Book State Control: The available boolean field is private. State mutations (borrowBook() and returnBook()) validate the current state first, throwing an IllegalStateException if an invalid transition is attempted (e.g., borrowing an already borrowed book).

Pre-validation in Library: In Library.borrowBook() and Library.returnBook(), all error checks (missing ISBN, already borrowed/available, and the 3-book limit) are performed before invoking state changes on the Book object. This ensures that failed operations leave the system state and counts completely unchanged.

Sorting and Filtering:

getBooksSortedByIsbn() copies the map values into an ArrayList and sorts them using Java's Comparator.comparing(Book::getIsbn) in ascending lexicographical order without altering the underlying collection.

getBooksByAuthor() performs an exact, case-sensitive match as required, including both available and borrowed books.

ME: Can you explain crc design to me?

GEMINI: CRC stands for Class-Responsibility-Collaborator. It is a classic object-oriented design technique created by Kent Beck and Ward Cunningham in the late 1980s.

Instead of jumping straight into writing complex code or drawing rigid UML diagrams, CRC design uses simple index cards (physical or digital) to model how a software system's components will behave and interact with one another.

Anatomy of a CRC Card
A CRC card is typically visualized as an index card divided into three sections:

Class Name
Responsibilities
What actions the class performs or what data it manages.
Let's look at how this breaks down using the Library example you just worked on:

Class Name: Book

Responsibilities: Store book information (ISBN, title, author); maintain availability status; validate borrowing/returning.

Collaborators: None (It stands alone; it doesn't need to know about other classes).

Class Name: Library

Responsibilities: Maintain a collection of books; add and find books; coordinate borrowing/returning; enforce the 3-book borrowing limit.

Collaborators: Book (It relies on Book instances to manage individual book states).

The Three Components Explained
1. Class Name
What it is: The noun representing a concept, entity, or service in your problem domain (e.g., Book, Library, User, Transaction).

Best Practice: Name it after a single, well-defined concept. If a class name uses "and" (like BookAndBorrowerManager), it usually means it's doing too much and should be split up.

2. Responsibilities
What it is: The obligations of the class—essentially, what the class knows (its data) and what it does (its methods/behaviors).

Focus: Expressed as short, action-oriented phrases (e.g., "validate borrowing" or "calculate total price").

Design Rule (Single Responsibility Principle): A class should have a clear, focused set of responsibilities. If a card gets too crowded, it's a sign that the class is taking on too much work and needs to delegate.

3. Collaborators
What it is: Other classes that this class must talk to or work with to fulfill its responsibilities.

Design Insight: If a class has a long list of collaborators, it might be overly dependent on the rest of the system (tight coupling).

Why Use CRC Design?
Behavior-First Thinking: It forces you to think about what objects do rather than just what data they hold.

Low Barrier to Entry: Because you are just writing on cards (or simple text blocks), you can easily move them around, throw them away, or rewrite them during brainstorming sessions without getting bogged down by syntax.

Encourages Good OO Principles: It naturally guides you toward Encapsulation (keeping data and the operations on that data inside the same class) and Delegation (having objects ask other objects to do work rather than doing everything themselves).

How CRC Cards are Used in Practice (The Walkthrough)
Teams typically use CRC cards in a collaborative workshop style:

Brainstorming: Identify the key actors and entities in the problem domain and write them on cards.

Walkthrough Scenarios: Take a real-world use case (e.g., "A user wants to borrow a book"). Physically move the cards or trace the conversation:

“Okay, the Library receives a request to borrow ISBN 'B101'.”

“Library looks up the book, collaborating with Book to check its availability.”

“Library checks its internal counter to ensure the limit isn't exceeded.”

“Library tells the Book to change its state to borrowed.”

Refining: If a card cannot fulfill a responsibility on its own, it discovers a need for a new collaborator or a shift in responsibilities. Once the card design feels solid, translating it into actual Java classes (like the implementation we just built) becomes much easier and cleaner!

ME: What do the interactions between the three files show?

GEMINI: The interactions between the three files—Driver.java, Library.java, and Book.java—demonstrate key object-oriented design principles in action. Together, they show how a well-structured application separates concerns, delegates responsibilities, and protects its data.

1. Separation of Concerns (The Three-Tiered Hierarchy)
Each file has a distinct, non-overlapping job, showing a clean architectural hierarchy:

Driver.java (The Client / Test Runner): It doesn't contain business logic or data structures. Instead, it acts as an external user or testing suite that drives the system, calling public methods and asserting expected outcomes.

Library.java (The Coordinator / Manager): It handles system-wide policies, collections (HashMap), and constraints (like the 3-book borrowing limit and duplicate ISBN checks). It acts as the gateway for library operations.

Book.java (The Domain Model): It focuses entirely on a single entity's identity and state (isbn, title, author, available).

2. Delegation Pattern (Library 
→ Book)
When the Driver tells the Library to borrow or return a book (e.g., library.borrowBook("B101")), the interaction highlights delegation:

The Library locates the correct Book instance using its internal map.

After verifying system-level rules (like the 3-book limit), the Library does not change the book's availability field directly.

Instead, it delegates the task downward by calling book.borrowBook(). This shows how a manager object coordinates workflow while letting individual objects manage their own internal state.

3. Strict Encapsulation and State Protection
The interactions enforce strict data hiding across boundaries:

No Direct State Manipulation: The Driver cannot write book.available = false, and the Library cannot modify a book's availability directly either.

Controlled Access: State changes can only happen through specific, validated pathways (Book's public methods). If an invalid operation is attempted (like the Driver trying to force a 4th book borrow), the exception travels safely back up through the Library to the Driver, leaving the system state intact.

4. Standalone Reusability vs. System Context
At the very end of Driver.java, the code tests a Book in complete isolation (Book standalone = new Book(...)) without putting it into a Library.

This interaction proves that a Book is a self-contained component. It can function independently, yet it seamlessly plugs into a larger container (Library) when needed, demonstrating high cohesion and low coupling.

ME: What is a three book borrowing limit?

GEMINI: In the context of this Library Lending System, the three-book borrowing limit is a business rule (or system constraint) that restricts the library from having more than three books checked out (borrowed) at the same time.

Here is how it works and why it is designed this way:

1. How It Operates in the Code
The Check: Every time library.borrowBook(isbn) is called, the Library first checks its internal state using getBorrowedBookCount().

The Threshold: If the count is already at 3, attempting to borrow another book violates the rule.

The Exception: The library throws an IllegalStateException, rejecting the request.

State Protection: Because the check happens before the book's state is changed, failing this limit leaves the library and the book completely unchanged (the 4th book stays available, and the count remains at 3).

2. Real-World Analogy
Think of it like a public library card that has a strict checkout quota. Even if the library has thousands of books available on the shelves, your account (or in this case, the system's active loan capacity) caps your active checkouts at three items until you return something.

Once you return a book, the active count drops below 3, freeing up a "slot" so you can borrow another book again.

******************************************************************************
Gemini AI aided me in my implementation for question 1 by generating code suggestions for me to try, while also explaining what and why it suggested the code it did. This not only guided me to the correct solution, but also help me to learn more along the way. 
