package com.eli.textbarista;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdentifierTest {

  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @MethodSource("createInvalidIdentifiers")
  void detectsInvalidIdentifierNames(String invalidIdentifier, String reasonIdentifierIsInvalid) {
    assertFalse(Identifier.validateIdentifierName(invalidIdentifier), reasonIdentifierIsInvalid);
  }

  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @MethodSource("createValidIdentifiers")
  void doesNotRejectValidIdentifierNames(String validIdentifier, String notableCharacteristic) {
    assertTrue(
        Identifier.validateIdentifierName(validIdentifier),
        String.format(
            "Identifier with \"%s\" notable characteristic should be valid.",
            notableCharacteristic));
  }

  private static Stream<Arguments> createInvalidIdentifiers() {
    String invalidlyLongIdentifier =
        String.join("", Collections.nCopies(Identifier.MAXIMUM_IDENTIFIER_NAME_LENGTH + 1, "v"));
    return Stream.of(
        Arguments.of("", "Identifier name cannot be empty."),
        Arguments.of("my_invalid:identifier", "The character ':' is forbidden."),
        Arguments.of("my_invalid$identifier", "The character '$' is forbidden."),
        Arguments.of("my_invalid{identifier", "The character '{' is forbidden."),
        Arguments.of("my_invalid}identifier", "The character '}' is forbidden."),
        Arguments.of("my_invalid${identifier}", "The characters \"${}\" are forbidden."),
        Arguments.of(" ", "Identifier name cannot contain whitespace."),
        Arguments.of("my_invalid identifier", "Identifier name cannot contain whitespace."),
        Arguments.of("my_invalid\nidentifier", "Identifier name cannot contain whitespace."),
        Arguments.of(
            invalidlyLongIdentifier,
            String.format(
                "Identifier name is too long. Maximum length is %d characters.",
                Identifier.MAXIMUM_IDENTIFIER_NAME_LENGTH)));
  }

  private static Stream<Arguments> createValidIdentifiers() {
    return Stream.of(
        Arguments.of("v", "short_ascii"),
        Arguments.of("my-val1d_IDentifier", "long_ascii"),
        Arguments.of("345=-=*%@*()*%@*#()<%#@<,./?>#%", "symbols"),
        Arguments.of("これはテストです", "unicode"));
  }
}
