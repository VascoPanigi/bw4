package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.card.Card;
import team3.exceptions.NotFoundException;

import java.util.UUID;

public class CardDao {
    private final EntityManager em;

    public CardDao(EntityManager em) {
        this.em = em;
    }

    public void save(Card card) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(card);
        transaction.commit();
        System.out.println("The card with id: " + card.getCard_id() + ", has been added.");
    }

    public Card findById(UUID cardId) {
        Card card = em.find(Card.class, cardId);
        if (card == null) throw new NotFoundException(cardId);
        return card;
    }

    public void findByIdAndDelete(UUID cardId) {
        Card found = this.findById(cardId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("The card with id: " + found.getCard_id() + ", has been eliminated from our system!");
    }

    public void renovateCard(Card card) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        card.setExpiration_date(card.getExpiration_date().plusYears(1));
        em.persist(card);
        transaction.commit();
        System.out.println("The card has now expiration date: " + card.getExpiration_date());
    }
}
