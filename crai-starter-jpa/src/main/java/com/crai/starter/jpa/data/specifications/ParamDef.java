package com.crai.starter.jpa.data.specifications;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class ParamDef {

  String dbField;
  String name;

  ParamOperators operator;

  static public ParamDef buildLike(String name) {
    return new ParamDef(name, name, ParamOperators.LIKE);
  }

  static public ParamDef buildLike(String name, String dbField) {
    return new ParamDef(name, dbField, ParamOperators.LIKE);
  }

  static public ParamDef build(String name, String dbField, ParamOperators operator) {
    return new ParamDef(name, dbField, operator);
  }
}
