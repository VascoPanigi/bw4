package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.transportation.MaintenancePeriod;
import team3.exceptions.NotFoundException;

import java.util.UUID;

public class MaintenancePeriodDAO {
    private final EntityManager em;

    public MaintenancePeriodDAO(EntityManager em) {
        this.em = em;
    }

    public void save(MaintenancePeriod item) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(item);
        transaction.commit();
        System.out.println("The maintenance period with id: " + item.getId() + ", has been added.");
    }

    public MaintenancePeriod findById(UUID itemId) {
        MaintenancePeriod item = em.find(MaintenancePeriod.class, itemId);
        if (item == null) throw new NotFoundException(itemId);
        return item;
    }

    public void findByIdAndDelete(UUID itemId) {
        MaintenancePeriod found = this.findById(itemId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("The maintenance period with id: " + found.getId() + " has been eliminated from our system!");
    }
}
