package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.entities.card.Card;
import team3.entities.membership.Membership;
import team3.enums.MembershipPeriodicity;
import team3.exceptions.NotFoundException;

import java.time.LocalDate;
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

    public void addMembershipToCard(Card card, MembershipPeriodicity mp) {
        EntityTransaction transaction = em.getTransaction();

        Membership membership = null;

        switch (mp) {
            case mp.MONTHLY ->
                    membership = new Membership(LocalDate.now(), LocalDate.now().plusDays(30), MembershipPeriodicity.MONTHLY);
            case mp.WEEKLY ->
                    membership = new Membership(LocalDate.now(), LocalDate.now().plusDays(7), MembershipPeriodicity.WEEKLY);
        }
        transaction.begin();
        membership.setCard(card);
        em.persist(membership);
        transaction.commit();
        System.out.println("The membership has been created: " + membership.getMembership_id());

    }

}
