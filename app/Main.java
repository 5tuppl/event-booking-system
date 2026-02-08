package app;

import domain.*;
import repository.EventRepository;
import service.BookingService;
import observer.EmailService;
import java.util.Scanner;
import java.util.List;

public class Main 
{
    private static EventRepository eventRepo = new EventRepository();
    private static EmailService emailService = new EmailService();
    private static BookingService bookingService = new BookingService(eventRepo, emailService);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        seedData(); // contains events

        System.out.println("========================================");
        System.out.println("      WELCOME TO YERATON                ");

        while (true) {
            System.out.println("1. Browse Events"); // Added '1.' for clarity
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            
            // Check if input is integer 
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
                continue;
            }

            int choice = scanner.nextInt();
            if (choice == 2) break;
            if (choice == 1) showEvents();
        }
    }

    private static void seedData() {
        // Concert и Match классы public и конструкторы соответствуют сигнатурам 
        eventRepo.save(new Concert(1, "Yenlik: World Tour", "Bangkok Arena", "2024-11-15", 5000, "Yenlik"));
        eventRepo.save(new Concert(2, "Swae Lee Live", "Almaty Arena", "2025-02-10", 12000, "Swae Lee"));
        eventRepo.save(new Match(3, "Kairat vs Real Madrid", "Central Stadium", "2025-08-20", 25000, "Kairat Almaty", "Real Madrid"));
        eventRepo.save(new Concert(4, "Ayau Concert", "Barys Arena", "2026-03-20", 3000, "Ayau"));
    }

    private static void showEvents() {
        List<Event> events = eventRepo.findAll();
        System.out.println("--- CURRENT EVENTS ---");
        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            // Polymorphism: e.getEventType(), e.getVenue(), e.getDateTime() must exist in Event class
            System.out.println((i + 1) + ". [" + e.getEventType() + "] " + e.getName() + 
                               " | Venue: " + e.getVenue() + " | Date: " + e.getDateTime());
        }

        System.out.print("Select event number to book (or 0 to go back): ");
        if (scanner.hasNextInt()) {
            int eventIndex = scanner.nextInt() - 1;
            if (eventIndex >= 0 && eventIndex < events.size()) {
                startBookingFlow(events.get(eventIndex));
            }
        } else {
            scanner.next(); 
        }
    }

    private static void startBookingFlow(Event event)
    {
        System.out.println(">>> Booking for: " + event.getName());
        
        // : Clear the buffer before using nextLine()
        scanner.nextLine(); 
        
        System.out.print("Enter your Full Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter your Email: ");
        String email = scanner.next();
        
        System.out.print("Select Row (1-20): ");
        int row = scanner.nextInt();

        if (row > 20)
        {
            System.out.print("It's more than there are rows!");
        }

        System.out.print("Select Seat (1-50): ");
        int seat = scanner.nextInt();

        if (seat > 50) 
        {
            System.out.print("It's more than there are seats!");
        }

        User user = new User((int)(Math.random() * 1000), name, email);
        Booking booking = bookingService.createBooking((int)(Math.random() * 9999), user, event.getId(), row, seat);

        if (booking != null)
        {
            System.out.println("----------------------------------------");
            System.out.println("SUCCESS! E-ticket issued for " + event.getName());
            System.out.println("Booking ID: " + booking.getBookingId());
            System.out.println("Sent to: " + user.getEmail());
            System.out.println("----------------------------------------");
        }
    }
}
