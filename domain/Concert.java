package domain;

public class Concert extends Event
{
    private String artist;

    public Concert(int id, String name, String venue, String dateTime, int totalSeats, String artist)
    {
        super(id, name, venue, dateTime, totalSeats);
        this.artist = artist;
    }

    public String getArtist()
    { 
        return artist;
    }

    @Override
    public String getEventType() 
    {
        return "Concert";
    }
}
