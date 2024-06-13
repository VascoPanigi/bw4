package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.transportation.DutyPeriod;
import team3.exceptions.NotFoundException;

import java.util.UUID;

public class DutyPeriodDAO {

    private final EntityManager em;

    public DutyPeriodDAO(EntityManager em) {
        this.em = em;
    }

    public void save(DutyPeriod item) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(item);
        transaction.commit();
        System.out.println("The duty period with id: " + item.getId() + ", has been added.");
    }

    public DutyPeriod findById(UUID itemId) {
        DutyPeriod item = em.find(DutyPeriod.class, itemId);
        if (item == null) throw new NotFoundException(itemId);
        return item;
    }

    public void findByIdAndDelete(UUID itemId) {
        DutyPeriod found = this.findById(itemId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("The duty period with id: " + found.getId() + " has been eliminated from our system!");
    }
}
