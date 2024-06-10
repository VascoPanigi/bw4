package team3.entities.utils;

import com.github.javafaker.Faker;
import team3.entities.membership.Membership;
import team3.enums.MembershipPeriodicity;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Suppliers {
    static Faker fkr = new Faker();
    static Random random = new Random();

    public static Suppliers<Membership> membershipSupplier = () -> {
        // generate random periodicity
        MembershipPeriodicity[] periocityValues = MembershipPeriodicity.values();
        MembershipPeriodicity periodicity = periocityValues[random.nextInt(periocityValues.length)];

        // generate random date -- classic method cause faker.date().between() does not provide expected values. Daje
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        long start = startDate.toEpochDay();
        LocalDate endDate = LocalDate.now();
        long end = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();

        LocalDate randomDate = LocalDate.ofEpochDay(randomEpochDay);
        System.out.println(randomDate);

        //generate ending date based on periodicity

        LocalDate endingDate = switch (periodicity) {
            case WEEKLY -> randomDate.plusDays(7);
            case MONTHLY -> randomDate.plusDays(30);
        };


        return new Membership(periodicity, randomDate, endingDate);

    };


}
