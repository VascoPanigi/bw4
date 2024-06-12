package team3.entities.commute;

import jakarta.persistence.*;
import team3.entities.transportation.Transportation;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "`commute`")
public class Commute {
    @Id
    @GeneratedValue
    private UUID commute_id;

    @Column(name = "departure")
    private String departure;

    @Column(name = "averageTravelTime")
    private int averageTravelTime;

    @Column(name = "terminal")
    private String terminal;

    @OneToMany(mappedBy = "commute", cascade = CascadeType.ALL)
    private List<Transportation> trasportations;

    public Commute() {
    }

    public Commute(String departure, int averageTravelTime, String terminal) {
        this.departure = departure;
        this.averageTravelTime = averageTravelTime;
        this.terminal = terminal;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public int getAverageTravelTime() {
        return averageTravelTime;
    }

    public void setAverageTravelTime(int averageTravelTime) {
        this.averageTravelTime = averageTravelTime;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }


}
