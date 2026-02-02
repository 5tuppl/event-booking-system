package repository;

import domain.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventRepository {
    private List<Event> storage = new ArrayList<>();

    public void save(Event event) { storage.add(event); }
    public List<Event> findAll() { return storage; }
    
    public Event findById(int id) {
        return storage.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    // NEW: Search by keyword (name or venue)
    public List<Event> search(String keyword) {
        return storage.stream()
            .filter(e -> e.getName().toLowerCase().contains(keyword.toLowerCase()) || 
                         e.getVenue().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());
    }
}
