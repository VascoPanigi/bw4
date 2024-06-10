package team3.entities.card;

import jakarta.persistence.*;
import team3.entities.membership.Membership;
import team3.entities.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity

public class Card {
    @Id
    @GeneratedValue
    private UUID card_id;

    private LocalDate expiration_date;

    @OneToMany(mappedBy = "card")
    private List<Membership> memberships;

    @OneToOne
    @JoinColumn(name = "user_id")

    private User user;


    public Card() {
    }

    public Card(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }
}