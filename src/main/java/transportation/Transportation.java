package transportation;


import jakarta.persistence.*;

@Entity
@Table(name = "transportation")
public class Transportation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TransportationType type;

    private int capacity;

    @Enumerated(EnumType.STRING)
    private TransportationState state;

    @OneToOne(mappedBy = "transportation", cascade = CascadeType.ALL)
    private EndutyPeriod endutyPeriod;

    @OneToOne(mappedBy = "transportation", cascade = CascadeType.ALL)
    private MaintenancePeriod maintenancePeriod;



    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public EndutyPeriod getEndutyPeriod() {
        return endutyPeriod;
    }

    public void setEndutyPeriod(EndutyPeriod endutyPeriod) {
        this.endutyPeriod = endutyPeriod;
    }

    public MaintenancePeriod getMaintenancePeriod() {
        return maintenancePeriod;
    }

    public void setMaintenancePeriod(MaintenancePeriod maintenancePeriod) {
        this.maintenancePeriod = maintenancePeriod;
    }
}