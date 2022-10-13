package me.dmmax.example.grpc.service.student;

import java.time.Instant;
import java.util.Map;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import me.dmmax.example.grpc.service.person.PersonConverter;
import me.dmmax.example.grpc.service.person.PersonDto;
import me.dmmax.example.grpc.student.v1.StudentGrpc.StudentImplBase;
import me.dmmax.example.grpc.student.v1.StudentRequest;
import me.dmmax.example.grpc.student.v1.StudentResponse;

public class StudentServiceStaticImpl extends StudentImplBase {

  private static final Map<String, StudentDto> STUDENT_BY_ENTITY_ID = Map.of(
      "uuid1", new StudentDto(new PersonDto("Emelia", "Crawford", Instant.ofEpochSecond(1L)), "card-number-1"),
      "uuid2", new StudentDto(new PersonDto("Atticus", "Golden", Instant.ofEpochSecond(2L)), "card-number-2"),
      "uuid3", new StudentDto(new PersonDto("Mckenzie", "Luna", Instant.ofEpochSecond(3L)), "card-number-3"),
      "uuid4", new StudentDto(new PersonDto("Lorelei", "Weber", Instant.ofEpochSecond(4L)), "card-number-4"),
      "uuid5", new StudentDto(new PersonDto("Moises", "Maxwell", Instant.ofEpochSecond(5L)), "card-number-5"));

  private final StudentResponseConverter responseConverter;

  public StudentServiceStaticImpl() {
    this(new StudentResponseConverter(new PersonConverter()));
  }
  public StudentServiceStaticImpl(StudentResponseConverter responseConverter) {
    this.responseConverter = responseConverter;
  }

  @Override
  public void get(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
    var entityId = request.getEntityId().getUuid();
    var studentById = STUDENT_BY_ENTITY_ID.get(entityId);
    if (studentById != null) {
      responseObserver.onNext(responseConverter.convert(studentById));
      responseObserver.onCompleted();
    } else {
      responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND));
    }
  }
}
