package com.epam.rd.autocode.assessment.basics.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Employee extends User implements Serializable {
  // place your code here
  private String phone;
  private LocalDate birthDate;

  public Employee() {
  }

  public Employee(long id, String email, String password, String name, String phone, LocalDate birthDate) {
    super(id, email, password, name);
    this.phone = phone;
    this.birthDate = birthDate;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Employee employee = (Employee) o;
    return Objects.equals(phone, employee.phone) && Objects.equals(birthDate, employee.birthDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), phone, birthDate);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + "{" +
            "id=" + getId() +
            ", email='" + getEmail() + '\'' +
            ", name='" + getName() + '\'' +
            ", phone='" + getPhone() + '\'' +
            ", birthDate=" + birthDate +
            "}";
  }
}
