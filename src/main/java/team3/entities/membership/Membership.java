package team3.entities.membership;


import jakarta.persistence.*;
import team3.entities.card.Card;
import team3.enums.MembershipPeriodicity;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NamedQuery(name = "searchByTimeInterval", query = "SELECT m FROM Membership m WHERE m.starting_date >= :start_date AND m.starting_date <= :ending_date")
@NamedQuery(name = "searchByIdQuery", query = "SELECT m FROM Membership m WHERE m.card.id = :card_id")

public class Membership {
    @Id
    @GeneratedValue
    private UUID membership_id;

    @Enumerated(EnumType.STRING)
    private MembershipPeriodicity periodicity; // Settimana, Mensile
    private LocalDate starting_date;
    private LocalDate ending_date;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public Membership(MembershipPeriodicity periodicity, LocalDate starting_date, LocalDate ending_date) {
        this.periodicity = periodicity;
        this.starting_date = starting_date;
        this.ending_date = ending_date;
    }

    public Membership(LocalDate starting_date, LocalDate ending_date, MembershipPeriodicity periodicity) {
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

    public UUID getMembership_id() {
        return membership_id;
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

    @Override
    public String toString() {
        return "Membership{" +
                "periodicity=" + periodicity +
                ", starting_date=" + starting_date +
                ", ending_date=" + ending_date +
                '}';
    }
}

