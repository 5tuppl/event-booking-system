package repository;
import domain.Event;
import java.util.ArrayList;
import java.util.List;

public class EventRepository
{
    private List<Event> storage = new ArrayList<>();

    public void save(Event event) { storage.add(event); }
    public List<Event> findAll() { return storage; }
    public Event findById(int id) {
        return storage.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }
}
