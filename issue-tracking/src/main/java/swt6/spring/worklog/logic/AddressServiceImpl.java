package swt6.spring.worklog.logic;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.AddressDao;
import swt6.spring.worklog.domain.Address;
import swt6.spring.worklog.domain.util.AddressPK;

import java.util.Optional;

@Transactional
public class AddressServiceImpl implements AddressService {
    private AddressDao addressDao;

    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public Address create(Address address) {
        return addressDao.save(address);
    }

    @Override
    public void delete(Address address) {
        addressDao.delete(address);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Address> find(AddressPK addressPK) {
        return addressDao.findById(addressPK);
    }
}
