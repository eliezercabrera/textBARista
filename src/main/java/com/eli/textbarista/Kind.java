package com.eli.textbarista;

import com.google.common.collect.ImmutableSet;

import java.util.EnumSet;

enum Kind {
  OPTIONAL,
  MANDATORY,
  DEFAULT;

  private static final ImmutableSet<String> VALID_KIND_NAMES =
      EnumSet.allOf(Kind.class).stream().map(Kind::name).collect(ImmutableSet.toImmutableSet());

  private static final String DEFAULT_KIND_SEPARATOR = " ";

  static boolean validateKindName(String typeName) {
    return VALID_KIND_NAMES.contains(typeName);
  }

  // TODO: test this method.
  static boolean validateKind(String kindText, Type type) {
    String[] kindSections = kindText.split(DEFAULT_KIND_SEPARATOR, 2);

    if (kindSections.length < 1) {
      return false;
    }

    String kindName = kindSections[0];
    if (!validateKindName(kindName)) {
      return false;
    }

    // TODO: implement DEFAULT kind logic, as it requires value parsing.
    if (Kind.valueOf(kindName) == Kind.DEFAULT) {
      return false;
    }
    return true;
  }
}
