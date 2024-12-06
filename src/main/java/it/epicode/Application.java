package it.epicode;

import it.epicode.entities.*;
import it.epicode.services.LibraryService;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();


        Book book1 = new Book("ISBN0001", "The Great Gatsby", 1925, 180, "F. Scott Fitzgerald", "Novel");
        Book book2 = new Book("ISBN0002", "To Kill a Mockingbird", 1960, 281, "Harper Lee", "Novel");
        Magazine magazine1 = new Magazine("ISBN1001", "Time", 2023, 50, Periodicity.WEEKLY);
        Magazine magazine2 = new Magazine("ISBN1002", "National Geographic", 2023, 100, Periodicity.MONTHLY);

        libraryService.addCatalogItem(book1);
        libraryService.addCatalogItem(book2);
        libraryService.addCatalogItem(magazine1);
        libraryService.addCatalogItem(magazine2);

        // Search by ISBN
        CatalogItem item = libraryService.searchByISBN("ISBN0001");
        System.out.println("Search by ISBN: " + item);

        // Search by author
        System.out.println("Search by author 'Harper Lee':");
        libraryService.searchByAuthor("Harper Lee").forEach(System.out::println);

        // Add a user
        User user1 = new User(null, "John", "Doe", LocalDate.of(1990, 1, 1));
        libraryService.addUser(user1);

        // Create a loan
        Loan loan1 = new Loan(user1, book1, LocalDate.now());
        libraryService.createLoan(loan1);

        // Create an overdue loan
        Loan loan2 = new Loan(user1, book2, LocalDate.now().minusDays(40));
        libraryService.createLoan(loan2);

        // Search for items currently on loan for the user
        System.out.println("Loans for user card number " + user1.getCardNumber() + ":");
        libraryService.searchLoansByUserCardNumber(user1.getCardNumber()).forEach(System.out::println);

        // Return a loan
        libraryService.returnLoan(loan1.getId(), LocalDate.now());

        // Search for overdue loans
        System.out.println("Overdue loans:");
        libraryService.searchOverdueLoans().forEach(System.out::println);

        // Remove an item by ISBN
        libraryService.removeCatalogItemByISBN("ISBN0002");
    }
}
