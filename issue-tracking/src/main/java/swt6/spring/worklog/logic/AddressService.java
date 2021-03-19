package swt6.spring.worklog.logic;

import swt6.spring.worklog.domain.Address;
import swt6.spring.worklog.domain.util.AddressPK;

import java.util.Optional;

public interface AddressService {
    Address create(Address address);
    void delete(Address address);
    Optional<Address> find(AddressPK addressPK);
}
