package me.dmmax.example.grpc.app;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthGrpc;
import me.dmmax.example.grpc.service.common.model.EntityIdDto;
import me.dmmax.example.grpc.service.student.StudentClient;

public class StudentClientApp {

  public static void main(String[] args) {
    var port = 8080;
    var channel = ManagedChannelBuilder.forAddress("localhost", port)
        .usePlaintext()
        .build();
    var studentClient = new StudentClient(channel);
    var healthStub = HealthGrpc.newBlockingStub(channel);
    var scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      var command = scanner.next();
      System.out.println("Executing command: " + command);
      switch (command) {
        case "--help" -> printOutHelpText();
        case "health" -> System.out.println("Health: " + healthStub.check(HealthCheckRequest.newBuilder().build()));
        case "get" -> printReceivedStudent(studentClient, scanner);
        case "exit" -> System.exit(1);
        default -> System.err.println("Don't know the command: " + command);
      }
    }
    onShutdownHook(channel);
  }

  private static void printOutHelpText() {
    System.out.println("Usage: ");
    System.out.println(" '--help'       – help text");
    System.out.println(" 'health'       - for getting health check response");
    System.out.println(" 'get <uuid>'   – returns student by uuid or throws an exception if something went wrong");
    System.out.println(" 'exit'         – to close the client");
  }

  private static void printReceivedStudent(StudentClient studentClient, Scanner scanner) {
    System.out.println("Need to provide uuid...");
    var uuid = scanner.next();
    var request = new EntityIdDto(uuid);
    try {
      var student = studentClient.get(request);
      System.out.println("Student Response: " + student);
    } catch (Exception e) {
      System.err.printf("Got an exception ('%s') during extraction of the student with id: %s", e.getMessage(), uuid);
      System.out.println();
    }
  }

  private static void onShutdownHook(ManagedChannel channel) {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      // Use stderr here since the logger may have been reset by its JVM shutdown hook.
      System.err.println("*** shutting down gRPC server since JVM is shutting down");
      try {
        channel.awaitTermination(5, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.err.println("*** server shut down");
    }));
  }
}
