package com.epam.rd.autocode.assessment.basics.service;

import com.epam.rd.autocode.assessment.basics.entity.Book;
import com.epam.rd.autocode.assessment.basics.entity.Client;
import com.epam.rd.autocode.assessment.basics.entity.Employee;
import com.epam.rd.autocode.assessment.basics.entity.Order;

public interface Mapper {
    Client csvToClient(String[] values);

    Employee csvToEmployee(String[] values);

    Book csvToBook(String[] values);

    Order csvToOrder(String[] values);

    String[] orderToCsv(Order order);

    String[] bookToCsv(Book book);

    String[] clientToCsv(Client client);

    String[] employeeToCsv(Employee employee);
}
