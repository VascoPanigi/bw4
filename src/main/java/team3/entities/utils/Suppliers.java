package team3.entities.utils;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team3.dao.CardDao;
import team3.dao.UserDao;
import team3.entities.card.Card;
import team3.entities.membership.Membership;
import team3.entities.user.UserClass;
import team3.enums.MembershipPeriodicity;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class Suppliers {

    public static Supplier<Card> cardSupplier = () -> {
        LocalDate randomDate = Suppliers.randomDateSupplier(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 30)).get();
        return new Card(randomDate);
    };

    static Faker fkr = new Faker();
    public static Supplier<UserClass> userSupplier = () -> {
        String[] fullname;
        do {
            fullname = fkr.gameOfThrones().character().split(" ");
        } while (fullname.length < 2);

//        String[] fullname = fkr.gameOfThrones().character().split(" ");

        String name = fullname[0];
        String surname = fullname[1];

        return new UserClass(name, surname);
    };
    static Random random = new Random();
    public static Supplier<Membership> membershipSupplier = () -> {
        // generate random periodicity
        MembershipPeriodicity[] periocityValues = MembershipPeriodicity.values();
        MembershipPeriodicity periodicity = periocityValues[random.nextInt(periocityValues.length)];

        LocalDate randomDate = Suppliers.randomDateSupplier(LocalDate.of(2024, 1, 1), LocalDate.now()).get();

        // generate ending date based on periodicity
        LocalDate endingDate = switch (periodicity) {
            case WEEKLY -> randomDate.plusDays(7);
            case MONTHLY -> randomDate.plusDays(30);
        };

        return new Membership(periodicity, randomDate, endingDate);
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

}
