package com.csv;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.csv.helper.CSVBuildHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class CSVBuildHandlerTest {

  @TestFactory
  @DisplayName("Tests CSV build")
  List<DynamicTest> buildCSV() {
    return Arrays.asList(
        dynamicTest(
            "checks csv construction when labels are passed",
            () -> {
              final List<String> labels =
                  Arrays.asList("interactionId", "connectionId", "componentId");

              final Map<String, Object> map1 = new HashMap<>();
              map1.put("interactionId", "abc");
              map1.put("componentId", "def");
              map1.put("connectionId", "ijk");

              final Map<String, Object> map2 = new HashMap<>();
              map2.put("componentId", "lmn");
              map2.put("connectionId", "opq");
              map2.put("interactionId", "rst");

              final Map<String, Object> map3 = new HashMap<>();
              map3.put("componentId", "uvw");
              map3.put("interactionId", "xyz");
              map3.put("connectionId", "abc");

              final CSVBuildHandler handler =
                  new CSVBuildHandler(Arrays.asList(map1, map2, map3), labels);

              final String expected =
                  "interactionId,connectionId,componentId\nabc,ijk,def\nrst,opq,lmn\nxyz,abc,uvw";
              final String actual = handler.buildCSV();

              assertEquals(expected, actual);
            }),
        dynamicTest(
            "checks csv construction when labels are not passed",
            () -> {
              final Map<String, Object> map1 = new LinkedHashMap<>();
              map1.put("interactionId", "abc");
              map1.put("componentId", "def");
              map1.put("connectionId", "ijk");

              final Map<String, Object> map2 = new LinkedHashMap<>();
              map2.put("componentId", "lmn");
              map2.put("connectionId", "opq");
              map2.put("interactionId", "rst");

              final Map<String, Object> map3 = new LinkedHashMap<>();
              map3.put("componentId", "uvw");
              map3.put("interactionId", "xyz");
              map3.put("connectionId", "abc");

              final CSVBuildHandler handler =
                  new CSVBuildHandler(Arrays.asList(map1, map2, map3), null);

              final String expected =
                  "interactionId,componentId,connectionId\nabc,def,ijk\nrst,lmn,opq\nxyz,uvw,abc";
              final String actual = handler.buildCSV();

              assertEquals(expected, actual);
            }));
  }

  @TestFactory
  @DisplayName("Tests label build")
  List<DynamicTest> buildLabels() {
    return Arrays.asList(
        dynamicTest(
            "checks label construction when labels are passed",
            () -> {
              final CSVBuildHandler handler =
                  new CSVBuildHandler(
                      null, Arrays.asList("connectionId", "interactionId", "componentId"));

              handler.buildLabels();

              assertEquals(
                  "connectionId,interactionId,componentId", handler.getCsvBuilder().get(0));
            }),
        dynamicTest(
            "checks label construction when labels are not present",
            () -> {
              final Map<String, Object> map1 = new LinkedHashMap<>();
              map1.put("connectionId", "abc");
              map1.put("interactionId", "def");
              map1.put("componentId", "ijk");

              final Map<String, Object> map2 = new LinkedHashMap<>();
              map2.put("connectionId", "abc");
              map2.put("componentId", "ijk");
              map2.put("interactionId", "def");

              final CSVBuildHandler handler = new CSVBuildHandler(Arrays.asList(map1, map2), null);

              handler.buildLabels();

              assertEquals(
                  "connectionId,interactionId,componentId", handler.getCsvBuilder().get(0));
            }));
  }

  @TestFactory
  @DisplayName("Tests values build")
  List<DynamicTest> buildValues() {
    return Arrays.asList(
        dynamicTest(
            "checks values construction when labels are present",
            () -> {
              final List<String> labels =
                  Arrays.asList("connectionId", "interactionId", "componentId");

              final Map<String, Object> value1 = new LinkedHashMap<>();
              value1.put("interactionId", "abc");
              value1.put("connectionId", "def");
              value1.put("componentId", "ijk");

              final Map<String, Object> value2 = new LinkedHashMap<>();
              value2.put("componentId", "lmn");
              value2.put("interactionId", "opq");
              value2.put("connectionId", "rst");

              final List<String> expected = new ArrayList<>();
              expected.add("def,abc,ijk");
              expected.add("rst,opq,lmn");

              final CSVBuildHandler handler =
                  new CSVBuildHandler(Arrays.asList(value1, value2), labels);

              handler.buildValues();

              assertEquals(expected, handler.getCsvBuilder());
            }),
        dynamicTest(
            "checks values construction when labels are not present",
            () -> {
              final Map<String, Object> value1 = new LinkedHashMap<>();
              value1.put("interactionId", "abc");
              value1.put("connectionId", "def");
              value1.put("componentId", "ijk");

              final Map<String, Object> value2 = new LinkedHashMap<>();
              value2.put("componentId", "lmn");
              value2.put("interactionId", "opq");
              value2.put("connectionId", "rst");

              final List<String> expected = new ArrayList<>();
              expected.add("abc,def,ijk");
              expected.add("opq,rst,lmn");

              final CSVBuildHandler handler =
                  new CSVBuildHandler(Arrays.asList(value1, value2), null);

              handler.buildValues();

              assertEquals(expected, handler.getCsvBuilder());
            }));
  }
}
