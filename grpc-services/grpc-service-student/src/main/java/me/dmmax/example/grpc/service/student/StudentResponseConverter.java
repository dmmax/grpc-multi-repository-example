package me.dmmax.example.grpc.service.student;

import com.google.common.base.Converter;
import me.dmmax.example.grpc.service.person.PersonConverter;
import me.dmmax.example.grpc.student.v1.StudentResponse;

public class StudentResponseConverter extends Converter<StudentDto, StudentResponse> {

  private final PersonConverter personConverter;

  public StudentResponseConverter(PersonConverter personConverter) {
    this.personConverter = personConverter;
  }

  @Override
  protected StudentResponse doForward(StudentDto dto) {
    return StudentResponse.newBuilder()
        .setPerson(personConverter.convert(dto.person()))
        .setCardNumber(dto.cardNumber())
        .build();
  }

  @Override
  protected StudentDto doBackward(StudentResponse response) {
    return new StudentDto(personConverter.doBackward(response.getPerson()), response.getCardNumber());
  }
}
