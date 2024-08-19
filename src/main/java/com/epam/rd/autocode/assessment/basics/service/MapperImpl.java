package com.epam.rd.autocode.assessment.basics.service;

import com.epam.rd.autocode.assessment.basics.entity.Book;
import com.epam.rd.autocode.assessment.basics.entity.Client;
import com.epam.rd.autocode.assessment.basics.entity.Employee;
import com.epam.rd.autocode.assessment.basics.entity.Order;
import com.epam.rd.autocode.assessment.basics.entity.enums.AgeGroup;
import com.epam.rd.autocode.assessment.basics.entity.enums.Language;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class MapperImpl implements Mapper{
  @Override
  public Client csvToClient(String[] values) {
    /*Gets line from csv file with content appropriate to the Client fields.
    It parses text representation of values and converts to proper data types according to
    the requirements in Details section.*/
    if (values == null || (values.length == 0)) {
      return null;
    }

    try {
      long id = parseLong(values[0]);
      String email = parseString(values[1]);
      String password = parseString(values[2]);
      String name = parseString(values[3]);
      BigDecimal balance = parseBigDecimal(values.length == 5  ? values[4] : null);

      return new Client(id, email, password, name, balance);

    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error parsing numeric value", e);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Error parsing date value", e);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Error parsing enum value", e);
    }
  }

  @Override
  public Employee csvToEmployee(String[] values) {
    /*Gets line from csv file with content appropriate to the Employee fields.
    It parses text representation of values and converts to proper data types
    according to the requirements in Details section.*/
    if (values == null || (values.length == 0)) {
      return null;
    }

    try {
      long id = parseLong(values[0]);
      String email = parseString(values[1]);
      String password = parseString(values[2]);
      String name = parseString(values[3]);
      String phone = parseString(values[4]);
      LocalDate birthDate = parseDate(values.length == 6 ? values[5] : null);

      return new Employee(id, email, password, name, phone, birthDate);

    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error parsing numeric value", e);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Error parsing date value", e);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Error parsing enum value", e);
    }
  }

  @Override
  public Book csvToBook(String[] values) {
    if (values == null || (values.length == 0)) {
      return null;
    }

    try {
      long id = parseLong(values[0]);
      String name = parseString(values[1]);
      String genre = parseString(values[2]);
      AgeGroup ageGroup = parseEnum(AgeGroup.class, values[3]);
      BigDecimal price = parseBigDecimal(values[4]);
      LocalDate publicationDate = parseDate(values[5]);
      String author = parseString(values[6]);
      int numberOfPages = parseInt(values[7]);
      String characteristics = parseString(values[8]);
      String description = parseString(values[9]);
      Language language = parseEnum(Language.class, values.length == 11  ? values[10] : null);

      return new Book(id, name, genre, ageGroup, price, publicationDate, author, numberOfPages, characteristics, description, language);

    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error parsing numeric value", e);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Error parsing date value", e);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Error parsing enum value", e);
    }
  }

  @Override
  public Order csvToOrder(String[] values) {
    if (values == null || (values.length == 0)) {
      return null;
    }

    try {
      long id = parseLong(values[0]);
      long clientId = parseLong(values[1]);
      long employeeId = parseLong(values[2]);
      long bookId = parseLong(values[3]);
      int numberOfBooks = parseInt(values[4]);
      LocalDateTime orderDate = parseDateTime(values[5]);
      BigDecimal price = parseBigDecimal(values[6]);

      return new Order(id, clientId, employeeId, bookId, numberOfBooks, orderDate, price);

    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error parsing numeric value", e);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Error parsing date value", e);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Error parsing enum value", e);
    }
  }

  @Override
  public String[] orderToCsv(Order order) {
    if (order == null) {
      return null;
    }

    String[] csvFields = new String[7];

    csvFields[0] = valueToString(order.getId());
    csvFields[1] = valueToString(order.getClientId());
    csvFields[2] = valueToString(order.getEmployeeId());
    csvFields[3] = valueToString(order.getBookId());
    csvFields[4] = valueToString(order.getNumberOfBooks());
    csvFields[5] = valueToString(order.getOrderDate());
    csvFields[6] = valueToString(order.getPrice());

    return csvFields;
  }

  @Override
  public String[] bookToCsv(Book book) {
    if (book == null) {
      return null;
    }

    String[] csvFields = new String[11];

    csvFields[0] = valueToString(book.getId());
    csvFields[1] = valueToString(book.getName());
    csvFields[2] = valueToString(book.getGenre());
    csvFields[3] = valueToString(book.getAgeGroup());
    csvFields[4] = valueToString(book.getPrice());
    csvFields[5] = valueToString(book.getPublicationDate());
    csvFields[6] = valueToString(book.getAuthor());
    csvFields[7] = valueToString(book.getNumberOfPages());
    csvFields[8] = valueToString(book.getCharacteristics());
    csvFields[9] = valueToString(book.getDescription());
    csvFields[10] = valueToString(book.getLanguage());

    return csvFields;
  }

  private <T> String valueToString(T value) {
    if (value == null || value.equals(0)) {
      return "";
    }

    if (value instanceof Long && ((long) value) == 0) {
      return "";
    }

    if (value instanceof String && ((String) value).isEmpty()) {
      return "\"\"";
    }

    return value.toString();
  }

  @Override
  public String[] clientToCsv(Client client) {
    /*Gets object of Client with some filled parameters.
    It parses text representation of values and converts to proper data types
    according to the requirements in Details section.*/
    if (client == null) {
      return null;
    }

    String[] csvFields = new String[5];

    csvFields[0] = valueToString(client.getId());
    csvFields[1] = valueToString(client.getEmail());
    csvFields[2] = valueToString(client.getPassword());
    csvFields[3] = valueToString(client.getName());
    csvFields[4] = valueToString(client.getBalance());

    return csvFields;
  }

  @Override
  public String[] employeeToCsv(Employee employee) {
  /*Gets object of Employee with some filled parameters.
  It parses text representation of values and converts to proper data types
  according to the requirements in Details section.*/
    if (employee == null) {
      return null;
    }

    String[] csvFields = new String[6];

    if (employee.getId() == 0 && employee.getName() != null && !employee.getName().isEmpty()) {
      csvFields[0] = "0";
    } else {
      csvFields[0] = valueToString(employee.getId());
    }
    csvFields[1] = valueToString(employee.getEmail());
    csvFields[2] = valueToString(employee.getPassword());
    csvFields[3] = valueToString(employee.getName());
    csvFields[4] = valueToString(employee.getPhone());
    csvFields[5] = valueToString(employee.getBirthDate());



    return csvFields;
  }
  private long parseLong(String value) {
    return value == null || value.trim().isEmpty() || value.trim().equals("''") ? 0 : Long.parseLong(value.trim());
  }

  private int parseInt(String value) {
    return value == null || value.trim().isEmpty() || value.trim().equals("''") ? 0 : Integer.parseInt(value.trim());
  }

  private BigDecimal parseBigDecimal(String value) {
    return value == null || value.trim().isEmpty() || value.trim().equals("''") ? null : new BigDecimal(value.trim());
  }

  private String parseString(String value) {
    if (value == null || value.isEmpty()) return null;
    value = (value.equals("\"\"") || value.equals("\'\'") ? "" : value.trim());

    return value;
  }

  private <E extends Enum<E>> E parseEnum(Class<E> enumType, String value) {
    if (value == null || value.trim().isEmpty()) return null;
    return Enum.valueOf(enumType, value.trim().toUpperCase());
  }

  private LocalDate parseDate(String value) {
    if (value == null || value.trim().isEmpty()) return null;
    try {
      return LocalDate.parse(value.trim());
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid date format", e);
    }
  }

  private LocalDateTime parseDateTime(String value) {
    if (value == null || value.trim().isEmpty() || value.trim().equals("''")) return null;
    try {
      return LocalDateTime.parse(value.trim());
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid DateTime format", e);
    }
  }

}
