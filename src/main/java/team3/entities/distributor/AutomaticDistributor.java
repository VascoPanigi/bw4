package team3.entities.distributor;

import jakarta.persistence.Entity;

@Entity

public class AutomaticDistributor extends Distributor{

    private Boolean inService;

    public AutomaticDistributor() {

    }

    public AutomaticDistributor(Boolean inService) {
        this.inService = inService;

    }

    public Boolean getInService() {
        return inService;
    }

    public void setInService(Boolean inService) {
        this.inService = inService;
    }



}
