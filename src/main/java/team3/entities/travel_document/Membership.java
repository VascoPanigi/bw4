package team3.entities.travel_document;


import jakarta.persistence.*;
import team3.entities.card.Card;
import team3.entities.distributor.Distributor;
import team3.enums.MembershipPeriodicity;

import java.time.LocalDate;

@Entity
@NamedQueries({
        @NamedQuery(name = "searchByTimeInterval", query = "SELECT m FROM Membership m WHERE m.starting_date >= :start_date AND m.starting_date <= :ending_date AND m.distributor = :distributor"),
        @NamedQuery(name = "findMembershipByCard", query = "SELECT m FROM Membership m WHERE m.card.id = :card "),
})
public class Membership extends TravelDocument {

    @Enumerated(EnumType.STRING)
    private MembershipPeriodicity periodicity; // Settimana, Mensile
    private LocalDate starting_date;
    private LocalDate ending_date;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;

    public Membership(LocalDate starting_date, LocalDate ending_date, MembershipPeriodicity periodicity, LocalDate issueDate) {
        super(issueDate);
        this.ending_date = ending_date;
        this.starting_date = starting_date;
        this.periodicity = periodicity;
    }


    public Membership(MembershipPeriodicity periodicity, LocalDate starting_date, LocalDate ending_date, LocalDate issueDate) {
        super(issueDate);
        this.ending_date = ending_date;
        this.starting_date = starting_date;
        this.periodicity = periodicity;
    }

    public Membership() {
    }

    public MembershipPeriodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(MembershipPeriodicity periodicity) {
        this.periodicity = periodicity;
    }

    public LocalDate getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(LocalDate starting_date) {
        this.starting_date = starting_date;
    }

    public LocalDate getEnding_date() {
        return ending_date;
    }

    public void setEnding_date(LocalDate ending_date) {
        this.ending_date = ending_date;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "periodicity=" + periodicity +
                ", starting_date=" + starting_date +
                ", ending_date=" + ending_date +
                '}';
    }
}

