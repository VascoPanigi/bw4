package team3.entities.transportation;

import jakarta.persistence.*;
import team3.entities.travel.Travel;
import team3.enums.TransportationType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
//@NamedQuery(name="findInServiceTransportation",  query = "SELECT t FROM Transportation t WHERE t.TransportationState = :TransportationState")

@NamedQueries({
        @NamedQuery(name = "findTramQuery", query = "SELECT t FROM Transportation t WHERE t.type = :tram"),
        @NamedQuery(name = "findBusQuery", query = "SELECT t FROM Transportation t WHERE t.type = :bus"),
        @NamedQuery(name = "findAllTransportation", query = "SELECT t FROM Transportation t"),
})
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transportation")
public class Transportation {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TransportationType type;

    private int capacity;

//    @Enumerated(EnumType.STRING)
//    private TransportationState state;

    @OneToMany(mappedBy = "transportation", cascade = CascadeType.ALL)
    private List<DutyPeriod> dutyPeriods = new ArrayList<>();

    @OneToMany(mappedBy = "transportation", cascade = CascadeType.ALL)
    private List<MaintenancePeriod> maintenancePeriods = new ArrayList<>();

    @OneToMany(mappedBy = "transportation", cascade = CascadeType.ALL)
    private List<Travel> travels = new ArrayList<>();

    //@OneToMany --- vogliamo una lista di Tickets. finire di implementare manytoone da tickets su transportation_id

    public Transportation(TransportationType type, int capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    public Transportation() {
    }

    public UUID getId() {
        return id;
    }

    public TransportationType getType() {
        return type;
    }

    public void setType(TransportationType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    @Override
    public String toString() {
        return "Transportation{" +
                "id=" + id +
                ", type=" + type +
                ", capacity=" + capacity +
                '}';
    }
}
