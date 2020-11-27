package com.csv.helper;

import static java.util.Objects.isNull;

public class CSVBuildHelper {

  private CSVBuildHelper() {}

  public static String getValue(Object value) {
    return isNull(value) ? "" : value.toString();
  }
}
