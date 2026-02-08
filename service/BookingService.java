package service;
import domain.Event;
import repository.IRepository;

public class BookingService
{
    private IRepository<Event> eventRepo;

    public BookingService(IRepository<Event> eventRepo)
    {
        this.eventRepo = eventRepo;
    }

    public boolean bookEvent(int eventId)
    {
        Event event = eventRepo.findById(eventId);
        if (event != null && event.getTotalSeats() > 0)
        {
            return event.bookTicket();
        }
        return false;
    }
}
