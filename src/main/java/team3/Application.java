package team3;

import jakarta.persistence.*;
import team3.dao.CardDao;
import team3.dao.MembershipDAO;
import team3.dao.UserDao;
import team3.entities.user.UserClass;
import team3.entities.utils.Suppliers;
import team3.enums.MembershipPeriodicity;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4");
    private static final EntityManager em = emf.createEntityManager();
    private static final UserDao ud = new UserDao(em);
    private static final MembershipDAO md = new MembershipDAO(em);
    private static final CardDao cd = new CardDao(em);

    public static void manageDistributor() {
        while (true) {
            try {
                // richiesta dati per cercare user nel database
                System.out.println();
                System.out.println("Hello, please insert your name: ");
                System.out.println("If you wanna exit, type exit");
                String name = scanner.nextLine().toLowerCase();
                if (name.equals("exit")) {
                    System.out.println("See you soon!");
                    return;
                }
                System.out.println();
                System.out.println("Now, insert your surname");
                String surname = scanner.nextLine().toLowerCase();
                UserClass user = ud.findUserByNameAndSurname(name, surname);

                System.out.println(user);

                //check sulla card dell'utente
                if (user.getCard() == null) {
                    System.out.println("It seems that you don't have a card.");
                    System.out.println("Do you want to create one?");
                    System.out.println("1-Yes, 2-No");
                    int userChoice = Integer.parseInt(scanner.nextLine());
                    switch (userChoice) {
                        case 1:
                            Suppliers.linkCardStartingFromUser(user, cd, em);
                            break;
                        case 2:
                            System.out.println("Okay the f**k you");
                            return;
                    }
                } else {

                    System.out.println(user.getCard().getExpiration_date().toEpochDay());
                    System.out.println(LocalDate.now().toEpochDay());
                    if (user.getCard().getExpiration_date().toEpochDay() < LocalDate.now().toEpochDay()) {
                        System.out.println("Your card is expired. Renovating it...");
                        cd.renovateCard(user.getCard());
                        System.out.println("Card renovated");
                    }
                }

                // una volta che ci troviamo qui, siamo sicuri al 100% che abbiamo sia card attiva che user
                System.out.println("Which kind of membership do you wish to purchase? ");
                System.out.println("1-weekly, 2-monthly");
                int periodicity = Integer.parseInt(scanner.nextLine());

                // generazione membership a seconda della periodicity
                switch (periodicity) {
                    case 1:
                        md.addMembershipToCard(user.getCard(), MembershipPeriodicity.WEEKLY);
                        System.out.println("7 days membership added to your card.");
                        break;
                    case 2:
                        md.addMembershipToCard(user.getCard(), MembershipPeriodicity.MONTHLY);
                        System.out.println("30 days membership added to your card.");
                        break;
                }

            } catch (NoResultException nr) {
                System.out.println("This user does not exist in our Database. Do you wish to register?");
                System.out.println("1-Yes, 2-No");
                int userChoice = Integer.parseInt(scanner.nextLine());
                switch (userChoice) {
                    case 1:
                        createUserFromInput();
                        break;
                    case 2:
                        System.out.println("See you soon!");
                        break;
                }
            }
        }

        // extra. aggiungere numero biglietti
        //check se l-utente ha la carta, altrimenti creazione carta per utente X - get userID


        //se ha la carta, check se è scaduta
        //--------------------------------------------------------
        //tipo di abbonamento da prendere
        //TODO.7 - per fare il commit dell'abbonamento nella carta - QUERY ricerca carta per id
        //check se lo user voglia effettuare un'altra operazione


    }

    public static void main(String[] args) {
        System.out.println("Welcome to our system!");
        System.out.println();


//        UserClass gabibbo = new UserClass("gabibbo", "scotti");
//        ud.save(gabibbo);
//        for (int i = 0; i < 2; i++) {
//            UserClass newMembership = Suppliers.userSupplier.get();
//            ud.save(newMembership);
//            System.out.println("YIPPIEE");
//        }


        while (true) {
            System.out.println("Which operation do you wish to perform?");
            System.out.println("1-Create a new User, 2-Buy a membership");
            System.out.println("Type 0 to exit.");

            try {
                int userChoice = scanner.nextInt();
                scanner.nextLine();

                switch (userChoice) {
                    case 0:
                        System.out.println("See you!");
                        scanner.close();
                        return;
                    case 1:
                        createUserFromInput();
                        break;
                    case 2:
                        manageDistributor();
                        break;
                    default:
                        System.out.println("Invalid choice, try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }


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

//        for (int i = 0; i < 10; i++) {
//            Suppliers.linkUserAndCard(em, cd, ud, true);
//            Suppliers.linkUserAndCard(em, cd, ud, false);
//            System.out.println("YIPPIEE");
//        }


    }

    public static void createUserFromInput() {
        // controllare il tipo dell-input

        System.out.println("Insert your name: ");
        String name = scanner.nextLine().toLowerCase();
        System.out.println();

        System.out.println("Insert your surname: ");
        String surname = scanner.nextLine().toLowerCase();
        System.out.println();

        EntityTransaction transaction = em.getTransaction();
        try {
            UserClass newUser = new UserClass(name, surname);
            ud.save(newUser);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }
}
