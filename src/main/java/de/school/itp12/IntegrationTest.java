package de.school.itp12;
import  org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {

    @Test
    void testGesamtablauf(){
        Reading a=ReadingProvider.read(UUID.fromString("1"));


    }

}
