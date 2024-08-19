package com.epam.rd.autocode.assessment.basics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order implements Serializable {
  // place your code here
  private long id;
  private long clientId;
  private long employeeId;
  private long bookId;
  private int numberOfBooks;
  private LocalDateTime orderDate;
  private BigDecimal price;

  public Order() {
  }

  public Order(long id, long clientId, long employeeId, long bookId, int numberOfBooks, LocalDateTime orderDate, BigDecimal price) {
    this.id = id;
    this.clientId = clientId;
    this.employeeId = employeeId;
    this.bookId = bookId;
    this.numberOfBooks = numberOfBooks;
    this.orderDate = orderDate;
    this.price = price;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getClientId() {
    return clientId;
  }

  public void setClientId(long clientId) {
    this.clientId = clientId;
  }

  public long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(long employeeId) {
    this.employeeId = employeeId;
  }

  public long getBookId() {
    return bookId;
  }

  public void setBookId(long bookId) {
    this.bookId = bookId;
  }

  public int getNumberOfBooks() {
    return numberOfBooks;
  }

  public void setNumberOfBooks(int numberOfBooks) {
    this.numberOfBooks = numberOfBooks;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return id == order.id && clientId == order.clientId && employeeId == order.employeeId && bookId == order.bookId && numberOfBooks == order.numberOfBooks && Objects.equals(orderDate, order.orderDate) && Objects.equals(price, order.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, clientId, employeeId, bookId, numberOfBooks, orderDate, price);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + "{" +
            "id=" + id +
            ", clientId=" + clientId +
            ", employeeId=" + employeeId +
            ", bookId=" + bookId +
            ", numberOfBooks=" + numberOfBooks +
            ", orderDate=" + orderDate +
            ", price=" + price +
            '}';
  }

}
