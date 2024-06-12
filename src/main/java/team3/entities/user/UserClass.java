package team3.entities.user;

import jakarta.persistence.*;
import team3.entities.card.Card;

import java.util.UUID;

@Entity
@NamedQuery(name = "findUserByNameAndSurname", query = "SELECT u FROM UserClass u WHERE u.name LIKE :name AND u.surname LIKE :surname")
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

    @Override
    public String toString() {
        return "UserClass{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", card=" + card +
                '}';
    }
}
