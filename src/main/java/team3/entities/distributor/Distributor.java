package team3.entities.distributor;

import jakarta.persistence.*;
import team3.entities.travel_document.Membership;
import team3.entities.travel_document.Ticket;
import team3.enums.DistributorTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "distributor")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Distributor {
    @Id
    @GeneratedValue

    private UUID id;
    private DistributorTypes type;

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL)
    private List<Membership> memberships = new ArrayList<>();

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public Distributor() {
    }

    public Distributor(DistributorTypes type) {
        this.type = type;
    }

//    public List<Ticket> getTicketList() {
//        return ticketList;
//    }
//
//    public void setTicketList(List<Ticket> ticketList) {
//        this.ticketList = ticketList;
//    }

    public UUID getId() {
        return id;
    }

    public DistributorTypes getType() {
        return type;
    }

    public void setType(DistributorTypes type) {
        this.type = type;
    }
}
