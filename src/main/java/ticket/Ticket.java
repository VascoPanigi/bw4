package ticket;

import jakarta.persistence.*;
import transportation.Transportation;
import java.time.LocalDate;



@Entity
@Table (name = "Tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticket_id;

    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "transportation_id")
    private Transportation transportation;


    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }
}
