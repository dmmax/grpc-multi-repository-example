package me.dmmax.example.grpc.app;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.HealthStatusManager;
import me.dmmax.example.grpc.service.student.StudentServiceStaticImpl;

class StudentServer {

  private final HealthStatusManager healthStatusManager = new HealthStatusManager();
  private Server server;

  void start(int port) throws IOException {
    server = ServerBuilder.forPort(port)
        .addService(new StudentServiceStaticImpl())
        .addService(healthStatusManager.getHealthService())
        .build()
        .start();
    onJvmShutdown();
  }

  void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  private void onJvmShutdown() {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      // Use stderr here since the logger may have been reset by its JVM shutdown hook.
      System.err.println("*** shutting down gRPC server since JVM is shutting down");
      try {
        healthStatusManager.enterTerminalState();
        stop();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.err.println("*** server shut down");
    }));
  }

  private void stop() throws InterruptedException {
    if (server != null) {
      server.awaitTermination(5, TimeUnit.SECONDS);
    }
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    var studentServer = new StudentServer();
    var port = 8080;
    System.out.println("Starting server on the port: " + port);
    studentServer.start(port);
    studentServer.blockUntilShutdown();
    System.out.println("Server is started");
  }
}
