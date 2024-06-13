package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.travel.Travel;
import team3.exceptions.NotFoundException;

import java.util.UUID;

public class TravelDao {
    private final EntityManager em;

    public TravelDao(EntityManager em) {
        this.em = em;
    }

    public void save(Travel item) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(item);
        transaction.commit();
        System.out.println("The travel with id: " + item.getId() + ", has been added.");
    }

    public Travel findById(UUID itemId) {
        Travel item = em.find(Travel.class, itemId);
        if (item == null) throw new NotFoundException(itemId);
        return item;
    }

    public void findByIdAndDelete(UUID itemId) {
        Travel found = this.findById(itemId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("The travel with id: " + found.getId() + " has been eliminated from our system!");
    }
}
