package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.distributor.AuthorizedDistributor;
import team3.entities.distributor.AutomaticDistributor;
import team3.entities.distributor.Distributor;
import team3.exceptions.NotFoundException;

import java.util.List;
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

    public List<AutomaticDistributor> findAutomaticDistributors() {
        TypedQuery<AutomaticDistributor> query = em.createNamedQuery("findAutomaticDistributors", AutomaticDistributor.class);
        return query.getResultList();
    }

    public List<AuthorizedDistributor> findAuthorizedDistributors() {
        TypedQuery<AuthorizedDistributor> query = em.createNamedQuery("findAuthorizedDistributors", AuthorizedDistributor.class);
        return query.getResultList();
    }

    public List<AutomaticDistributor> findAllDistributorsInService() {
        TypedQuery<AutomaticDistributor> query = em.createNamedQuery("findAllDistributorsInService", AutomaticDistributor.class);
        return query.getResultList();
    }
}
