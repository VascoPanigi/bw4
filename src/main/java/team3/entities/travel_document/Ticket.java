package team3.entities.travel_document;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import team3.entities.distributor.Distributor;
import team3.entities.transportation.Transportation;

import java.time.LocalDate;


@Entity

@NamedQuery(name = "findValidTickets", query = "SELECT t FROM Ticket t WHERE t.isValid = :isValid ")

@Table(name = "Tickets")
public class Ticket extends TravelDocument {
    private boolean isValid;

    @ManyToOne
    @JoinColumn(name = "transportation_id")
    private Transportation transportation;

    @ManyToOne
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;

    public Ticket(LocalDate issueDate) {
        super(issueDate);
        this.isValid = true;
    }

    public Ticket() {
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }
}
