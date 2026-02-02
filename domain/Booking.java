package domain;

import java.util.ArrayList;
import java.util.List;

public class Booking
{
    private int bookingId;
    private User user;
    private Event event;
    private List<Ticket> tickets;

    public Booking(int bookingId, User user, Event event)
    {
        this.bookingId = bookingId;
        this.user = user;
        this.event = event;
        this.tickets = new ArrayList<>();
    }

    public void addTicket(Ticket ticket) { tickets.add(ticket); }
    public int getBookingId() { return bookingId; }
    public List<Ticket> getTickets() { return tickets; }
}
