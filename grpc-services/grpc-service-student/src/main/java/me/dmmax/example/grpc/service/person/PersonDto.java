package me.dmmax.example.grpc.service.person;

import java.time.Instant;
import java.util.Objects;

public class PersonDto {

    private final String firstName;
    private final String lastName;
    private final Instant birthday;

    public PersonDto(String firstName, String lastName, Instant birthday) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthday = birthday;
    }

    public String firstName() {
      return firstName;
    }

    public String lastName() {
      return lastName;
    }

    public Instant birthday() {
      return birthday;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonDto personDto = (PersonDto) o;
    return Objects.equals(firstName, personDto.firstName) && Objects.equals(lastName, personDto.lastName)
        && Objects.equals(birthday, personDto.birthday);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, birthday);
  }

  @Override
  public String toString() {
    return "PersonDto{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", birthday=" + birthday +
        '}';
  }
}