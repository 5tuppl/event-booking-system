package domain;

public class Match extends Event
{
    private String homeTeam;
    private String awayTeam;

    public Match(int id, String name, String venue, String dateTime, int totalSeats, String homeTeam, String awayTeam)
    {
        super(id, name, venue, dateTime, totalSeats);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public String getHomeTeam()
    {
        return homeTeam;
    }
    public String getAwayTeam()
    {
        return awayTeam;
    }

    @Override
    public String getEventType()
    {
        return "Sporting Match";
    }
}
