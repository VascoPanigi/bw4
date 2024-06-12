package team3.entities.travel;

import jakarta.persistence.*;
import team3.enums.TransportationType;

import java.util.UUID;

@Entity
@Table(name = "travel")
public class Travel {

    @Id
    @GeneratedValue
    private UUID id;

    private int duration;
    private String startingPoint;
    private String terminal;

    @Enumerated(EnumType.STRING)
    private TransportationType transportationType;

    public Travel(int duration, String startingPoint, String terminal, TransportationType transportationType) {
        this.duration = duration;
        this.startingPoint = startingPoint;
        this.terminal = terminal;
        this.transportationType = transportationType;
    }

    public Travel() {

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

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

}
