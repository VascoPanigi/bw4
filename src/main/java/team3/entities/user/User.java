package team3.entities.user;


import jakarta.persistence.*;

import team3.entities.card.Card;

import java.util.UUID;

@Entity

public class User {

    @Id
    @GeneratedValue

    private UUID user_id;

    private String name;

    private String surname;

@OneToOne(mappedBy = "user")
    private Card card;

public User() {

}


    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
