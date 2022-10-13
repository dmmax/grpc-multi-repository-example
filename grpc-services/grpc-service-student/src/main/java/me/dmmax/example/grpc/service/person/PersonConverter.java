package me.dmmax.example.grpc.service.person;

import java.time.Instant;
import com.google.common.base.Converter;
import com.google.protobuf.Duration;
import me.dmmax.example.grpc.student.v1.Person;

public class PersonConverter extends Converter<PersonDto, Person> {

    @Override
    protected Person doForward(PersonDto dto) {
      var birthDay = dto.birthday();
      return Person.newBuilder()
          .setLastName(dto.lastName())
          .setFirstName(dto.firstName())
          .setBirthday(Duration.newBuilder()
              .setNanos(birthDay.getNano())
              .setSeconds(birthDay.getEpochSecond())
              .build())
          .build();
    }

    @Override
    public PersonDto doBackward(Person person) {
      var birthday = person.getBirthday();
      return new PersonDto(person.getFirstName(), person.getLastName(), Instant.ofEpochSecond(birthday.getSeconds(), birthday.getNanos()));
    }
  }