package com.eli.textbarista;

import com.google.common.collect.ImmutableSet;

import java.util.EnumSet;

enum Type {
  BOOLEAN,
  NATURAL,
  DOUBLE,
  STRING;

  private static final ImmutableSet<String> VALID_TYPE_NAMES =
      EnumSet.allOf(Type.class).stream().map(Type::name).collect(ImmutableSet.toImmutableSet());

  static boolean validateTypeName(String typeName) {
    return VALID_TYPE_NAMES.contains(typeName);
  }
}
