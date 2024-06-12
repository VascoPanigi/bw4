package team3.entities.distributor;

import jakarta.persistence.Entity;
import team3.enums.DistributorTypes;

@Entity
public class AutomaticDistributor extends Distributor {
    private Boolean in_service;

    public AutomaticDistributor() {
    }

    public AutomaticDistributor(Boolean in_service) {
        super(DistributorTypes.AUTOMATIC);
        this.in_service = in_service;
    }

    public Boolean getIn_service() {
        return in_service;
    }

    public void setIn_service(Boolean in_service) {
        this.in_service = in_service;
    }

}
