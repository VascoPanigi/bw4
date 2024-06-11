package team3.entities.user;

import jakarta.persistence.*;
import team3.entities.card.Card;

import java.util.UUID;

@Entity
public class UserClass {
    @Id
    @GeneratedValue
    private UUID user_id;

    private String name;
    private String surname;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Card card;


    public UserClass(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public UserClass() {
    }

    public UUID getUser_id() {
        return user_id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}