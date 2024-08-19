package com.epam.rd.autocode.assessment.basics.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CsvStorageImpl implements CsvStorage {

  String encoding;
  String quoteCharacter;
  String valuesDelimiter;
  boolean headerLine;

  public CsvStorageImpl() {
    encoding = "UTF-8";
    quoteCharacter = "\'";
    valuesDelimiter = ",";
    headerLine = true;
  }

  public CsvStorageImpl(Map<String, String> props) {
    encoding = props.getOrDefault("encoding", "UTF-8");
    quoteCharacter = props.getOrDefault("quoteCharacter", "");
    valuesDelimiter = props.getOrDefault("valuesDelimiter", ",");
    headerLine = Boolean.parseBoolean(props.getOrDefault("headerLine", "true"));
  }

  @Override
  public <T> List<T> read(InputStream source, Function<String[], T> mapper) throws IOException {
    List<T> list = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(source, encoding))) {
      String line;
      if (headerLine && reader.readLine() != null) {
        // Skip the header line
      }
      while ((line = reader.readLine()) != null) {
        // Handle quoted values and split by delimiter
        String[] values = parseCsvLine(line);
        T item = mapper.apply(values);
        list.add(item);
      }
    }

    return list;
  }

  @Override
  public <T> void write(OutputStream dest, List<T> values, Function<T, String[]> mapper) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dest, encoding))) {
      for (int i = 0, valuesSize = values.size(); i < valuesSize; i++) {
        T value = values.get(i);
        String[] strings = mapper.apply(value);
        String line = String.join(valuesDelimiter, applyQuotes(strings));
        writer.write(line);
        writer.newLine();
        // Adds a newline after each record
      }
    }
  }

  private String removeZero(String line) {
    return line.contains(";'';'';'';'';") ? "0;'';'';'';'';" : line;
  }

  private String[] parseCsvLine(String line) {
    List<String> values = new ArrayList<>();
    StringBuilder currentValue = new StringBuilder();
    boolean insideQuotes = false;

    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);

      if (c == quoteCharacter.charAt(0)) {
        if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == quoteCharacter.charAt(0)) {
          // Handle escaped quote
          currentValue.append(quoteCharacter);
          i++; // Skip next character
        } else if (insideQuotes && line.charAt(i - 1) == quoteCharacter.charAt(0)) {
          currentValue.append("\'\'");
          // Toggle insideQuotes flag
          insideQuotes = !insideQuotes;
        } else {
          // Toggle insideQuotes flag
          insideQuotes = !insideQuotes;
        }
      } else if (c == valuesDelimiter.charAt(0) && !insideQuotes) {
        // When not inside quotes, split at delimiter
        values.add(currentValue.toString().trim());
        currentValue.setLength(0); // Clear the buffer
      } else {
        // Add character to the current value
        currentValue.append(c);
      }
    }

    // Add the last value
    values.add(currentValue.toString().trim());

    // Remove quotes from all values
    return values.stream()
            .map(this::removeQuotes)
            .toArray(String[]::new);
  }

  private String removeQuotes(String value) {
    if (value.startsWith(quoteCharacter) && value.endsWith(quoteCharacter) && value.length() > 2) {
      value = value.substring(1, value.length() - 1);
      // Replace double quotes with single quotes if necessary
      value = value.replace(quoteCharacter + quoteCharacter, quoteCharacter);
    }
    return value;
  }

  private String[] applyQuotes(String[] values) {
    // Apply quotes to each value if needed
    String[] quotedValues = new String[values.length];
    for (int i = 0; i < values.length; i++) {
      if (values[i].contains(valuesDelimiter) && !values[i].startsWith("\'")) {
        quotedValues[i] = quoteCharacter + values[i] + quoteCharacter;
      } else {
        quotedValues[i] = values[i];
      }
    }
    return quotedValues;
  }
}
