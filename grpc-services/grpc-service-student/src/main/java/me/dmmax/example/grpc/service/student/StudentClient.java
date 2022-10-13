package me.dmmax.example.grpc.service.student;

import io.grpc.ManagedChannel;
import me.dmmax.example.grpc.service.common.converter.EntityIdConverter;
import me.dmmax.example.grpc.service.common.model.EntityIdDto;
import me.dmmax.example.grpc.service.person.PersonConverter;
import me.dmmax.example.grpc.student.v1.StudentGrpc;
import me.dmmax.example.grpc.student.v1.StudentGrpc.StudentBlockingStub;
import me.dmmax.example.grpc.student.v1.StudentRequest;

public class StudentClient {

  private final StudentBlockingStub blockingStub;
  private final EntityIdConverter entityIdConverter;
  private final StudentResponseConverter studentResponseConverter;

  public StudentClient(ManagedChannel channel) {
    this.blockingStub = StudentGrpc.newBlockingStub(channel);
    this.entityIdConverter = new EntityIdConverter();
    this.studentResponseConverter = new StudentResponseConverter(new PersonConverter());
  }

  public StudentDto get(EntityIdDto entityIdDto) {
    var entityId = entityIdConverter.convert(entityIdDto);
    var request = StudentRequest.newBuilder()
        .setEntityId(entityId)
        .build();
    return studentResponseConverter.doBackward(blockingStub.get(request));
  }
}
