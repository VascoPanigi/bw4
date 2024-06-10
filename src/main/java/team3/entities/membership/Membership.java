package team3.entities.membership;


import jakarta.persistence.*;
import team3.entities.card.Card;
import team3.enums.MembershipPeriodicity;

import java.time.LocalDate;
import java.util.UUID;

@Entity

public class Membership {
    @Id
    @GeneratedValue
    private UUID membership_id;

    private MembershipPeriodicity periodicity; // Settimana, Mensile
    private LocalDate starting_date;
    private LocalDate ending_date;

    @ManyToOne
    @JoinColumn(name = "tessera_id")
    private Card card;

    public Membership(MembershipPeriodicity periodicity, LocalDate starting_date, LocalDate ending_date) {
        this.periodicity = periodicity;
        this.starting_date = starting_date;
        this.ending_date = ending_date;
    }

    public Membership() {

    }
}

