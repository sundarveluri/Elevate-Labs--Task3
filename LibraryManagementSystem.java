// Book.java
class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Book [ISBN=" + isbn + ", Title=" + title + ", Author=" + author + ", Available=" + isAvailable + "]";
    }
}

// User.java
class User {
    private String userId;
    private String name;
    private Book borrowedBook;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.borrowedBook = null;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Book getBorrowedBook() {
        return borrowedBook;
    }

    public void setBorrowedBook(Book book) {
        this.borrowedBook = book;
    }

    @Override
    public String toString() {
        return "User [ID=" + userId + ", Name=" + name + ", Borrowed=" + 
               (borrowedBook != null ? borrowedBook.getTitle() : "None") + "]";
    }
}

// Library.java
import java.util.ArrayList;
import java.util.List;

class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Added: " + book);
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("Added: " + user);
    }

    public boolean issueBook(String isbn, String userId) {
        Book book = findBook(isbn);
        User user = findUser(userId);

        if (book == null) {
            System.out.println("Book not found!");
            return false;
        }
        if (user == null) {
            System.out.println("User not found!");
            return false;
        }
        if (!book.isAvailable()) {
            System.out.println("Book is not available!");
            return false;
        }
        if (user.getBorrowedBook() != null) {
            System.out.println("User already has a book!");
            return false;
        }

        book.setAvailable(false);
        user.setBorrowedBook(book);
        System.out.println("Book issued successfully to " + user.getName());
        return true;
    }

    public boolean returnBook(String userId) {
        User user = findUser(userId);

        if (user == null) {
            System.out.println("User not found!");
            return false;
        }
        if (user.getBorrowedBook() == null) {
            System.out.println("User has no borrowed book!");
            return false;
        }

        Book book = user.getBorrowedBook();
        book.setAvailable(true);
        user.setBorrowedBook(null);
        System.out.println("Book returned successfully by " + user.getName());
        return true;
    }

    private Book findBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    private User findUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public void displayBooks() {
        System.out.println("\nAvailable Books:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void displayUsers() {
        System.out.println("\nRegistered Users:");
        for (User user : users) {
            System.out.println(user);
        }
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        // Adding sample books
        library.addBook(new Book("ISBN001", "The Great Gatsby", "F. Scott Fitzgerald"));
        library.addBook(new Book("ISBN002", "1984", "George Orwell"));
        library.addBook(new Book("ISBN003", "To Kill a Mockingbird", "Harper Lee"));

        // Adding sample users
        library.addUser(new User("U001", "Alice Smith"));
        library.addUser(new User("U002", "Bob Johnson"));

        // Display initial state
        library.displayBooks();
        library.displayUsers();

        // Testing book issue
        System.out.println("\nIssuing book...");
        library.issueBook("ISBN001", "U001");
        library.displayBooks();
        library.displayUsers();

        // Testing book return
        System.out.println("\nReturning book...");
        library.returnBook("U001");
        library.displayBooks();
        library.displayUsers();
    }
}