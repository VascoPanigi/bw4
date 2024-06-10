package team3.distributor;

import jakarta.persistence.*;
import ticket.Ticket;

import java.util.List;
import java.util.UUID;
@Entity
@Table(name= "distributor")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public  abstract class Distributor {

    @Id
    @GeneratedValue

  private UUID id;

   //private List<Ticket> ticketList;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

//    public List<Ticket> getTicketList() {
//        return ticketList;
//    }
//
//    public void setTicketList(List<Ticket> ticketList) {
//        this.ticketList = ticketList;
//    }

    public Distributor() {

    }

    public Distributor(UUID id) {
        this.id = id;

    }
}
