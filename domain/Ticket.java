package domain;

public class Ticket 
{
    private String ticketId;
    private int row;
    private int seat;

    public Ticket(String ticketId, int row, int seat)
    {
        this.ticketId = ticketId;
        this.row = row;
        this.seat = seat;
    }

    public String getTicketId()
    { 
        return ticketId;
    }
}
