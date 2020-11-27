package com.csv;

import java.util.List;
import java.util.Map;

public class CSVBuilder {

  private final List<Map<String, ?>> data;
  private List<String> labels;

  public CSVBuilder(final List<Map<String, ?>> data) {
    this.data = data;
  }

  public CSVBuilder(final List<Map<String, ?>> data, final List<String> labels) {
    this.data = data;
    this.labels = labels;
  }

  public void setLabels(final List<String> labels) {
    this.labels = labels;
  }

  public String build() {
    return new CSVBuildHandler(data, labels).buildCSV();
  }
}
