package domain;

public class Concert extends Event {
    private String artist; // The 6th argument

    public Concert(int id, String name, String venue, String dateTime, int totalSeats, String artist) {
        super(id, name, venue, dateTime, totalSeats);
        this.artist = artist;
    }

    // Implementing the abstract method from Event
    @Override
    public String getDetails() {
        return "Concert: " + getName() + " featuring " + artist + " at " + getVenue();
    }
    
    public String getArtist() { return artist; }
}
