package VascoPanigi;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4");

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
