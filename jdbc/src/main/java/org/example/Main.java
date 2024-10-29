package org.example;

import org.example.dao.*;
import org.example.model.*;
import org.example.service.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static final UserDao userDao = new UserDao();
    private static final CardDao cardDao = new CardDao();
    private static final UserService userService = new UserService(userDao);
    private static final CardService cardService = new CardService(cardDao);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the Application!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.println("Register a new user");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        boolean active = true;

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setEmail(email);
        user.setActive(active);
        user.setCreatedDate(getCurrentDate());

        try {
            User registeredUser = userService.signUp(user);
            System.out.println("Registration successful! User ID: " + registeredUser.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static String getCurrentDate() {
        return LocalDate.now().toString();
    }

    private static void loginUser() {
        System.out.println("Login");
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);

        try {
            User loggedInUser = userService.signIn(user);
            System.out.println("Login successful! Welcome " + loggedInUser.getName());
            manageCards(loggedInUser.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private static void manageCards(int userId) {
        while (true) {
            System.out.println("Card Management");
            System.out.println("1. Add Card");
            System.out.println("2. View My Cards");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCard(userId);
                    break;
                case 2:
                    viewMyCards(userId);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addCard(int userId) {
        System.out.println("Add a new card");
        System.out.print("Enter Card Number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Enter Expiration Date (MM/YY): ");
        String expirationDate = scanner.nextLine();
        System.out.print("Enter CVV: ");
        String cvv = scanner.nextLine();

        try {
            Card newCard = cardService.addCard(userId, cardNumber, expirationDate, cvv);
            System.out.println("Card added successfully! Card ID: " + newCard.getId());
        } catch (SQLException e) {
            System.out.println("Failed to add card: " + e.getMessage());
        }
    }

    private static void viewMyCards(int userId) {
        List<Card> cards = cardService.getMyCards(userId);
        if (cards.isEmpty()) {
            System.out.println("No cards found.");
        } else {
            System.out.println("My Cards:");
            for (Card card : cards) {
                System.out.println("Card ID: " + card.getId() + ", Card Number: " + card.getCardNumber() + ", Expiration Date: " + card.getExpirationDate());
            }
        }
    }
}
