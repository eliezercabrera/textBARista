package com.eli.textbarista;

import com.google.common.collect.ImmutableList;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;

/**
 * Deals with the input to the textBARista program. Can parse it into a map for the rest of the
 * program to use, validate input schemas, and validate input against its respective schema.
 */
class InputSchema {

  private static boolean isComment(String line) {
    return line.startsWith(SpecialCharacterSequence.COMMENT.get());
  }

  private static List<String> splitIntoSections(String line) {
    String[] preprocessedSections = line.split(SpecialCharacterSequence.SEPARATOR.get());

    ImmutableList.Builder<String> sections =
        ImmutableList.<String>builder().add(preprocessedSections[0]).add(preprocessedSections[1]);

    StringBuilder lastSection = new StringBuilder(preprocessedSections[2]);
    for (int i = 3; i < preprocessedSections.length; ++i) {
      lastSection.append(SpecialCharacterSequence.SEPARATOR.get() + preprocessedSections[i]);
    }

    return sections.add(lastSection.toString()).build();
  }

  private static boolean validateLineOfSchema(String line) {
    if (isComment(line)) {
      return true;
    }

    if (line.isEmpty()) {
      return true;
    }
    List<String> subsections = splitIntoSections(line);

    String identifierName = subsections.get(0);
    String typeName = subsections.get(1);

    boolean isKindMissingOrValid = true;

    if (subsections.size() == 3) {
      String kindText = subsections.get(2);
      Type type = Type.valueOf(typeName);
      isKindMissingOrValid = Kind.validateKind(kindText, type);
    }
    return Identifier.validateIdentifierName(identifierName)
        && Type.validateTypeName(typeName)
        && isKindMissingOrValid;
  }

  static boolean validateSchema(Reader programInput) {
    return new BufferedReader(programInput).lines().allMatch(InputSchema::validateLineOfSchema);
  }
}
