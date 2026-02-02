package service;

import domain.*;
import repository.EventRepository;
import observer.NotificationObserver;

public class BookingService
{
    private EventRepository eventRepo;
    private NotificationObserver notifier;

    public BookingService(EventRepository eventRepo, NotificationObserver notifier)
    {
        this.eventRepo = eventRepo;
        this.notifier = notifier;
    }

    public Booking createBooking(int bookingId, User user, int eventId, int row, int seat) {
        Event event = eventRepo.findById(eventId);
        if (event == null) return null;

        Booking booking = new Booking(bookingId, user, event);
        Ticket ticket = new Ticket("T-" + bookingId, row, seat);
        booking.addTicket(ticket);

        notifier.sendNotification(user, "Booked " + event.getName() + " for " + event.getDateTime());
        return booking;
    }
}
