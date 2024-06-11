package team3.entities.travel;

import jakarta.persistence.*;
import team3.entities.transportation.TransportationType;

@Entity
@Table(name = "travel")
public class Travel {
    private int number;
    private int duration;
    private String startingPoint;
    private String terminus;
    private TransportationType transportationType;

    public Travel(int number, int duration, String startingPoint, String terminus, TransportationType transportationType) {
        this.number = number;
        this.duration = duration;
        this.startingPoint = startingPoint;
        this.terminus = terminus;
        this.transportationType = transportationType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @ManyToOne
    public TransportationType getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(TransportationType transportationType) {
        this.transportationType = transportationType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getTerminus() {
        return terminus;
    }

    public void setTerminus(String terminus) {
        this.terminus = terminus;
    }

}
