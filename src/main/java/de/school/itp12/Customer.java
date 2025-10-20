package de.school.itp12;

import java.time.LocalDate;
import java.util.UUID;

public class Customer {
    private UUID id;
    private String firstName;
    private String lastName;
    private ICustomer.Gender gender;
    private LocalDate birthDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ICustomer.Gender getGender() {
        return gender;
    }

    public void setGender(ICustomer.Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
