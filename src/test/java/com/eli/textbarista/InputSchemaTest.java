package com.eli.textbarista;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputSchemaTest {
  @Test
  void illegalIdentifierNameTest() throws IOException {
    assertEquals(Paths.get("").toAbsolutePath().toString(), "/home/eli/Downloads/text-barista");
    assertTrue(
        Files.readAllLines(
                Paths.get("src/test/resources/input-schemas/invalid/empty_file.txt"))
            .isEmpty());
  }
}