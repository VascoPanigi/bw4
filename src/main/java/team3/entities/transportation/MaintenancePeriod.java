package team3.entities.transportation;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "maintenancePeriod")
public class MaintenancePeriod {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "transportation_id")
    private Transportation transportation;


    private LocalDate startingDate;
    private LocalDate endingDate;


    public MaintenancePeriod(Transportation transportation, LocalDate startingDate, LocalDate endingDate) {
        this.transportation = transportation;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }

    public MaintenancePeriod() {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
