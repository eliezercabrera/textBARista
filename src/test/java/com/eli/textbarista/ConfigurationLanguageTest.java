package com.eli.textbarista;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigurationLanguageTest {

  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @MethodSource("createInvalidArguments")
  void detectsInvalidIdentifierNames(String invalidIdentifier, String reasonIdentifierIsInvalid) {
    assertFalse(
        ConfigurationLanguage.validateIdentifierName(invalidIdentifier), reasonIdentifierIsInvalid);
  }

  @ParameterizedTest(name = "run #{index} with [{arguments}]")
  @MethodSource("createValidArguments")
  void doesNotRejectValidIdentifierNames(String validIdentifier, String notableCharacteristic) {
    assertTrue(
        ConfigurationLanguage.validateIdentifierName(validIdentifier),
        String.format(
            "Identifier with \"%s\" notable characteristic should be valid.",
            notableCharacteristic));
  }

  private static Stream<Arguments> createInvalidArguments() {
    String invalidlyLongIdentifier =
        String.join(
            "", Collections.nCopies(ConfigurationLanguage.MAXIMUM_IDENTIFIER_NAME_LENGTH + 1, "v"));
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
                ConfigurationLanguage.MAXIMUM_IDENTIFIER_NAME_LENGTH)));
  }

  private static Stream<Arguments> createValidArguments() {
    return Stream.of(
        Arguments.of("v", "short_ascii"),
        Arguments.of("my-val1d_IDentifier", "long_ascii"),
        Arguments.of("345=-=*%@*()*%@*#()<%#@<,./?>#%", "symbols"),
        Arguments.of("これはテストです", "unicode"));
  }
}
