package team3.entities.distributor;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import team3.enums.DistributorTypes;

@Entity
@NamedQuery(name = "findAutomaticDistributors", query = "SELECT a FROM AutomaticDistributor a")
@NamedQuery(name = "findAllDistributorsInService", query = "SELECT a FROM AutomaticDistributor a WHERE a.in_service = true")
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

    @Override
    public String toString() {
        return "AutomaticDistributor{" +
                "in_service=" + in_service +
                ", id=" + id +
                ", type=" + type +
                '}';
    }
}
