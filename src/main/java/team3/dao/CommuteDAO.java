package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.commute.Commute;
import team3.exceptions.NotFoundException;

import java.util.UUID;

public class CommuteDAO {
    private final EntityManager em;

    public CommuteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Commute item) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(item);
        transaction.commit();
        System.out.println("The commute with id: " + item.getCommute_id() + ", has been added.");
    }

    public Commute findById(UUID itemId) {
        Commute item = em.find(Commute.class, itemId);
        if (item == null) throw new NotFoundException(itemId);
        return item;
    }

    public void findByIdAndDelete(UUID itemId) {
        Commute found = this.findById(itemId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("The commute with id: " + found.getCommute_id() + " has been eliminated from our system!");
    }
}
