package team3.entities.transportation;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "maintenancePeriod")
public class MaintenancePeriod {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "transportation_id")

    private Transportation transportation;

    private LocalDate startingDate;
    private LocalDate endingDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }
}
