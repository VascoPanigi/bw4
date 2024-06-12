package team3.entities.distributor;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import team3.enums.DistributorTypes;

@Entity
@NamedQuery(name = "findAuthorizedDistributors", query = "SELECT a FROM AuthorizedDistributor a")

public class AuthorizedDistributor extends Distributor {
    public AuthorizedDistributor() {
        super(DistributorTypes.AUTHORIZED);
    }

    @Override
    public String toString() {
        return "AuthorizedDistributor{" +
                "id=" + id +
                ", type=" + type +
                '}';
    }
}
