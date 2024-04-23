package com.epam.rd.autocode.assessment.basics.entity;

import com.epam.rd.autocode.assessment.basics.entity.enums.AgeGroup;
import com.epam.rd.autocode.assessment.basics.entity.enums.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class GetSetTest {

    @Test
    @DisplayName("Book fields successfully tested")
    void testBook() {
        GetSetVerifier verifier = new GetSetVerifier(Book.class);
        verifier.verify("id", 1L, 2L);
        verifier.verify("name", "1984", "1724");
        verifier.verify("genre", "Horror", "Detective");
        verifier.verify("ageGroup", AgeGroup.ADULT, AgeGroup.CHILD);
        verifier.verify("price", BigDecimal.ONE, BigDecimal.ZERO);
        verifier.verify("publicationDate", LocalDate.now(), LocalDate.MIN);
        verifier.verify("author", "Alex", "Mark");
        verifier.verify("numberOfPages", 111, 222);
        verifier.verify("characteristics", "some", "info");
        verifier.verify("description", "some", "info");
        verifier.verify("language", Language.OTHER, Language.JAPANESE);
    }

    @Test
    @DisplayName("Order fields successfully tested")
    void testOrder() {
        GetSetVerifier verifier = new GetSetVerifier(Order.class);
        verifier.verify("id", 1L, 2L);
        verifier.verify("clientId", 1L, 2L);
        verifier.verify("employeeId", 1L, 2L);
        verifier.verify("bookId", 1L, 2L);
        verifier.verify("numberOfBooks", 2, 1);
        verifier.verify("orderDate", LocalDateTime.now(), LocalDateTime.now().plusSeconds(1500));
        verifier.verify("price", BigDecimal.ZERO, BigDecimal.ONE);
    }

    @Test
    @DisplayName("Client fields successfully tested")
    void testClient() {
        GetSetVerifier verifier = new GetSetVerifier(Client.class);
        verifier.verify("id", 1L, 2L);
        verifier.verify("email", "one", "two");
        verifier.verify("password", "one", "two");
        verifier.verify("balance", BigDecimal.ZERO, BigDecimal.ONE);
    }

    @Test
    @DisplayName("Employee fields successfully tested")
    void testEmployee() {
        GetSetVerifier verifier = new GetSetVerifier(Employee.class);
        verifier.verify("id", 1L, 2L);
        verifier.verify("email", "1@test.com", "2@gmail.com");
        verifier.verify("password", "qwerty", "aezakmi");
        verifier.verify("phone", "123456789", "987654321");
        verifier.verify("birthDate", LocalDate.now(), LocalDate.now().minusYears(12));
    }
}
