package com.crai.starter.jpa.util;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public final class StringFormatter {

  public static final String format(String messagePattern, Object... arg) {
    return MessageFormatter.arrayFormat(messagePattern, arg).getMessage();
  }

  public static final FormattingTuple formatTuple(String messagePattern, Object... arg) {
    return MessageFormatter.arrayFormat(messagePattern, new Object[] {arg});
  }
}
