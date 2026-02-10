package app;

import domain.*;
import repository.*;
import service.*;
import java.util.Scanner;
import java.util.List;

public class Main {


    private static IRepository<User> userRepo = new UserRepository();
    private static IRepository<Event> eventRepo = new EventRepository();

    
    private static AuthService authService = new AuthService(userRepo);
    private static BookingService bookingService = new BookingService(eventRepo);

    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        seedInitialData();

        while (true) {
            if (currentUser == null) {
                showAuthMenu();
            } else {
                if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
                    showAdminMenu();
                } else {
                    showUserMenu();
                }
            }
        }
    }

    private static void seedInitialData() {
        // events (у Concert 6 аргумента)
        eventRepo.add(new Concert(1, "Yenlik Concert", "Barys Arena", "2024-12-01", 1000, "Yenlik"));
        eventRepo.add(new Concert(2, "Ayau Live", "Almaty Stadium", "2026-12-15", 5000, "Ayau"));
    }

    // AUTHENTICATION MENU 
    private static void showAuthMenu() {
        System.out.println("\n=== EVENT BOOKING SYSTEM ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Select: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                System.out.print("Username: ");
                String u = scanner.nextLine();
                System.out.print("Password: ");
                String p = scanner.nextLine();
                currentUser = authService.login(u, p);
                if (currentUser == null) System.out.println("Invalid credentials.");
                break;
            case "2":
                System.out.print("Username: ");
                String regU = scanner.nextLine();
                System.out.print("Password: ");
                String regP = scanner.nextLine();
                System.out.print("Email: ");
                String regE = scanner.nextLine();
                if (authService.register(regU, regP, regE)) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("User already exists.");
                }
                break;
            case "3":
                System.exit(0);
        }
    }

    //  ADMIN PERSPECTIVE
    private static void showAdminMenu() {
        System.out.println("\n[ADMIN DASHBOARD]");
        System.out.println("1. Create Concert");
        System.out.println("2. View All Events");
        System.out.println("3. Delete Event");
        System.out.println("4. Logout");
        System.out.print("Select Action: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                System.out.print("ID: ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Venue: ");
                String venue = scanner.nextLine();
                System.out.print("Date: ");
                String date = scanner.nextLine();
                System.out.print("Seats: ");
                int seats = Integer.parseInt(scanner.nextLine());
                System.out.print("Artist: ");
                String artist = scanner.nextLine();
                
                eventRepo.add(new Concert(id, name, venue, date, seats, artist));
                System.out.println("Concert created successfully.");
                break;
            case "2":
                List<Event> events = eventRepo.getAll();
                System.out.println("\n--- All Events ---");
                events.forEach(e -> System.out.println(e.getId() + ": " + e.getDetails()));
                break;
            case "3":
                System.out.print("Enter ID to delete: ");
                int delId = Integer.parseInt(scanner.nextLine());
                if (eventRepo.delete(delId)) System.out.println("Deleted.");
                else System.out.println("Not found.");
                break;
            case "4":
                currentUser = null;
                break;
        }
    }

    // USER PERSPECTIVE 
    private static void showUserMenu() {
        System.out.println("\n[USER DASHBOARD]");
        System.out.println("1. View Events");
        System.out.println("2. Book a Ticket");
        System.out.println("3. Logout");
        System.out.print("Select Action: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                eventRepo.getAll().forEach(e -> 
                    System.out.println(e.getId() + ": " + e.getDetails() + " [Seats: " + e.getTotalSeats() + "]"));
                break;
            case "2":
                System.out.print("Enter Event ID to book: ");
                int id = Integer.parseInt(scanner.nextLine());
                if (bookingService.bookEvent(id)) {
                    System.out.println("Booking successful!");
                } else {
                    System.out.println("Booking failed (Sold out or invalid ID).");
                }
                break;
            case "3":
                currentUser = null;
                break;
        }
    }
}
