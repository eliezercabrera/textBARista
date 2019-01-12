package com.eli.textbarista;

import com.google.common.collect.ImmutableSet;

/**
 * Deals with low levels aspect of the configuration language, such as identifying keywords and
 * special character sequences, and parsing or validating tokens. Other classes deal with whether or
 * not a configuration file contains a valid order of tokens.
 */
class ConfigurationLanguage {
  static final int MAXIMUM_IDENTIFIER_NAME_LENGTH = 60;

  private static final ImmutableSet<Character> INVALID_IDENTIFIER_NAME_CHARACTERS =
      ImmutableSet.of(
          SpecialCharacterSequence.SEPARATOR.getChar(),
          SpecialCharacterSequence.IDENTIFIER_INTRODUCTOR.getChar(),
          SpecialCharacterSequence.IDENTIFIER_OPENING.getChar(),
          SpecialCharacterSequence.IDENTIFIER_CLOSING.getChar());

  enum SpecialCharacterSequence {
    COMMENT("--"),
    SEPARATOR(":"),
    ESCAPE("\\"),
    IDENTIFIER_INTRODUCTOR("$"),
    IDENTIFIER_OPENING("{"),
    IDENTIFIER_CLOSING("}");

    private final String sequence;

    SpecialCharacterSequence(String sequence) {
      this.sequence = sequence;
    }

    String get() {
      return sequence;
    }

    char getChar() {
      return sequence.charAt(0);
    }
  }

  private static boolean isInvalidIdentifierNameCharacter(char c) {
    return INVALID_IDENTIFIER_NAME_CHARACTERS.contains(c) || Character.isWhitespace(c);
  }

  static boolean validateIdentifierName(String identifierName) {

    if (identifierName.isEmpty()) {
      return false;
    }

    if (identifierName.length() > MAXIMUM_IDENTIFIER_NAME_LENGTH) {
      return false;
    }

    return identifierName
        .chars()
        .mapToObj(c -> (char) c)
        .noneMatch(ConfigurationLanguage::isInvalidIdentifierNameCharacter);
  }
}
