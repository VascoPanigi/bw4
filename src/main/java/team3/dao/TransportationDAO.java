package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.ticket.Ticket;
import team3.entities.transportation.Transportation;
import team3.enums.TransportationState;
import team3.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public class TransportationDAO {

    private final EntityManager em;

    public TransportationDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Transportation transportation) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (transportation.getId() == null) {
                transportation.setId(UUID.randomUUID());
            }
            em.persist(transportation);
            transaction.commit();
            System.out.println("Transportation " + transportation.getId() + " has been added.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Transportation findById(UUID id) {
        Transportation transportation = em.find(Transportation.class, id);
        if (transportation == null) {
            throw new NotFoundException(id);
        }
        return transportation;
    }

    public void deleteById(UUID id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Transportation transportation = findById(id);
            em.remove(transportation);
            transaction.commit();
            System.out.println("Transportation " + id + " has been eliminated from our system!");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Transportation> findInServiceTransportation(TransportationState inService) {


        TypedQuery<Transportation> userQuery = em.createNamedQuery("findInServiceTransportation", Transportation.class);
        userQuery.setParameter("inService", inService);

        return userQuery.getResultList();

    }
}
