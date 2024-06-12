package team3.entities.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import team3.dao.CardDao;
import team3.dao.DistributorDAO;
import team3.dao.MembershipDAO;
import team3.dao.UserDao;
import team3.entities.travel_document.Membership;
import team3.entities.user.UserClass;
import team3.enums.MembershipPeriodicity;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Methods {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4");
    private static final EntityManager em = emf.createEntityManager();
    private static final UserDao ud = new UserDao(em);
    private static final MembershipDAO md = new MembershipDAO(em);
    private static final CardDao cd = new CardDao(em);
    private static final DistributorDAO dd = new DistributorDAO(em);

    public static void manageDistributor() {
        while (true) {
            try {
                //TODO where do you wanna go? automatic or authorized distributor
                // if distributor is automatic check if it's in_service, otherwise make him chose another one


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


                // TODO what do you want to purchase? membership or ticket? to purchase a membership, you must have a card.


                //check sulla card dell'utente
                if (user.getCard() == null) {
                    System.out.println("It seems that you don't have a card.");
                    System.out.println("Do you want to create one?");
                    System.out.println("1-Yes, 2-No");
                    int userChoice = getUserChoice(1, 2);
                    switch (userChoice) {
                        case 1:
                            Suppliers.linkCardStartingFromUser(user, cd, em);
                            break;
                        case 2:
                            System.out.println("See you soon!");
                            return;
                    }
                } else {

                    System.out.println(user.getCard().getExpiration_date().toEpochDay());
                    System.out.println(LocalDate.now().toEpochDay());
                    if (LocalDate.now().isAfter(user.getCard().getExpiration_date())) {
                        System.out.println("Your card is expired. Renovating it...");
                        cd.renovateCard(user.getCard());
                        System.out.println("Card renovated");
                    }
                }

                // una volta che ci troviamo qui, siamo sicuri al 100% che abbiamo sia card attiva che user
                System.out.println("Which kind of membership do you wish to purchase? ");
                System.out.println("1-weekly, 2-monthly");
                int periodicity = getUserChoice(1, 2);

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
                int userChoice = getUserChoice(1, 2);
                switch (userChoice) {
                    case 1:
                        Suppliers.createUserFromInput(scanner, em, ud);
                        break;
                    case 2:
                        System.out.println("See you soon!");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    public static void searchByTimeInterval() {
        while (true) {
            try {
                System.out.println();
                System.out.println("Hello, insert the starting year");
                System.out.println("If you want to exit, type 0");
                int startingYear = getInputYear();
                if (startingYear == 0) break;

                int startingMonth = getInputMonth("starting");
                int startingDay = getInputDay("starting");

                System.out.println("Now, insert the ending year");
                int endingYear = getInputYear();
                int endingMonth = getInputMonth("ending");
                int endingDay = getInputDay("ending");
                LocalDate start_date = LocalDate.of(startingYear, startingMonth, startingDay);
                LocalDate ending_date = LocalDate.of(endingYear, endingMonth, endingDay);
                if (start_date.isAfter(ending_date)) {
                    System.out.println("The start date must be before the end date.");
                    continue;
                }

                List<Membership> results = md.searchByTimeInterval(start_date, ending_date);
                if (results.isEmpty()) {
                    System.out.println("No memberships found within the given date range.");
                } else {
                    results.forEach(System.out::println);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values.");
            } catch (DateTimeException e) {
                System.out.println("Invalid date. " + e.getMessage());
            }

        }

    }

    private static int getUserChoice(int n1, int n2) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= n1 && choice <= n2) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter a value between " + n1 + " and " + n2 + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    private static int getInputDay(String period) {
        while (true) {
            try {
                System.out.println("Insert the " + period + " day");
                int day = Integer.parseInt(scanner.nextLine());
                if (day < 1 || day > 31) {
                    System.out.println("Invalid day. Please enter a value from 1 to 31.");
                } else {
                    return day;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    private static int getInputMonth(String period) {
        while (true) {
            try {
                System.out.println("Insert the " + period + " month");
                int month = Integer.parseInt(scanner.nextLine());
                if (month < 1 || month > 12) {
                    System.out.println("Invalid month. Please enter a value from 1 to 12.");
                } else {
                    return month;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    private static int getInputYear() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid year. Please enter a numeric value.");
            }
        }
    }

    public static void App() {
//        for (int i = 0; i < 5; i++) {
//            AuthorizedDistributor newDistributor = Suppliers.authorizedDistributorSupplier.get();
//            dd.save(newDistributor);
//            System.out.println("YIPPIEE");
//        }
//
//        for (int i = 0; i < 5; i++) {
//            AutomaticDistributor newDistributor = Suppliers.automaticDistributorSupplier.get();
//            dd.save(newDistributor);
//            System.out.println("YIPPIEE");
//        }


        System.out.println("Welcome to our system!");
        System.out.println();


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
                        Suppliers.createUserFromInput(scanner, em, ud);
                        break;
                    case 2:
                        manageDistributor();
                        break;
                    case 3:
                        searchByTimeInterval();
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


//        UserClass gabibbo = new UserClass("gabibbo", "scotti");
//        ud.save(gabibbo);
//        for (int i = 0; i < 20; i++) {
//            Membership newMembership = Suppliers.membershipSupplier.get();
//            md.save(newMembership);
//            System.out.println("YIPPIEE");
//        }
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
}
