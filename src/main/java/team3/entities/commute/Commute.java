package team3.entities.commute;

import jakarta.persistence.*;
import team3.entities.travel.Travel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NamedQueries({
        @NamedQuery(name = "findAllCommutes", query = "SELECT c FROM Commute c"),
})
@Table(name = "commute")
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
    private List<Travel> travels = new ArrayList<>();

    public Commute() {
    }

    public Commute(String departure, String terminal) {
        this.departure = departure;
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

    public UUID getCommute_id() {
        return commute_id;
    }

    @Override
    public String toString() {
        return "Commute{" +
                "commute_id=" + commute_id +
                ", departure='" + departure + '\'' +
                ", averageTravelTime=" + averageTravelTime +
                ", terminal='" + terminal + '\'' +
                '}';
    }
}
