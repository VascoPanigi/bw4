package team3.entities.utils;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.dao.CardDao;
import team3.dao.UserDao;
import team3.entities.card.Card;
import team3.entities.commute.Commute;
import team3.entities.distributor.AuthorizedDistributor;
import team3.entities.distributor.AutomaticDistributor;
import team3.entities.distributor.Distributor;
import team3.entities.transportation.Transportation;
import team3.entities.travel_document.Membership;
import team3.entities.user.UserClass;
import team3.enums.MembershipPeriodicity;
import team3.enums.TransportationType;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class Suppliers {

    public static Supplier<Card> cardSupplier = () -> {
        LocalDate randomDate = Suppliers.randomDateSupplier(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 30)).get();
        return new Card(randomDate);
    };
    public static Supplier<AuthorizedDistributor> authorizedDistributorSupplier = AuthorizedDistributor::new;
    static Random random = new Random();
    public static Supplier<Transportation> transportationSupplier = () -> {
        Transportation transportation = new Transportation();
        transportation.setType(random.nextBoolean() ? TransportationType.BUS : TransportationType.TRAM);
        if (transportation.getType().toString().toLowerCase().equals("bus")) {
            transportation.setCapacity(50);
        } else {
            transportation.setCapacity(100);
        }
        return transportation;
    };
    public static Supplier<Membership> membershipSupplier = () -> {
        // generate random periodicity
        MembershipPeriodicity[] periocityValues = MembershipPeriodicity.values();
        MembershipPeriodicity periodicity = periocityValues[random.nextInt(periocityValues.length)];

        LocalDate randomDate = Suppliers.randomDateSupplier(LocalDate.of(2020, 1, 1), LocalDate.now()).get();

        // generate ending date based on periodicity
        LocalDate endingDate = switch (periodicity) {
            case WEEKLY -> randomDate.plusDays(7);
            case MONTHLY -> randomDate.plusDays(30);
        };

        return new Membership(periodicity, randomDate, endingDate, LocalDate.now());
    };
    public static Supplier<Distributor> randomDistributorSupplier = () -> {
        boolean isAutomatic = random.nextBoolean();


        if (isAutomatic) {
            Boolean inService = random.nextBoolean();
            return new AutomaticDistributor(inService);
        } else {
            return new AuthorizedDistributor();
        }
    };
    //    public static Supplier<Travel> travelSupplier = () -> {
//        String origin = fkr.address().city();
//        String destination = fkr.address().city();
//        LocalDateTime departureTime = LocalDateTime.now().plusDays(fkr.number().numberBetween(1, 30));
//        LocalDateTime arrivalTime = departureTime.plusHours(fkr.number().numberBetween(1, 10));
//
//        return new Travel(origin, destination, departureTime, arrivalTime);
//    };
    public static Supplier<AutomaticDistributor> automaticDistributorSupplier = () -> {
        Boolean inService = random.nextBoolean();
        return new AutomaticDistributor(inService);
    };
    static Faker fkr = new Faker();
    public static Supplier<UserClass> userSupplier = () -> {
        String[] fullname;
        do {
            fullname = fkr.gameOfThrones().character().split(" ");
        } while (fullname.length < 2);

//        String[] fullname = fkr.gameOfThrones().character().split(" ");

        String name = fullname[0].toLowerCase();
        String surname = fullname[1].toLowerCase();

        return new UserClass(name, surname);
    };
    public static Supplier<Commute> commuteSupplier = () -> {
        String name = fkr.address().city();
        String terminal = fkr.address().city();
        return new Commute(name, terminal);
    };

    public static Supplier<LocalDate> randomDateSupplier(LocalDate startDate, LocalDate endDate) {
        // generate random date -- classic method cause faker.date().between() does not provide expected values. Daje

        return () -> {
            long start = startDate.toEpochDay();
            long end = endDate.toEpochDay();
            long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();

            return LocalDate.ofEpochDay(randomEpochDay);
        };
    }

    public static void linkUserAndCard(EntityManager em, CardDao cd, UserDao ud, boolean hasCard) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            UserClass newUser = Suppliers.userSupplier.get();
            if (hasCard) {
                Card newCard = Suppliers.cardSupplier.get();

                newUser.setCard(newCard);
                newCard.setUser(newUser);
                cd.save(newCard);
            }
            ud.save(newUser);
            transaction.commit();

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    public static void linkCardStartingFromUser(UserClass user, CardDao cd, EntityManager em) {
        EntityTransaction transaction = em.getTransaction();

        try {
            Card newCard = new Card(LocalDate.now().plusYears(1));
            user.setCard(newCard);
            newCard.setUser(user);
            cd.save(newCard);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void createUserFromInput(Scanner scanner, EntityManager em, UserDao ud) {
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

    public static void createUserFromValues(String name, String surname, UserDao ud, EntityManager em) {

        // controllare il tipo dell-input
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
