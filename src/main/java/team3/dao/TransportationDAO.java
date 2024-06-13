package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.transportation.Transportation;
import team3.enums.TransportationType;
import team3.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public class TransportationDAO {

    private final EntityManager em;

    public TransportationDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Transportation item) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(item);
        transaction.commit();
        System.out.println("The travel with id: " + item.getId() + ", has been added.");
    }

    public Transportation findById(UUID itemId) {
        Transportation item = em.find(Transportation.class, itemId);
        if (item == null) throw new NotFoundException(itemId);
        return item;
    }

    public void findByIdAndDelete(UUID itemId) {
        Transportation found = this.findById(itemId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("The travel with id: " + found.getId() + " has been eliminated from our system!");
    }

//    public List<Transportation> findInServiceTransportation(TransportationState inService) {
//
//
//        TypedQuery<Transportation> userQuery = em.createNamedQuery("findInServiceTransportation", Transportation.class);
//        userQuery.setParameter("inService", inService);
//
//        return userQuery.getResultList();
//
//    }

    public List<Transportation> findTram(TransportationType tram) {
        TypedQuery<Transportation> findTramQuery = em.createNamedQuery("findTramQuery", Transportation.class);
        findTramQuery.setParameter("tram", tram);
        return findTramQuery.getResultList();
    }

    public List<Transportation> findBus(TransportationType bus) {
        TypedQuery<Transportation> findBusQuery = em.createNamedQuery("findBusQuery", Transportation.class);
        findBusQuery.setParameter("bus", bus);
        return findBusQuery.getResultList();
    }

    public List<Transportation> findAllTransportation() {
        TypedQuery<Transportation> findAllTransportation = em.createNamedQuery("findAllTransportation", Transportation.class);
        return findAllTransportation.getResultList();
    }
}
