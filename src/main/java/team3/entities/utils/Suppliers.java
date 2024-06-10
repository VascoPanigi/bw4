package team3.entities.utils;

import com.github.javafaker.Faker;
import team3.entities.card.Card;
import team3.entities.membership.Membership;
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
}
