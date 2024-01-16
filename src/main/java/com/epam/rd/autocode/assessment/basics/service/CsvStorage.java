package com.epam.rd.autocode.assessment.basics.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Function;

public interface CsvStorage {
    <T> List<T> read(InputStream source, Function<String[], T> mapper) throws IOException;

    <T> void write(OutputStream dest, List<T> values, Function<T, String[]> mapper) throws IOException;
}
