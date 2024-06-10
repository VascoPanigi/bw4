package team3.entities.card;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import team3.entities.membership.Membership;

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

}