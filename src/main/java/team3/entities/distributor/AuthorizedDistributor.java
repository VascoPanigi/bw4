package team3.entities.distributor;

import jakarta.persistence.Entity;
import team3.enums.DistributorTypes;

@Entity

public class AuthorizedDistributor extends Distributor {
    public AuthorizedDistributor() {
        super(DistributorTypes.AUTHORIZED);
    }
}
