package team3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team3.dao.CardDao;
import team3.dao.MembershipDAO;
import team3.dao.UserDao;
import team3.entities.utils.Suppliers;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        MembershipDAO md = new MembershipDAO(em);
        UserDao ud = new UserDao(em);
        CardDao cd = new CardDao(em);
        //TODO.1 - SWITCH per menu
        //TODO.2 - creazione utente
        //TODO.3 - creare sistema biglietteria. Far inserire il nome e cognome all-utente. verificare se ha la carta o no. se ce l'ha
        // verificare che non sia scaduta. Se è tutto okay procedere con la procedura - selezionare tipo di abbonamento e
        // aggiungerlo alla carta dello user
        //TODO.4 - EXTRA' aggiungere zone e fare un check al momento della vidimazione del biglietto. un mezzo fa una tratta specifica
        // in una zona specifica.


//        for (int i = 0; i < 20; i++) {
//            Membership newMembership = Suppliers.membershipSupplier.get();
//            md.save(newMembership);
//            System.out.println("YIPPIEE");
//        }
//
//        UserClass gabibbo = new UserClass("gabibbo", "scotti");
//        ud.save(gabibbo);
//
//
//        System.out.println("ao");


        for (int i = 0; i < 10; i++) {
            Suppliers.linkUserAndCard(em, cd, ud, true);
            Suppliers.linkUserAndCard(em, cd, ud, false);
            System.out.println("YIPPIEE");
        }
    }
}
