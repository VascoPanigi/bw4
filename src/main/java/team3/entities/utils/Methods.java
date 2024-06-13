package team3.entities.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import team3.dao.*;
import team3.entities.commute.Commute;
import team3.entities.distributor.AuthorizedDistributor;
import team3.entities.distributor.AutomaticDistributor;
import team3.entities.distributor.Distributor;
import team3.entities.transportation.Transportation;
import team3.entities.travel.Travel;
import team3.entities.travel_document.Membership;
import team3.entities.travel_document.Ticket;
import team3.entities.user.UserClass;
import team3.enums.MembershipPeriodicity;
import team3.enums.TransportationType;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Methods {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4");
    private static final EntityManager em = emf.createEntityManager();
    private static final UserDao ud = new UserDao(em);
    private static final MembershipDAO md = new MembershipDAO(em);
    private static final CardDao cd = new CardDao(em);
    private static final DistributorDAO dd = new DistributorDAO(em);
    private static final TicketDAO td = new TicketDAO(em);
    private static final CommuteDAO cmd = new CommuteDAO(em);
    private static final TravelDao trd = new TravelDao(em);
    private static final TransportationDAO transportd = new TransportationDAO(em);
    private static final DutyPeriodDAO dpd = new DutyPeriodDAO(em);
    private static final MaintenancePeriodDAO mpd = new MaintenancePeriodDAO(em);
    private static final Random random = new Random();

    public static void manageDistributor() {
        while (true) {
            try {
                System.out.println("Hello, where do you want to go?");
                System.out.println("1- Automatic Distributor, 2- Authorized Distributor");
                System.out.println("If you wanna exit, type 0");

                int distributorChoice = getUserChoice(1, 2);

                Distributor distributor = null;
                switch (distributorChoice) {
                    case 1:
                        System.out.println("Finding an automatic distributor on service...");
                        List<AutomaticDistributor> automaticDistributorsInService = dd.findAllDistributorsInService();
                        //  se la lista è vuota la riempiamo
                        if (automaticDistributorsInService.isEmpty()) {
                            for (int i = 0; i < 10; i++) {
                                Suppliers.automaticDistributorSupplier.get();
                            }
                        }
                        //prendiamo un index random della lista
                        int randomIndex = random.nextInt(automaticDistributorsInService.size());
                        distributor = automaticDistributorsInService.get(randomIndex);

                        System.out.println("Found one! Your distributor has ID: " + distributor.getId());
//                        System.out.println(distributor);
                        break;
                    case 2:
                        System.out.println("Looking for an authorized near you...");
                        List<AuthorizedDistributor> authorizedDistributors = dd.findAuthorizedDistributors();
                        int randomIndex2 = random.nextInt(authorizedDistributors.size());
                        distributor = authorizedDistributors.get(randomIndex2);
                        System.out.println("Your distributor has ID: " + distributor.getId());
                        break;
                    case 0:
                        System.out.println("See you soon!");
                        return;
                }

                // richiesta dati per cercare user nel database
                System.out.println();
                System.out.println("Please insert your name: ");
                String name = scanner.nextLine().toLowerCase();
                System.out.println();
                System.out.println("Now, insert your surname");
                String surname = scanner.nextLine().toLowerCase();
                UserClass user = ud.findUserByNameAndSurname(name, surname);
                System.out.println("User found in our system!");

                System.out.println("In order to use our distributor, you must have a card.");
                System.out.println("Checking card details...\n");

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
                    if (LocalDate.now().isAfter(user.getCard().getExpiration_date())) {
                        System.out.println("Your card is expired. Renovating it...");
                        cd.renovateCard(user.getCard());
                        System.out.println("Card renovated!\n");
                    } else {
                        System.out.println("We found a valid card!\n");
                    }
                }
                // una volta che ci troviamo qui, siamo sicuri al 100% che abbiamo sia card attiva che user

                System.out.println("What do you wish to purchase?");
                System.out.println("1- Membership, 2- Single ticket");
                int membershipOrTicket = getUserChoice(1, 2);
                switch (membershipOrTicket) {
                    case 1:
                        System.out.println("Which kind of membership do you wish to purchase? ");
                        System.out.println("1-weekly, 2-monthly");
                        int periodicity = getUserChoice(1, 2);

                        // generazione membership a seconda della periodicity
                        switch (periodicity) {
                            case 1:
                                md.addMembershipToCard(user.getCard(), MembershipPeriodicity.WEEKLY, distributor);
                                System.out.println("7 days membership added to your card.");
                                break;
                            case 2:
                                md.addMembershipToCard(user.getCard(), MembershipPeriodicity.MONTHLY, distributor);
                                System.out.println("30 days membership added to your card.");
                                break;
                        }
                    case 2:
                        td.addTicketToCard(user.getCard(), distributor);
                        System.out.println("Ticket added to your card!");
                        break;
                }
                System.out.println("Do you wish to perform another action?");
                System.out.println("1- Yes, 2- No");

                int keepBuying = getUserChoice(1, 2);
                switch (keepBuying) {
                    case 1:
                        break;
                    case 2:
                        System.out.println("See you soon! :D \n");
                        return;
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

    public static void bookATravel() {
        while (true) {
            try {
                System.out.println("Hello, to book a travel you must be identified");
                System.out.println("If you want to exit, type 'exit'");

                System.out.println();
                System.out.println("Please insert your name: ");
                String name = scanner.nextLine().toLowerCase();

                if (name.equals("exit")) {
                    System.out.println("See you soon!");
                    return;
                }
                System.out.println();
                System.out.println("Now, insert your surname");
                String surname = scanner.nextLine().toLowerCase();
                UserClass user = ud.findUserByNameAndSurname(name, surname);
                System.out.println("User found in our system!");

                System.out.println("In order to use our distributor, you must have a card.");
                System.out.println("Checking card details...\n");

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
                    if (LocalDate.now().isAfter(user.getCard().getExpiration_date())) {
                        System.out.println("Your card is expired. Renovating it...");
                        cd.renovateCard(user.getCard());
                        System.out.println("Card renovated!\n");
                    } else {
                        System.out.println("We found a valid card!\n");
                    }
                }
                //arrivati a questo punto l'utente è identificato


                // COMMUTE ----- TRAVEL ----- TRANSPORTATION

                //for loop che itera sulle commute -> per ogni commute crea 1 viaggio ogni ora -> a questo dobbiamo collegare
                System.out.println();
                System.out.println("Where do you want to go?");
                List<Commute> commuteList = cmd.findAllCommutes();
                if (commuteList.isEmpty()) {
                    System.out.println("commutes not found!");
                    createCommutesAndTransportations();
                    generateTravelTable();
                }
                AtomicInteger counter = new AtomicInteger(1);
                commuteList.stream().map(Commute::getTerminal).forEach(terminal -> {
                    System.out.println(counter + "- " + terminal);
                    counter.addAndGet(1);
                });
                int destination = Integer.parseInt(scanner.nextLine());
                Commute commute = null;

                switch (destination) {

                    case 1:
                        commute = commuteList.get(0);

                        break;
                    case 2:
                        commute = commuteList.get(1);
                        break;
                    case 3:
                        commute = commuteList.get(2);
                        break;
                    case 4:
                        commute = commuteList.get(3);
                        break;
                    case 5:
                        commute = commuteList.get(4);
                        break;

                    default:
                        System.out.println("Sorry, commute not found!");

                }

                System.out.println("Your destination is: " + commute.getTerminal() + ". Departure is: " + commute.getDeparture());


                List<Travel> travelList = trd.findByCommute(commute);
                System.out.println();
                System.out.println("Which transportation do you wish to take?");
                System.out.println("1- Tram, 2- Bus");

                int transportationChoice = getUserChoice(1, 2);


                Transportation transportation = null;
                List<Travel> listOfTravel = null;
                LocalDateTime currentTime = LocalDateTime.now();
                switch (transportationChoice) {
                    case 1:
                        listOfTravel = travelList.stream()
                                .filter(travel -> travel.getTransportation().getType().equals(TransportationType.TRAM))
                                .collect(Collectors.toList());
                        break;


                    case 2:

                        listOfTravel = travelList.stream()
                                .filter(travel -> travel.getTransportation().getType().equals(TransportationType.BUS))
                                .collect(Collectors.toList());
                        break;


                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


                Optional<Travel> nextTravel = listOfTravel.stream().filter(travel -> travel.getDepartureTime().isAfter(currentTime)).min(Comparator.comparing(Travel::getDepartureTime));
                if (nextTravel.isPresent()) {
                    System.out.println("The next available travel is at: " + nextTravel.get().getDepartureTime().format(formatter));
                } else System.out.println("No available travels!");

                System.out.println("Do you want to use your membership or a ticket?");
                System.out.println("1 Membership, 2 Ticket");
                int input = getUserChoice(1, 2);
                switch (input) {
                    case 1:
                        boolean hasValidMembership = cd.isValidMembership(user.getCard());
                        if (!hasValidMembership) {
                            System.out.println("You should use a ticket");
                            Ticket ticket = cd.findValidTickets(user.getCard());

                        }
                    case 2:
                        Ticket ticket = cd.findValidTickets(user.getCard());
                        td.validateTicket(ticket, transportation);
                }
//                 richiesta dati per cercare user nel database
//                 una volta che ci troviamo qui, siamo sicuri al 100% che abbiamo sia card attiva che user

//                System.out.println("What do you wish to purchase?");
//                System.out.println("1- Membership, 2- Single ticket");
//                int membershipOrTicket = getUserChoice(1, 2);
//                switch (membershipOrTicket) {
//                    case 1:
//                        System.out.println("Which kind of membership do you wish to purchase? ");
//                        System.out.println("1-weekly, 2-monthly");
//                        int periodicity = getUserChoice(1, 2);
//
//                        // generazione membership a seconda della periodicity
//                        switch (periodicity) {
//                            case 1:
//                                md.addMembershipToCard(user.getCard(), MembershipPeriodicity.WEEKLY, distributor);
//                                System.out.println("7 days membership added to your card.");
//                                break;
//                            case 2:
//                                md.addMembershipToCard(user.getCard(), MembershipPeriodicity.MONTHLY, distributor);
//                                System.out.println("30 days membership added to your card.");
//                                break;
//                        }
//                    case 2:
//                        td.addTicketToCard(user.getCard(), distributor);
//                        System.out.println("Ticket added to your card!");
//                        break;
//                }
//                System.out.println("Do you wish to perform another action?");
//                System.out.println("1- Yes, 2- No");
//
//                int keepBuying = getUserChoice(1, 2);
//                switch (keepBuying) {
//                    case 1:
//                        break;
//                    case 2:
//                        System.out.println("See you soon! :D \n");
//                        return;
//                }

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

    public static void generateTravelTable() {
        List<Commute> commuteList = cmd.findAllCommutes();
        List<Transportation> transportationList = transportd.findAllTransportation();

        for (Commute indexedCommute : commuteList) {
            LocalDateTime departureTimeForAllTravels = LocalDateTime.now();
            System.out.println(departureTimeForAllTravels);
            for (int j = 0; j < 10; j++) {
                System.out.println(departureTimeForAllTravels);
                departureTimeForAllTravels = departureTimeForAllTravels.plusHours(1);
                long arrivalTime = random.nextLong(60, 300);
                Travel newTravel = new Travel(arrivalTime);
                newTravel.setDepartureTime(departureTimeForAllTravels);
                newTravel.setArrivalTime(LocalDateTime.now().plusMinutes(arrivalTime));
                Transportation randomTrans = transportationList.get(random.nextInt(0, transportationList.size()));
                newTravel.setCommute(indexedCommute);
                newTravel.setTransportation(randomTrans);
                trd.save(newTravel);
                System.out.println("VIAGGIO CREATO ");
            }
        }
    }

    public static void createCommutesAndTransportations() {
        // FUNZIONE PER RIEMPIRE IL DATABASE DI TRATTE E MEZZI DI TRASPORTO
        for (int i = 0; i < 5; i++) {
            Commute newCommute = Suppliers.commuteSupplier.get();
            cmd.save(newCommute);
            System.out.println("daje roma");
        }

        for (int i = 0; i < 10; i++) {
            Transportation newTransport = Suppliers.transportationSupplier.get();
            transportd.save(newTransport);
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

    public static void checkValidMembership() {
        try {
            System.out.println("Please insert your name: ");
            String name = scanner.nextLine().toLowerCase();
            System.out.println();
            System.out.println("Now, insert your surname");
            String surname = scanner.nextLine().toLowerCase();
            UserClass user = ud.findUserByNameAndSurname(name, surname);
            System.out.println("User found in our system!");
            cd.isValidMembership(user.getCard());

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
        }


    }

    public static void initializeDistributors() {
        for (int i = 0; i < 5; i++) {
            AuthorizedDistributor newDistributor = Suppliers.authorizedDistributorSupplier.get();
            dd.save(newDistributor);
            System.out.println("YIPPIEE");
        }

        for (int i = 0; i < 10; i++) {
            AutomaticDistributor newDistributor = Suppliers.automaticDistributorSupplier.get();
            dd.save(newDistributor);
            System.out.println("YIPPIEE");
        }
    }


    public static void App() {
//        createCommutesAndTransportations();
//        generateTravelTable();
//
//        initializeDistributors();


//        for (int i = 0; i < 10; i++) {
//            Commute newTransport = Suppliers.commuteSupplier.get();
//            cmd.save(newTransport);
//            System.out.println("daje roma");
//        }


//        Transportation newTransport = Suppliers.transportationSupplier.get();
//        DutyPeriod newDutyPeriod = new DutyPeriod(newTransport, LocalDate.now(), LocalDate.now().plusDays(7));
//
//        MaintenancePeriod newTrans = new MaintenancePeriod(newTransport, LocalDate.now().plusDays(7), LocalDate.now().plusDays(14));
//        transportd.save(newTransport);
//        dpd.save(newDutyPeriod);
//        mpd.save(newTrans);


//        System.out.println("eblla pe voi");

//commute ----- 1 roma tivoli

//        Travel newTravel = new Travel(60);
//        Transportation newTransport = Suppliers.transportationSupplier.get();
//        Commute newCommute = Suppliers.commuteSupplier.get();
//
//        transportd.save(newTransport);
//        cmd.save(newCommute);
//
//        System.out.println("La destinazione è " + newCommute.getTerminal());
//        System.out.println("Il mezzo ha id " + newTransport.getId());
//
//        newTravel.setCommute(newCommute);
//        newTravel.setTransportation(newTransport);
//
//
//        trd.save(newTravel);
//        System.out.println("Questi sono i tram");
//        transportd.findTram(TransportationType.TRAM).forEach(System.out::println);
//        System.out.println();
//        System.out.println();
//        System.out.println("Questi sono i bus");
//        transportd.findBus(TransportationType.BUS).forEach(System.out::println);
//
//
//        System.out.println("Welcome to our system!");
//        System.out.println();


//                cmd.findAllCommutes().stream().map(Commute::getTerminal).forEach(System.out::println);
        //abbiamo la lista di tratte
        //abbiamo la lista di mezzi

        //per ogni tratta - creare 10 viaggi, al viaggio creato - setcommute e set-transportation


        while (true) {
            System.out.println("Which operation do you wish to perform?");
            System.out.println("1-Create a new User, 2-Buy a membership, 3- Check your memberships, 4- book a travel");
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
                        checkValidMembership();
                        break;
                    case 4:
                        bookATravel();
                        break;

                    case 5:
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
