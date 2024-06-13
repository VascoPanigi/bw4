package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.card.Card;
import team3.entities.travel_document.Membership;
import team3.entities.travel_document.Ticket;
import team3.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;
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

    public boolean isValidMembership(Card card) {
        TypedQuery<Membership> query = em.createQuery("SELECT m FROM Membership m WHERE m.card = :card", Membership.class);
        query.setParameter("card", card);
        List<Membership> memberships = query.getResultList();

        for (Membership membership : memberships) {
            if (membership.getEnding_date().isAfter(LocalDate.now())) {
                System.out.println("A valid membership has been found! It has expiration date " + membership.getEnding_date());
                System.out.println();
                // se siamo qui abbiamo trovato almeno un abbonamento valido
                return true;
            }
        }
        System.out.println("No valid membership has been found!\n");

        return false;
    }

    public Ticket findValidTickets(Card card) {
        TypedQuery<Ticket> userQuery = em.createNamedQuery("findValidTickets", Ticket.class);
        userQuery.setParameter("card", card);
        return userQuery.getSingleResult();
    }
}
