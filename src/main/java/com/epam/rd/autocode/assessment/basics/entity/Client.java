package com.epam.rd.autocode.assessment.basics.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Client extends User implements Serializable {
  // place your code here
  private BigDecimal balance;

  public Client() {
    super();
  }

  public Client(long id, String email, String password, String name, BigDecimal balance) {
    super(id, email, password, name);
    this.balance = balance;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Client client = (Client) o;
    return Objects.equals(balance, client.balance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), balance);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + "{" +
            "id=" + getId() +
            ", email='" + getEmail() + '\'' +
            ", name='" + getName() + '\'' +
            ", balance=" + balance +
            "}";
  }
}
