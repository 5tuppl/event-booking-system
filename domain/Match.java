package domain;

public class Match extends Event
{
    private String sportType; // The 6th argument

    public Match(int id, String name, String venue, String dateTime, int totalSeats, String sportType)
    {
        super(id, name, venue, dateTime, totalSeats);
        this.sportType = sportType;
    }

    // Implementing the abstract method from Event
    @Override
    public String getDetails() {
        return "Match: " + getName() + " (" + sportType + ") at " + getVenue();
    }

    public String getSportType()
    {
        return sportType;
    }
}
