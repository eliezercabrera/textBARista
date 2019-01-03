package com.eli.textbarista;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.logging.Logger;

public class TextBaristaServer {

  private static final Logger logger = Logger.getLogger(TextBaristaServer.class.getName());

  private int port = 42420;
  private Server server;
  private int counter = 0;

  private void start() throws Exception {
    logger.info("Starting the grpc server");

    server = ServerBuilder.forPort(port).addService(new GreeterImpl()).build().start();

    logger.info("Server started. Listening on port " + port);

    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  System.err.println(
                      "*** JVM is shutting down. Turning off grpc server as well ***");
                  TextBaristaServer.this.stop();
                  System.err.println("*** shutdown complete ***");
                }));
  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  public static void main(String[] args) throws Exception {
    logger.info("Server startup. Args = " + Arrays.toString(args));
    final TextBaristaServer helloWorldServer = new TextBaristaServer();

    helloWorldServer.start();
    helloWorldServer.blockUntilShutdown();
  }

  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  private int count() {
    int counterCopy = counter;
    counter++;
    return counterCopy;
  }

  private class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
      HelloReply response =
          HelloReply.newBuilder()
              .setMessage(String.format("%d: Hello %s", count(), request.getName()))
              .build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
  }
}
