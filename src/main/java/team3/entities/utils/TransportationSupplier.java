package team3.entities.utils;

import com.github.javafaker.Faker;
import team3.entities.transportation.Transportation;
import team3.enums.TransportationState;
import team3.enums.TransportationType;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class TransportationSupplier {

    static Faker faker = new Faker();
    static Random random = new Random();

    public static Supplier<Transportation> transportationSupplier = () -> {
        Transportation transportation = new Transportation();

        // generate random Id
        transportation.setId(UUID.randomUUID());

        // Set the transport type randomly
        TransportationType[] typeValues = TransportationType.values();
        TransportationType randomType = typeValues[random.nextInt(typeValues.length)];
        transportation.setType(randomType);

        // Set capacity randomly (between 1 and 100)
        transportation.setCapacity(random.nextInt(100) + 1);

        // Set the transport status randomly
        TransportationState[] stateValues = TransportationState.values();
        TransportationState randomState = stateValues[random.nextInt(stateValues.length)];
        transportation.setState(randomState);

        // Set the service and maintenance period to null
        transportation.setEndutyPeriod(null);
        transportation.setMaintenancePeriod(null);

        return transportation;
    };

}
