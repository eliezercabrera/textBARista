package com.eli.textbarista;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class KindTest {

  private static Stream<Arguments> createInvalidKinds() {
    return Stream.of(
        Arguments.of("", "Empty string provided as kind name."),
        Arguments.of("optional", "Kind names are case sensitive."),
        Arguments.of("9098n7asfd}@{", "Random gibberish provided."));
  }

  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @ValueSource(strings = {"OPTIONAL", "MANDATORY", "DEFAULT"})
  void acceptsValidKindNames(String kindName) throws IllegalArgumentException {
    assertTrue(Kind.validateKindName(kindName));
  }

  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @MethodSource("createInvalidKinds")
  void rejectInvalidTypeNames(String invalidKindName, String reasonKindIsInvalid) {
    assertTrue(!Kind.validateKindName(invalidKindName), reasonKindIsInvalid);
  }
}
