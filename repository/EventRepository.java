package repository;
import domain.Event;
import java.util.ArrayList;
import java.util.List;

public class EventRepository implements IRepository<Event>
{
    private List<Event> events = new ArrayList<>();

    @Override
    public void add(Event item)
    {
        events.add(item);
    }

    @Override
    public List<Event> getAll() 
    {
        return events;
    }

    @Override
    public Event findById(int id)
    {
        return events.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    @Override
    public boolean delete(int id) 
    {
        return events.removeIf(e -> e.getId() == id);
    }
}
