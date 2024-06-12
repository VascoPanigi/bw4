package team3.entities.transportation;

import jakarta.persistence.*;
import team3.entities.travel.Travel;
import team3.enums.TransportationState;
import team3.enums.TransportationType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
//@NamedQuery(name="findInServiceTransportation",  query = "SELECT t FROM Transportation t WHERE t.TransportationState = :TransportationState")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transportation")
public class Transportation {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TransportationType type;

    private int capacity;

    @Enumerated(EnumType.STRING)
    private TransportationState state;

    @OneToOne(mappedBy = "transportation", cascade = CascadeType.ALL)
    private DutyPeriod endutyPeriod;

    @OneToOne(mappedBy = "transportation", cascade = CascadeType.ALL)
    private MaintenancePeriod maintenancePeriod;

    @OneToMany(mappedBy = "transportation", cascade = CascadeType.ALL)
    private List<Travel> travels = new ArrayList<>();

    //@OneToMany --- vogliamo una lista di Tickets. finire di implementare manytoone da tickets su transportation_id

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public TransportationState getState() {
        return state;
    }

    public void setState(TransportationState state) {
        this.state = state;
    }

    public DutyPeriod getEndutyPeriod() {
        return endutyPeriod;
    }

    public void setEndutyPeriod(DutyPeriod endutyPeriod) {
        this.endutyPeriod = endutyPeriod;
    }

    public MaintenancePeriod getMaintenancePeriod() {
        return maintenancePeriod;
    }

    public void setMaintenancePeriod(MaintenancePeriod maintenancePeriod) {
        this.maintenancePeriod = maintenancePeriod;
    }
}
