package team3.entities.ticket;

import jakarta.persistence.*;
import team3.entities.transportation.Transportation;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "Tickets")
public class Ticket {
    @Id
    @GeneratedValue
    private UUID ticket_id;

    private LocalDate issueDate;

    private boolean isValid;

    @ManyToOne
    @JoinColumn(name = "transportation_id")
    private Transportation transportation;


    public Ticket(LocalDate issueDate) {
        this.issueDate = issueDate;
        this.isValid = true;
    }

    public Ticket() {
    }

    public UUID getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(UUID ticket_id) {
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

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
