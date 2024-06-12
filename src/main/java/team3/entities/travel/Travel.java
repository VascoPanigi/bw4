package team3.entities.travel;

import jakarta.persistence.*;
import team3.entities.commute.Commute;
import team3.entities.transportation.Transportation;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "travel")
public class Travel {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "travel_id")
    private List<Commute> commutes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "travel_id")
    private List<Transportation> transportations;
    //  @ManyToOne
    //  @JoinColumn(name = "commute")
    //  private Commute commute;
    //  private Transportation transportation;
    private int travelTime;

    public Travel(int travelTime) {

        this.travelTime = travelTime;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

}
