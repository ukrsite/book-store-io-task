package com.epam.rd.autocode.assessment.basics.service;

import com.epam.rd.autocode.assessment.basics.entity.Book;
import com.epam.rd.autocode.assessment.basics.entity.Client;
import com.epam.rd.autocode.assessment.basics.entity.Employee;
import com.epam.rd.autocode.assessment.basics.entity.Order;
import com.epam.rd.autocode.assessment.basics.entity.enums.AgeGroup;
import com.epam.rd.autocode.assessment.basics.entity.enums.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperTest {
    private static Mapper mapper;

    @BeforeAll
    static void globalSetup() {
        mapper = new MapperImpl();
    }

    public static Stream<Arguments> casesCsvToClient() {
        return Stream.of(
                Arguments.of(new String[]{"1", "a@a.a", "p", "n", "1"},
                        new Client(1, "a@a.a", "p", "n", new BigDecimal("1"))),
                Arguments.of(new String[]{"666555444", "Client11@bbs.com", "A%7422$S", "Client11", "1762"},
                        new Client(666555444, "Client11@bbs.com", "A%7422$S", "Client11", new BigDecimal("1762"))),
                Arguments.of(new String[]{"", "\"\"", "\"\"", "\"\"", ""},
                        new Client(0, "", "", "", null)),
                Arguments.of(new String[]{"", "", "", "", ""},
                        new Client(0, null, null, null, null)),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("casesCsvToClient")
    @DisplayName("Method MapperImpl.csvToClient launched [csv-> object conversion tested]")
    void csvToClient(String[] values, Client expected) {
        assertEquals(expected, mapper.csvToClient(values),
                "Some content are not equal. Check your realization.");
    }

    public static Stream<Arguments> casesCsvToEmployee() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new String[]{"1", "a@a.a", "p", "n", "1", "1111-12-22"},
                        new Employee(1, "a@a.a", "p", "n", "1", LocalDate.parse("1111-12-22"))),
                Arguments.of(new String[]{"1", "admin@vpa.com", "kY60#25#IL", "Admin", "111-602-23-00", "1996-07-03"},
                        new Employee(1, "admin@vpa.com", "kY60#25#IL", "Admin", "111-602-23-00", LocalDate.parse("1996-07-03"))),
                Arguments.of(new String[]{"", "\"\"", "\"\"", "\"\"", "\"\"", ""},
                        new Employee(0, "", "", "", "", null)),
                Arguments.of(new String[]{"", "", "", "", "", ""},
                        new Employee(0, null, null, null, null, null))
        );
    }

    @ParameterizedTest
    @MethodSource("casesCsvToEmployee")
    @DisplayName("Method MapperImpl.csvToEmployee launched [csv-> object conversion tested]")
    void csvToEmployee(String[] values, Employee expected) {
        assertEquals(expected, mapper.csvToEmployee(values),
                "Some content are not equal. Check your realization.");
    }

    public static Stream<Arguments> casesCsvToBook() {
        return Stream.of(
                Arguments.of(new String[]{"1", "The Great Gatsby", "Classics", "ADULT", "12.99", "2022-01-18", "F. Scott Fitzgerald", "180", "Roaring Twenties", "A tragic love story", "ENGLISH"},
                        new Book(1, "The Great Gatsby", "Classics", AgeGroup.ADULT, new BigDecimal("12.99"), LocalDate.parse("2022-01-18"), "F. Scott Fitzgerald", 180, "Roaring Twenties", "A tragic love story", Language.ENGLISH)),
                Arguments.of(new String[]{"10", "The Metamorphosis", "Classics", "ADULT", "7.50", "2022-01-08", "Franz Kafka", "55", "Existentialism", "A man transformation into an insect", "GERMAN"},
                        new Book(10, "The Metamorphosis", "Classics", AgeGroup.ADULT, new BigDecimal("7.50"), LocalDate.parse("2022-01-08"), "Franz Kafka", 55, "Existentialism", "A man transformation into an insect", Language.GERMAN)),
                Arguments.of(new String[]{"", "\"\"", "\"\"", "", "", "", "\"\"", "", "\"\"", "\"\"", ""},
                        new Book(0, "", "", null, null, null, "", 0, "", "", null)),
                Arguments.of(new String[]{"", "", "", "", "", "", "", "", "", "", ""},
                        new Book(0, null, null, null, null, null, null, 0, null, null, null)),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("casesCsvToBook")
    @DisplayName("Method MapperImpl.csvToBook launched [csv-> object conversion tested]")
    void csvToBook(String[] values, Book expected) {
        assertEquals(expected, mapper.csvToBook(values),
                "Some content are not equal. Check your realization.");
    }

    public static Stream<Arguments> casesCsvToOrder() {
        return Stream.of(
                Arguments.of(new String[]{"1", "1", "1", "1", "1", "2023-11-13T12:34:56", "1"},
                        new Order(1, 1, 1, 1, 1, LocalDateTime.parse("2023-11-13T12:34:56"), new BigDecimal("1"))),
                Arguments.of(new String[]{"2", "987654321", "123456789", "2", "3", "2023-11-13T13:45:30", "45.67"},
                        new Order(2, 987654321, 123456789, 2, 3, LocalDateTime.parse("2023-11-13T13:45:30"), new BigDecimal("45.67"))),
                Arguments.of(new String[]{"", "", "", "", "", "", ""},
                        new Order(0, 0, 0, 0, 0, null, null)),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("casesCsvToOrder")
    @DisplayName("Method MapperImpl.csvToOrder launched [csv-> object conversion tested]")
    void csvToOrder(String[] values, Order expected) {
        assertEquals(expected, mapper.csvToOrder(values),
                "Some content are not equal. Check your realization.");
    }

    public static Stream<Arguments> casesOrderToCsv() {
        return Stream.of(
                Arguments.of(new Order(1, 1, 1, 1, 1, LocalDateTime.parse("2023-11-13T12:34:56"), new BigDecimal("1")),
                        new String[]{"1", "1", "1", "1", "1", "2023-11-13T12:34:56", "1"}),
                Arguments.of(new Order(2, 987654321, 123456789, 2, 3, LocalDateTime.parse("2023-11-13T13:45:30"), new BigDecimal("45.67")),
                        new String[]{"2", "987654321", "123456789", "2", "3", "2023-11-13T13:45:30", "45.67"}),
                Arguments.of(new Order(0, 0, 0, 0, 0, null, null),
                        new String[]{"", "", "", "", "", "", ""}),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("casesOrderToCsv")
    @DisplayName("Method MapperImpl.orderToCsv launched [object -> csv conversion tested]")
    void orderToCsv(Order value, String[] expected) {
        assertArrayEquals(expected, mapper.orderToCsv(value),
                "Some content are not equal. Check your realization.");
    }

    public static Stream<Arguments> casesBookToCsv() {
        return Stream.of(
                Arguments.of(new Book(1, "The Great Gatsby", "Classics", AgeGroup.ADULT, new BigDecimal("12.99"), LocalDate.parse("2022-01-18"), "F. Scott Fitzgerald", 180, "Roaring Twenties", "A tragic love story", Language.ENGLISH),
                        new String[]{"1", "The Great Gatsby", "Classics", "ADULT", "12.99", "2022-01-18", "F. Scott Fitzgerald", "180", "Roaring Twenties", "A tragic love story", "ENGLISH"}),
                Arguments.of(new Book(10, "The Metamorphosis", "Classics", AgeGroup.ADULT, new BigDecimal("7.50"), LocalDate.parse("2022-01-08"), "Franz Kafka", 55, "Existentialism", "A man transformation into an insect", Language.GERMAN),
                        new String[]{"10", "The Metamorphosis", "Classics", "ADULT", "7.50", "2022-01-08", "Franz Kafka", "55", "Existentialism", "A man transformation into an insect", "GERMAN"}),
                Arguments.of(new Book(0, "", "", null, null, null, "", 0, "", "", null),
                        new String[]{"", "\"\"", "\"\"", "", "", "", "\"\"", "", "\"\"", "\"\"", ""}),
                Arguments.of(new Book(0, null, null, null, null, null, null, 0, null, null, null),
                        new String[]{"", "", "", "", "", "", "", "", "", "", ""}),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("casesBookToCsv")
    @DisplayName("Method MapperImpl.bookToCsv launched [object -> csv conversion tested]")
    void bookToCsv(Book value, String[] expected) {
        assertArrayEquals(expected, mapper.bookToCsv(value),
                "Some content are not equal. Check your realization.");
    }

    public static Stream<Arguments> casesClientToCsv() {
        return Stream.of(
                Arguments.of(new Client(1, "a@a.a", "p", "n", new BigDecimal("1")),
                        new String[]{"1", "a@a.a", "p", "n", "1"}),
                Arguments.of(new Client(666555444, "Client11@bbs.com", "A%7422$S", "Client11", new BigDecimal("1762")),
                        new String[]{"666555444", "Client11@bbs.com", "A%7422$S", "Client11", "1762"}),
                Arguments.of(new Client(0, "", "", "", null),
                        new String[]{"", "\"\"", "\"\"", "\"\"", ""}),
                Arguments.of(new Client(0, null, null, null, null),
                        new String[]{"", "", "", "", ""}),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("casesClientToCsv")
    @DisplayName("Method MapperImpl.clientToCsv launched [object -> csv conversion tested]")
    void clientToCsv(Client value, String[] expected) {
        assertArrayEquals(expected, mapper.clientToCsv(value),
                "Some content are not equal. Check your realization.");
    }

    public static Stream<Arguments> casesEmployeeToCsv() {
        return Stream.of(
                Arguments.of(new Employee(1, "a@a.a", "p", "n", "1", LocalDate.parse("1111-12-22")),
                        new String[]{"1", "a@a.a", "p", "n", "1", "1111-12-22"}),
                Arguments.of(new Employee(1, "admin@vpa.com", "kY60#25#IL", "Admin", "111-602-23-00", LocalDate.parse("1996-07-03")),
                        new String[]{"1", "admin@vpa.com", "kY60#25#IL", "Admin", "111-602-23-00", "1996-07-03"}),
                Arguments.of(new Employee(0, "", "", "", "", null),
                        new String[]{"", "\"\"", "\"\"", "\"\"", "\"\"", ""}),
                Arguments.of(new Employee(0, null, null, null, null, null),
                        new String[]{"", "", "", "", "", ""}),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("casesEmployeeToCsv")
    @DisplayName("Method MapperImpl.employeeToCsv launched [object -> csv conversion tested]")
    void employeeToCsv(Employee value, String[] expected) {
        assertArrayEquals(expected, mapper.employeeToCsv(value),
                "Some content are not equal. Check your realization.");
    }
}
