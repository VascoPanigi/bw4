package team3.entities.distributor;

import jakarta.persistence.*;
import team3.enums.DistributorTypes;

import java.util.UUID;

@Entity
@Table(name = "distributor")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Distributor {
    @Id
    @GeneratedValue

    private UUID id;
    private DistributorTypes type;


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
