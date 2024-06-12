package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.card.Card;
import team3.entities.distributor.Distributor;
import team3.entities.travel_document.Ticket;
import team3.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.UUID;

public class TicketDAO {

    private final EntityManager em;

    public TicketDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Ticket ticket) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(ticket);
        transaction.commit();
        System.out.println("The ticket: " + ticket.getId() + ", has been added.");
    }

    public Ticket findById(UUID ticketId) {
        Ticket ticket = em.find(Ticket.class, ticketId);
        if (ticket == null) throw new NotFoundException(ticketId);
        return ticket;
    }

    public void findByIdAndDelete(UUID ticketId) {
        Ticket found = this.findById(ticketId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("The ticket: " + found.getId() + ", has been eliminated from our system!");
    }


    public void addTicketToCard(Card card, Distributor distributor) {
        EntityTransaction transaction = em.getTransaction();

        Ticket ticket = new Ticket(LocalDate.now());


        transaction.begin();
        ticket.setCard_ticket(card);
        ticket.setDistributor(distributor);

        em.persist(ticket);
        transaction.commit();
        System.out.println("The ticket has been created: " + ticket.getId());
    }

//    public List<Ticket> findValidTickets(Boolean isValid) {
//        TypedQuery<Ticket> userQuery = em.createNamedQuery("findValidTickets", Ticket.class);
//        userQuery.setParameter("isValid", isValid);
//        return userQuery.getResultList();
//    }
}
