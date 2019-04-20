package com.eli.textbarista;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TypeTest {

  private static Stream<Arguments> createInvalidTypes() {
    return Stream.of(
        Arguments.of("", "Empty string provided as type name."),
        Arguments.of("boolean", "Type names are case sensitive."),
        Arguments.of("97:{}#$:}@{", "Random gibberish provided."));
  }

  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"BOOLEAN", "NATURAL", "DOUBLE", "STRING"})
  void acceptsValidTypeNames(String typeName) throws IllegalArgumentException {
    assertTrue(Type.validateTypeName(typeName));
  }

  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @MethodSource("createInvalidTypes")
  void rejectInvalidTypeNames(String invalidTypeName, String reasonTypeIsInvalid) {
    assertTrue(!Type.validateTypeName(invalidTypeName), reasonTypeIsInvalid);
  }
}
