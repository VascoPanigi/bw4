package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.membership.Membership;
import team3.exceptions.NotFoundException;

import java.util.UUID;

public class MembershipDAO {

    private final EntityManager em;

    public MembershipDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Membership membership) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(membership);
        transaction.commit();
        System.out.println("The membership: " + membership.getMembership_id() + ", has been added.");
    }

    public Membership findById(UUID membershipId) {
        Membership membership = em.find(Membership.class, membershipId);
        if (membership == null) throw new NotFoundException(membershipId);
        return membership;
    }

    public void findByIdAndDelete(UUID membershipId) {
        Membership found = this.findById(membershipId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("The membership: " + found.getMembership_id() + ", has been eliminated from our system!");
    }

}
