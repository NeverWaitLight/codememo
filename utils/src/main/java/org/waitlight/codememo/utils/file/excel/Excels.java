package org.waitlight.codememo.utils.file.excel;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.List;

public interface Excels {

    <T> Workbook write(String templateFileName,
                       int startRowNum,
                       Class<?> klass,
                       List<T> data) throws IOException;

    <T> void write(Workbook workbook,
                   int startRowNum,
                   Class<?> klass,
                   List<T> data);

    <T> List<T> read(Workbook workbook,
                     int startRowNum,
                     Class<T> klass,
                     Pair<Integer, Integer> cellNumRange);
}