package swt6.spring.worklog.domain;

import swt6.spring.worklog.domain.util.AddressPK;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(AddressPK.class)
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String zipCode;
    @Id
    private String city;
    @Id
    private String street;
    @Id
    private String number;

    public Address() {
    }

    public Address(String zipCode, String city, String street, String number) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String toString() {
        return String.format("%s %s, %s %s", zipCode, city, street, number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return zipCode.equals(address.zipCode) && city.equals(address.city) && street.equals(address.street) && number.equals(address.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, city, street, number);
    }
}
