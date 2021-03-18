package swt6.spring.worklog.domain.util;

import swt6.spring.worklog.domain.Address;

import java.io.Serializable;

public class AddressPK implements Serializable {
    private String zipCode;
    private String city;
    private String street;
    private String number;

    public AddressPK() {
    }

    public AddressPK(String zipCode, String city, String street, String number) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public AddressPK(Address address) {
        zipCode = address.getZipCode();
        city = address.getCity();
        street = address.getStreet();
        number = address.getNumber();
    }
}
