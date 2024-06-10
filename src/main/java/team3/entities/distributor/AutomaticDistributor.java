package team3.entities.distributor;

import jakarta.persistence.Entity;

@Entity

public class AutomaticDistributor extends Distributor{

    private Boolean in_service;

    public AutomaticDistributor() {

    }

    public AutomaticDistributor(Boolean inService) {
        this.in_service = inService;

    }

    public Boolean getIn_service() {
        return in_service;
    }

    public void setIn_service(Boolean in_service) {
        this.in_service = in_service;
    }



}
