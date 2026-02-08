package app;

import domain.Event;
import domain.Concert;
import domain.User;
import repository.UserRepository;
import repository.EventRepository;
import java.util.Scanner;
import java.util.List;

public class Main {
    
    private static UserRepository userRepo = new UserRepository();
    private static EventRepository eventRepo = new EventRepository();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null; 

    public static void main(String[] args)
    {
        eventRepo.createEvent(new Concert(1, "Yenlik Concert", "Barys Arena", "2024-12-01", 1000, "Yenlik"));
        eventRepo.createEvent(new Concert(2, "Ayau Live", "Almaty Stadium", "2026-12-15", 5000, "Ayau"));

        while (true)
        {
            System.out.println("\n=== EVENT BOOKING SYSTEM ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Select: ");

            if (scanner.hasNextInt())
            {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice)
                {
                    case 1: login(); break;
                    case 2: register(); break;
                    case 3: 
                        System.out.println("Goodbye!");
                        System.exit(0);
                    default: System.out.println("Invalid choice.");
                }
            } else
            {
                scanner.next(); 
            }
        }
    }


    private static void login() 
    {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userRepo.findByUsername(username);

        if (user != null && user.getPassword().equals(password))
        {
            currentUser = user;
            System.out.println("Login Successful! Welcome " + user.getUsername());

            if ("ADMIN".equalsIgnoreCase(user.getRole()))
            {
                showAdminMenu();
            }
            else {
                showUserMenu();
            }
        }
        else
        {
            System.out.println("Invalid username or password.");
        }
    }

    private static void register()
    {
        System.out.println("\n--- REGISTER ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        if (userRepo.findByUsername(username) != null)
        {
            System.out.println("Error: Username already taken.");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        User newUser = new User(username, password, "USER", email);
        userRepo.addUser(newUser);
        
        System.out.println("Registration successful! You can now login.");
    }

    private static void showAdminMenu() {
        while (currentUser != null) {
            System.out.println("\n[ADMIN DASHBOARD]");
            System.out.println("1. Create New Event");
            System.out.println("2. View All Events");
            System.out.println("3. Delete Event"); 
            System.out.println("4. Logout");
            System.out.print("Select Action: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        // add Event
                        System.out.print("ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Venue: ");
                        String venue = scanner.nextLine();
                        System.out.print("Date (YYYY-MM-DD): ");
                        String date = scanner.nextLine();
                        System.out.print("Total Seats: ");
                        int seats = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Artist Name: ");
                        String artist = scanner.nextLine();
                        
                        eventRepo.createEvent(new Concert(id, name, venue, date, seats, artist));
                        System.out.println("Success: Concert added.");
                        break;
                    
                    case 2:
                        // View Events
                        List<Event> events = eventRepo.getAllEvents();
                        System.out.println("\n--- All System Events ---");
                        if(events.isEmpty()) System.out.println("No events found.");
                        for (Event e : events) {
                            System.out.println("ID " + e.getId() + ": " + e.getName() + " (" + e.getDateTime() + ")");
                        }
                        break;

                    case 3:
                        // delete Event
                        System.out.print("Enter Event ID to delete: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();
                        
                        // нужно еще добавить репо дилит ивент 
                        boolean deleted = eventRepo.deleteEvent(deleteId);
                        
                        if (deleted)
                        {
                            System.out.println("Success: Event ID " + deleteId + " deleted.");
                        }
                        else
                        {
                            System.out.println("Error: Event ID " + deleteId + " not found.");
                        }
                        break;

                    case 4:
                        currentUser = null;
                        System.out.println("Logged out.");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
            else
            {
                scanner.next();
            }
        }
    }

    // меню юзера

    private static void showUserMenu()
    {
        while (currentUser != null)
        {
            System.out.println("\n[USER DASHBOARD]");
            System.out.println("1. View Available Events");
            System.out.println("2. Book an Event");
            System.out.println("3. Logout");
            System.out.print("Select Action: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        List<Event> events = eventRepo.getAllEvents();
                        System.out.println("\n--- Available Events ---");
                        for (Event e : events) {
                            System.out.println("ID " + e.getId() + ": " + e.getDetails() + " [Seats: " + e.getTotalSeats() + "]");
                        }
                        break;

                    case 2:
                        System.out.print("Enter Event ID to book: ");
                        int eventId = scanner.nextInt();
                        scanner.nextLine();
                        
                        Event eventToBook = eventRepo.findById(eventId);
                        
                        if (eventToBook != null) {
                            // bookTicket() method in Event.java
                            if (eventToBook.bookTicket()) {
                                System.out.println("SUCCESS: You booked a ticket for " + eventToBook.getName());
                            }
                            else 
                            {
                                System.out.println("FAILURE: Event is Sold Out!");
                            }
                        } else 
                        {
                            System.out.println("Event ID not found.");
                        }
                        break;

                    case 3:
                        currentUser = null;
                        System.out.println("Logged out.");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                scanner.next();
            }
        }
    }
}
