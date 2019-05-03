package com.eli.textbarista;

enum SpecialCharacterSequence {
  COMMENT("--"),
  SEPARATOR(":"),
  ESCAPE("\\"),
  IDENTIFIER_INTRODUCTOR("$"),
  IDENTIFIER_OPENING("{"),
  IDENTIFIER_CLOSING("}"),
  ASSIGNMENT_OPERATOR("=");

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
