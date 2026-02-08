package repository;

import domain.Event;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    private List<Event> events = new ArrayList<>();

    public void createEvent(Event event)
    {
        events.add(event);
    }

    public List<Event> getAllEvents() 
    {
        return events;
    }

    public Event findById(int id)
    {
        for (Event event : events)
        {
            if (event.getId() == id)
            {
                return event;
            }
        }
        return null;
    }

    public boolean deleteEvent(int id)
    {
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).getId() == id)
            {
                events.remove(i);
                return true; 
            }
        }
        return false; 
    }
}
