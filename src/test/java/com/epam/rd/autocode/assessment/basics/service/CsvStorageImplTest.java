package com.epam.rd.autocode.assessment.basics.service;

import com.epam.rd.autocode.assessment.basics.entity.Book;
import com.epam.rd.autocode.assessment.basics.entity.Client;
import com.epam.rd.autocode.assessment.basics.entity.Employee;
import com.epam.rd.autocode.assessment.basics.entity.Order;
import com.epam.rd.autocode.assessment.basics.entity.enums.AgeGroup;
import com.epam.rd.autocode.assessment.basics.entity.enums.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CsvStorageImplTest {

    static final String EOL = "\n";
    private static CsvStorage csvStorage;
    private static Mapper mapper;

    @BeforeEach
    void globalSetup() {
        mapper = new MapperImpl();
        csvStorage = new CsvStorageImpl();
    }

    public static Stream<Arguments> casesDefaultRead() {
        return Stream.of(
                Arguments.of(
                        """
                                id,email,password,name,balance
                                1,a@a.a,p,n,1
                                1,"","","",
                                ,,,,
                                """,
                        List.of(
                                new Client(1, "a@a.a", "p", "n", new BigDecimal("1")),
                                new Client(1, "", "", "", null),
                                new Client(0, null, null, null, null))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("casesDefaultRead")
    @DisplayName("Method read launched with default settings [csv->object conversion tested]")
    void testDefaultRead(String data, List<Client> expected) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data.getBytes())) {
            List<Client> clients = csvStorage.read(bis, mapper::csvToClient);
            assertIterableEquals(expected, clients,
                    "Some content are not equal. Check your realization.");
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesReadClient() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/clients.csv", "cp1251", "_", ";", "false",
                        List.of(
                                new Client(3, "Client15@acw.com", "i$25#21!ї", "Клієнт$12", new BigDecimal("1217")),
                                new Client(0, "", "", "", null),
                                new Client(0, null, null, null, null))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("casesReadClient")
    @DisplayName("Method read launched for Client [csv->object conversion tested]")
    void testReadClient(String fName,
                        String encoding,
                        String quoteCharacter,
                        String valuesDelimiter,
                        String headerLine,
                        List<Client> expected) {
        csvStorage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (FileInputStream in = new FileInputStream(fName)) {
            List<Client> actual = csvStorage.read(in, mapper::csvToClient);
            assertIterableEquals(expected, actual,
                    "Some content are not equal. Check your realization.");
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesReadEmployee() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/employees.csv", "cp1251", "'", ";", "false",
                        List.of(
                                new Employee(4, "rakili@vpa.com", "еY$60;25,IL", "Ракіль", "111-602-23-00", LocalDate.parse("1992-11-12")),
                                new Employee(0, "", "", "", "", null),
                                new Employee(0, null, null, null, null, null)
                        )
                ));
    }

    @ParameterizedTest
    @MethodSource("casesReadEmployee")
    @DisplayName("Method read launched for Employee [csv->object conversion tested]")
    void testReadEmployee(String fName,
                          String encoding,
                          String quoteCharacter,
                          String valuesDelimiter,
                          String headerLine,
                          List<Employee> expected) {
        csvStorage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (FileInputStream in = new FileInputStream(fName)) {
            List<Employee> employees = csvStorage.read(in, mapper::csvToEmployee);
            assertIterableEquals(expected, employees,
                    "Some content are not equal. Check your realization.");
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesReadBook() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/books.csv", "cp1251", "'", ";", "false",
                        List.of(
                                new Book(13, "The Stranger", "Philosophical Fiction",
                                        AgeGroup.ADULT, BigDecimal.valueOf(8.75), LocalDate.parse("2021-11-01"),
                                        "Albert Camus", 123, "Existentialism", "A mans sense of alienation",
                                        Language.FRENCH),
                                new Book(0, "", "",
                                        null, null, null,
                                        "", 0, "", "",
                                        null),
                                new Book(27, "Moby-Dick", "Adventure",
                                        AgeGroup.ADULT, BigDecimal.valueOf(13.52), LocalDate.parse("2022-03-08"),
                                        "Herman Melville", 625, "Whaling", "The pursuit of the white whale",
                                        Language.ENGLISH),
                                new Book(0, null, null,
                                        null, null, null,
                                        null, 0, null, null,
                                        null)
                        ))
        );
    }

    @ParameterizedTest
    @MethodSource("casesReadBook")
    @DisplayName("Method read launched for Book [csv->object conversion tested]")
    void testReadBook(String fName,
                      String encoding,
                      String quoteCharacter,
                      String valuesDelimiter,
                      String headerLine,
                      List<Book> expected) {
        csvStorage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (FileInputStream in = new FileInputStream(fName)) {
            List<Book> actual = csvStorage.read(in, mapper::csvToBook);
            assertIterableEquals(expected, actual,
                    "Some content are not equal. Check your realization.");
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesReadOrder() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/orders.csv", "cp1251", "'", ";", "false",
                        List.of(
                                new Order(4, 3, 1, 5, 20, LocalDateTime.parse("2021-11-11T10:10"), new BigDecimal("140")),
                                new Order(0, 0, 0, 0, 0, null, null),
                                new Order(0, 0, 0, 0, 0, null, null)
                        )
                ));
    }

    @ParameterizedTest
    @MethodSource("casesReadOrder")
    @DisplayName("Method read launched for Order [csv->object conversion tested]")
    void testReadOrder(String fName,
                       String encoding,
                       String quoteCharacter,
                       String valuesDelimiter,
                       String headerLine,
                       List<Order> expected) {
        csvStorage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (FileInputStream in = new FileInputStream(fName)) {
            List<Order> actual = csvStorage.read(in, mapper::csvToOrder);
            assertIterableEquals(expected, actual,
                    "Some content are not equal. Check your realization.");
        } catch (IOException e) {
            fail(e);
        }
    }


    public static Stream<Arguments> casesDefaultWrite() {
        return Stream.of(
                Arguments.of(
                        "1,a@a.a,p,n,1" + EOL +
                                "1,\"\",\"\",\"\"," + EOL +
                                ",,,,",
                        List.of(
                                new Client(1, "a@a.a", "p", "n", new BigDecimal("1")),
                                new Client(1, "", "", "", null),
                                new Client(0, null, null, null, null))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("casesDefaultWrite")
    @DisplayName("Method write launched with default settings [object->csv conversion tested]")
    void testDefaultWrite(String expected, List<Client> data) {
        try (ByteArrayOutputStream bis = new ByteArrayOutputStream()) {
            csvStorage.write(bis, data, mapper::clientToCsv);
            assertEquals(expected, bis.toString(),
                    "Some content are not equal. Check your realization.");
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesWriteClient() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/clientsw.csv",
                        "cp1251", "_", ";", "false",
                        List.of(
                                new Client(3, "Client15@acw.com", "i$25#21!ї", "_Клієнт$12_", new BigDecimal("1217")),
                                new Client(0, "__", "__", "__", null),
                                new Client(0, null, null, null, null))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("casesWriteClient")
    @DisplayName("Method write launched for Client [object->csv conversion tested]")
    void testWriteClient(String fName,
                         String encoding,
                         String quoteCharacter,
                         String valuesDelimiter,
                         String headerLine,
                         List<Client> clients) {
        csvStorage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            csvStorage.write(out, clients, mapper::clientToCsv);
            out.flush();
            String actual = out.toString(Charset.forName(encoding));
            String expected = Files.readString(Path.of(fName), Charset.forName(encoding));
            assertEquals(expected, actual,
                    "Some content are not equal. Check your realization.");
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesWriteEmployee() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/employeesw.csv",
                        "cp1251", "'", ";", "false",
                        List.of(
                                new Employee(4, "rakili@vpa.com", "'еY$60;25,IL'", "Ракіль", "111-602-23-00", LocalDate.parse("1992-11-12")),
                                new Employee(0, "''", "''", "''", "''", null),
                                new Employee(0, null, null, null, null, null)
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("casesWriteEmployee")
    @DisplayName("Method write launched for Employee [object->csv conversion tested]")
    void testWriteEmployee(String fName,
                           String encoding,
                           String quoteCharacter,
                           String valuesDelimiter,
                           String headerLine,
                           List<Employee> employees) {
        csvStorage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            csvStorage.write(out, employees, mapper::employeeToCsv);
            out.flush();
            String actual = out.toString(Charset.forName(encoding));
            String expected = Files.readString(Path.of(fName), Charset.forName(encoding));
            assertEquals(expected, actual,
                    "Some content are not equal. Check your realization.");
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesWriteBook() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/booksw.csv", "cp1251", "'", ";", "false",
                        List.of(
                                new Book(13, "The Stranger", "Philosophical Fiction",
                                        AgeGroup.ADULT, BigDecimal.valueOf(8.75), LocalDate.parse("2021-11-01"),
                                        "Albert Camus", 123, "Existentialism", "A mans sense of alienation",
                                        Language.FRENCH),
                                new Book(27, "Moby-Dick", "Adventure",
                                        AgeGroup.ADULT, BigDecimal.valueOf(13.52), LocalDate.parse("2022-03-08"),
                                        "'Herman Melville'", 625, "Whaling", "'The pursuit of the white whale'",
                                        Language.ENGLISH),
                                new Book(0, "", "",
                                        null, null, null,
                                        "", 0, "", "",
                                        null),
                                new Book(0, null, null,
                                        null, null, null,
                                        null, 0, null, null,
                                        null)
                        ))
        );
    }

    @ParameterizedTest
    @MethodSource("casesWriteBook")
    @DisplayName("Method write launched for Book [object->csv conversion tested]")
    void testWriteBook(String fName,
                       String encoding,
                       String quoteCharacter,
                       String valuesDelimiter,
                       String headerLine,
                       List<Book> books) {
        csvStorage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            csvStorage.write(out, books, mapper::bookToCsv);
            out.flush();
            String actual = out.toString(Charset.forName(encoding));
            String expected = Files.readString(Path.of(fName), Charset.forName(encoding));
            assertEquals(expected, actual,
                    "Some content are not equal. Check your realization.");
        } catch (IOException e) {
            fail(e);
        }
    }

    public static Stream<Arguments> casesWriteOrder() {
        return Stream.of(
                Arguments.of(
                        "src/test/resources/service/ordersw.csv", "cp1251", "'", ";", "false",
                        List.of(
                                new Order(4, 3, 1, 5, 20, LocalDateTime.parse("2021-11-11T10:10"), new BigDecimal("140")),
                                new Order(0, 0, 0, 0, 0, null, null),
                                new Order(0, 0, 0, 0, 0, null, null)
                        )
                ));
    }

    @ParameterizedTest
    @MethodSource("casesWriteOrder")
    @DisplayName("Method write launched for Order [object->csv conversion tested]")
    void testWriteOrder(String fName,
                        String encoding,
                        String quoteCharacter,
                        String valuesDelimiter,
                        String headerLine,
                        List<Order> orders) {
        csvStorage = new CsvStorageImpl(Map.of("encoding", encoding,
                "quoteCharacter", quoteCharacter,
                "valuesDelimiter", valuesDelimiter,
                "headerLine", headerLine));
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            csvStorage.write(out, orders, mapper::orderToCsv);
            out.flush();
            String actual = out.toString(Charset.forName(encoding));
            String expected = Files.readString(Path.of(fName), Charset.forName(encoding));
            assertEquals(expected, actual,
                    "Some content are not equal. Check your realization.");
        } catch (IOException e) {
            fail(e);
        }
    }
}
