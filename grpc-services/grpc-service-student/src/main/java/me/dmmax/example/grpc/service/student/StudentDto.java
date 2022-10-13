package me.dmmax.example.grpc.service.student;

import java.util.Objects;
import me.dmmax.example.grpc.service.person.PersonDto;

public class StudentDto {

  private final PersonDto person;
  private final String cardNumber;

  public StudentDto(PersonDto person, String cardNumber) {
    this.person = person;
    this.cardNumber = cardNumber;
  }

  public PersonDto person() {
    return person;
  }

  public String cardNumber() {
    return cardNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StudentDto that = (StudentDto) o;
    return Objects.equals(person, that.person) && Objects.equals(cardNumber, that.cardNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(person, cardNumber);
  }

  @Override
  public String toString() {
    return "StudentDto{" +
        "person=" + person +
        ", cardNumber='" + cardNumber + '\'' +
        '}';
  }
}
