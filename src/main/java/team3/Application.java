package team3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team3.dao.MembershipDAO;
import team3.entities.membership.Membership;
import team3.entities.utils.Suppliers;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        MembershipDAO md = new MembershipDAO(em);

        for (int i = 0; i < 20; i++) {
            Membership newMembership = Suppliers.membershipSupplier.get();
            md.save(newMembership);
            System.out.println("YIPPIEE");
        }
    }
}
