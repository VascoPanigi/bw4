package team3.entities.travel_document;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import team3.entities.card.Card;
import team3.entities.distributor.Distributor;
import team3.entities.transportation.Transportation;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
//@NamedQuery(name = "findValidTickets", query = "SELECT t FROM Ticket t WHERE t.isValid = :isValid")
@Table(name = "Tickets")
public class Ticket extends TravelDocument {

    private LocalDateTime isValid;

    @ManyToOne
    @JoinColumn(name = "transportation_id")
    private Transportation transportation;

    @ManyToOne
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card_ticket;

    public Ticket(LocalDate issueDate) {
        super(issueDate);
        this.isValid = null;
    }

    public Ticket() {
    }

    public Card getCard_ticket() {
        return card_ticket;
    }

    public void setCard_ticket(Card card_ticket) {
        this.card_ticket = card_ticket;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public LocalDateTime isValid() {
        return isValid;
    }

    public void setValid(LocalDateTime valid) {
        isValid = valid;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }
}
