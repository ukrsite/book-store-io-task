package com.epam.rd.autocode.assessment.basics.entity;

import com.epam.rd.autocode.assessment.basics.entity.enums.AgeGroup;
import com.epam.rd.autocode.assessment.basics.entity.enums.Language;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RealDataTest {

    static final Locale DEFAULT_LOCALE = Locale.getDefault();
    static final TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();

    @BeforeAll
    static void setUp() {
        Locale.setDefault(new Locale("en"));
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @AfterAll
    static void tearDown() {
        Locale.setDefault(DEFAULT_LOCALE);
        TimeZone.setDefault(DEFAULT_TIMEZONE);
    }

    @ParameterizedTest
    @DisplayName("Employee values successfully tested")
    @CsvFileSource(files = "src/test/resources/entity/employee.csv", numLinesToSkip = 1)
    void testEmployee(long id, String email, String password, String name,
                      String phone, String dateOfBirth, String expected) {
        LocalDate date = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String actual = new Employee(id, email, password, name, phone, date).toString();
        assertEquals(expected, actual);

    }

    @ParameterizedTest
    @DisplayName("Client values successfully tested")
    @CsvFileSource(files = "src/test/resources/entity/client.csv", numLinesToSkip = 1)
    void testClient(long id, String email, String password, String name,
                    BigDecimal balance, String expected) {
        String actual = new Client(id, email, password, name, balance).toString();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Order values successfully tested")
    @CsvFileSource(files = "src/test/resources/entity/order.csv", numLinesToSkip = 1)
    void testOrder(long id, long clientId, long employeeId, long bookId,
                   int numberOfBooks, String orderDate, BigDecimal price, String expected) {
        LocalDateTime od = LocalDateTime.parse(orderDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        String actual = new Order(id, clientId, employeeId, bookId, numberOfBooks, od, price).toString();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Book values successfully tested")
    @CsvFileSource(files = "src/test/resources/entity/book.csv", numLinesToSkip = 1)
    void testBook(long id, String name, String genre, AgeGroup ageGroup,
                  BigDecimal price, String publicationYear, String author, int numberOfPages,
                  String characteristics, String description, Language language, String expected) {
        LocalDate date = LocalDate.parse(publicationYear, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String actual = new Book(id, name, genre, ageGroup, price, date, author, numberOfPages, characteristics, description, language).toString();
        assertEquals(expected, actual);
    }
}
