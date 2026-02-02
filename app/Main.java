package app;

import domain.*;
import repository.EventRepository;
import service.BookingService;
import observer.EmailService;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static EventRepository eventRepo = new EventRepository();
    private static EmailService emailService = new EmailService();
    private static BookingService bookingService = new BookingService(eventRepo, emailService);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedData(); // This now contains your new events

        System.out.println("========================================");
        System.out.println("      WELCOME TO TICKETON CLONE         ");
        System.out.println("========================================");

        while (true) {
            System.out.println("\n1. Browse Events");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            if (choice == 2) break;
            if (choice == 1) showEvents();
        }
    }

    private static void seedData() {
        // 1. Yenlik concert in Thailand
        eventRepo.save(new Concert(1, "Yenlik: World Tour", "Bangkok Arena", "2024-11-15", 5000, "Yenlik"));

        // 2. Swae Lee concert in Almaty
        eventRepo.save(new Concert(2, "Swae Lee Live", "Almaty Arena", "2025-02-10", 12000, "Swae Lee"));

        // 3. Kairat - Real Madrid match
        // Using the new Match class
        eventRepo.save(new Match(3, "Kairat vs Real Madrid", "Central Stadium", "2025-08-20", 25000, "Kairat Almaty", "Real Madrid"));
    }

    private static void showEvents() {
        List<Event> events = eventRepo.findAll();
        System.out.println("\n--- CURRENT EVENTS ---");
        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            // Polymorphism in action: getEventType() returns "Concert" or "Sporting Match" automatically
            System.out.println((i + 1) + ". [" + e.getEventType() + "] " + e.getName() + 
                               " | Venue: " + e.getVenue() + " | Date: " + e.getDateTime());
        }

        System.out.print("\nSelect event number to book (or 0 to go back): ");
        int eventIndex = scanner.nextInt() - 1;

        if (eventIndex >= 0 && eventIndex < events.size()) {
            startBookingFlow(events.get(eventIndex));
        }
    }

    private static void startBookingFlow(Event event) {
        System.out.println("\n>>> Booking for: " + event.getName());
        System.out.print("Enter your Full Name: ");
        scanner.nextLine(); 
        String name = scanner.nextLine();
        System.out.print("Enter your Email: ");
        String email = scanner.next();
        
        System.out.print("Select Row (1-20): ");
        int row = scanner.nextInt();
        System.out.print("Select Seat (1-50): ");
        int seat = scanner.nextInt();

        User user = new User((int)(Math.random() * 1000), name, email);
        Booking booking = bookingService.createBooking((int)(Math.random() * 9999), user, event.getId(), row, seat);

        if (booking != null) {
            System.out.println("\n----------------------------------------");
            System.out.println("SUCCESS! E-ticket issued for " + event.getName());
            System.out.println("Booking ID: " + booking.getBookingId());
            System.out.println("Sent to: " + user.getEmail());
            System.out.println("----------------------------------------");
        }
    }
}
