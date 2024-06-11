package team3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team3.entities.user.UserClass;
import team3.exceptions.NotFoundException;

import java.util.UUID;

public class UserDao {
    private final EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    public void save(UserClass user) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(user);
        transaction.commit();
        System.out.println("The user: " + user.getName() + ", has been added.");
    }

    public UserClass findById(UUID userId) {
        UserClass user = em.find(UserClass.class, userId);
        if (userId == null) throw new NotFoundException(userId);
        return user;
    }

    public void findByIdAndDelete(UUID userId) {
        UserClass found = this.findById(userId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("The user: " + found.getName() + ", has been eliminated from our system!");
    }

    public UserClass findUserByNameAndSurname(String name, String surname) {
        TypedQuery<UserClass> userQuery = em.createNamedQuery("findUserByNameAndSurname", UserClass.class);
        userQuery.setParameter("name", name);
        userQuery.setParameter("surname", surname);
        return userQuery.getSingleResult();
    }


}
