package com.eli.textbarista;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextBaristaClient {
  private static final Logger logger = Logger.getLogger(TextBaristaClient.class.getName());

  private final ManagedChannel channel;
  private GreeterGrpc.GreeterBlockingStub blockingStub;

  private TextBaristaClient(String hostname, int port) {
    channel = ManagedChannelBuilder.forAddress(hostname, port).usePlaintext().build();
    blockingStub = GreeterGrpc.newBlockingStub(channel);
  }

  private void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  private void greet(String name) {
    logger.info("Trying to greet " + name);
    try {
      HelloRequest request = HelloRequest.newBuilder().setName(name).build();
      HelloReply response = blockingStub.sayHello(request);
      logger.info("Response: " + response.getMessage());
    } catch (RuntimeException e) {
      logger.log(Level.WARNING, "Request to grpc server failed", e);
    }
  }

  public static void main(String[] args) throws Exception {
    TextBaristaClient client = new TextBaristaClient("localhost", 42420);
    String name = args.length > 0 ? args[0] : "unknown";

    try {
      client.greet(name);
    } finally {
      client.shutdown();
    }
  }
}
