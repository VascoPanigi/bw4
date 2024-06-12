package team3.entities.travel;

import jakarta.persistence.*;
import team3.enums.TransportationType;

@Entity
@Table(name = "travel")
public class Travel {
    private int number;
    private int duration;
    private String startingPoint;
    private String terminal;
    @Enumerated(EnumType.STRING)
    private TransportationType transportationType;

    public Travel(int duration, String startingPoint, String terminus, TransportationType transportationType) {
        this.duration = duration;
        this.startingPoint = startingPoint;
        this.terminal = terminus;
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
