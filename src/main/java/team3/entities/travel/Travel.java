package team3.entities.travel;

import jakarta.persistence.*;
import team3.entities.commute.Commute;
import team3.entities.transportation.Transportation;

import java.util.UUID;

@Entity
@Table(name = "travel")
public class Travel {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "transportation_id")
    private Transportation transportation;

    @ManyToOne
    @JoinColumn(name = "commute_id")
    private Commute commute;

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
