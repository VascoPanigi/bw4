package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import team3.entities.membership.Membership;
import team3.entities.ticket.Ticket;

import javax.sound.sampled.Line;
import java.util.List;

public class TicketDAO {

    private final EntityManager em;

    public TicketDAO(EntityManager em) {
        this.em = em;
    }

    public List<Ticket> findValidTickets(Boolean isValid) {


        TypedQuery<Ticket> userQuery = em.createNamedQuery("findValidTickets", Ticket.class);
        userQuery.setParameter("isValid", isValid);

        return userQuery.getResultList();

    }
}
