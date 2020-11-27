package com.csv;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.joining;

import com.csv.helper.CSVBuildHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSVBuildHandler {

  private final List<Map<String, ?>> data;
  private final List<String> labels;

  private final List<String> csvBuilder;

  public CSVBuildHandler(final List<Map<String, ?>> data, final List<String> labels) {
    this.data = data;
    this.labels = isNull(labels) ? createDefaultLabels() : labels;

    this.csvBuilder = new ArrayList<>();
  }

  public String buildCSV() {
    buildLabels();
    buildValues();

    return String.join("\n", csvBuilder);
  }

  void buildLabels() {
    csvBuilder.add(String.join(",", labels));
  }

  void buildValues() {
    data.forEach(map -> csvBuilder.add(buildValuesWhenLabelsArePresent(map)));
  }

  List<String> getCsvBuilder() {
    return csvBuilder;
  }

  List<String> createDefaultLabels() {
    return new ArrayList<>(data.get(0).keySet());
  }

  private String buildValuesWhenLabelsArePresent(final Map<String, ?> map) {
    return labels.stream()
        .map(label -> CSVBuildHelper.getValue(map.get(label)))
        .collect(joining(","));
  }
}
