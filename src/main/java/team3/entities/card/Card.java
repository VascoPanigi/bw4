package team3.entities.card;

import jakarta.persistence.*;
import team3.entities.travel_document.Membership;
import team3.entities.travel_document.Ticket;
import team3.entities.user.UserClass;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity

public class Card {
    @Id
    @GeneratedValue
    private UUID card_id;

    private LocalDate expiration_date;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Membership> memberships;

    @OneToMany(mappedBy = "card_ticket", cascade = CascadeType.ALL)
    private List<Ticket> tickets;


    @OneToOne
    @JoinColumn(name = "user_id")
    private UserClass user;


    public Card() {
    }

    public Card(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }

    public UUID getCard_id() {
        return card_id;
    }

    public UserClass getUser() {
        return user;
    }

    public void setUser(UserClass user) {
        this.user = user;
    }

    public LocalDate getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }
}