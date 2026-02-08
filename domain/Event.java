package domain;

public abstract class Event {
    private int id;
    private String name;
    private String venue;
    private String dateTime;
    private int totalSeats;

    public Event(int id, String name, String venue, String dateTime, int totalSeats)
    {
        this.id = id;
        this.name = name;
        this.venue = venue;
        this.dateTime = dateTime;
        this.totalSeats = totalSeats;
    }

    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getVenue()
    {
        return venue;
    }
    public String getDateTime()
    {
        return dateTime;
    }
    public int getTotalSeats() 
    { 
        return totalSeats; 
    }

    public abstract String getDetails();

    public boolean bookTicket()
    {
        if (totalSeats > 0) {
            totalSeats--; 
            return true;
        }
        return false;     
    }
}
