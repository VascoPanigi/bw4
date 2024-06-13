package team3.entities.travel;

import jakarta.persistence.*;
import team3.entities.commute.Commute;
import team3.entities.transportation.Transportation;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity

@NamedQuery(name = "findByCommute", query = "SELECT t FROM Travel t WHERE t.commute = :commute ")
@Table(name = "travel")
public class Travel {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "transportation_id")
    private Transportation transportation;

    @ManyToOne
    @JoinColumn(name = "commute_id")
    private Commute commute;

    private long travelTime;

    public Travel(long travelTime) {
        this.travelTime = travelTime;
        this.departureTime = null;
        this.arrivalTime = null;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }

    public UUID getId() {
        return id;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public Commute getCommute() {
        return commute;
    }

    public void setCommute(Commute commute) {
        this.commute = commute;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
}
