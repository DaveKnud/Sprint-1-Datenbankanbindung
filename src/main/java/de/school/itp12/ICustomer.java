package de.school.itp12;

import java.time.LocalDate;

public interface ICustomer {

    enum Gender {
        D, // divers
        M, // männlich
        U, // unbekannt
        W; // weiblich
    }

    LocalDate getBirthDate();

    String getFirstName();

    Gender getGender();

    String getLastName();

    void setBirthDate(LocalDate birtDate);

    void setFirstName(String firstName);

    void setGender(Gender gender);

    void setLastName(String lastName);


}
