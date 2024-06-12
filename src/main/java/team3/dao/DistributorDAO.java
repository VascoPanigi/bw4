package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.distributor.Distributor;
import team3.exceptions.NotFoundException;

import java.util.UUID;

public class DistributorDAO {

    private final EntityManager em;

    public DistributorDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Distributor distributor) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(distributor);
        transaction.commit();
        System.out.println("The distributor: " + distributor.getId() + ", type: " + distributor.getType().toString().toLowerCase() + ", has been added.");
    }

    public Distributor findById(UUID distributorId) {
        Distributor distributor = em.find(Distributor.class, distributorId);
        if (distributor == null) throw new NotFoundException(distributorId);
        return distributor;
    }

    public void findByIdAndDelete(UUID distributorId) {
        Distributor found = this.findById(distributorId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("The distributor: " + found.getId() + ", type: " + found.getType().toString().toLowerCase() + " has been eliminated from our system!");
    }
}
