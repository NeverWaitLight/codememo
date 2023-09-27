package org.waitlight.codememo.utils.file.excel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * 实例化是为了使用 {@link ConversionService}
 */
@Slf4j
@AllArgsConstructor
public class ExcelTool {

    private static final ConcurrentHashMap<Class<?>, List<CellHandler>> SETTER_CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, List<CellHandler>> GETTER_CACHE = new ConcurrentHashMap<>();

    private final ConversionService conversionService;
    private final ResourceLoader resourceLoader;

    public <T> Workbook write(String templateFileName, int startRowNum, Class<?> klass, List<T> data) throws IOException {
        Resource resource = resourceLoader.getResource(templateFileName);
        Workbook workbook = WorkbookFactory.create(resource.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        sheet.removeRow(sheet.getRow(startRowNum));

        write(workbook, startRowNum, klass, data);

        return workbook;
    }

    public <T> void write(Workbook workbook, int startRowNum, Class<?> klass, List<T> data) {
        if (CollectionUtils.isEmpty(data)) return;
        List<CellHandler> cellHandlers = takeGetters(klass);

        Sheet sheet = workbook.getSheetAt(0);
        if (CollectionUtils.isEmpty(data)) return;
        writeContent(sheet, startRowNum, data, cellHandlers);
    }

    private <T> void writeContent(Sheet sheet, int startRowNum, List<T> objects, List<CellHandler> cellHandlers) {
        for (int i = 0; i < objects.size(); i++) {
            T obj = objects.get(i);
            Row row = sheet.createRow(i + startRowNum);
            for (int cellnum = 0; cellnum < cellHandlers.size(); cellnum++) {
                Cell cell = row.createCell(cellnum);
                Method method = cellHandlers.get(cellnum).getMethod();
                Object value = null;
                try {
                    value = method.invoke(obj);
                } catch (Exception e) {
                    log.error("Failed to invoke method", e);
                }

                if (Objects.isNull(value)) continue;
                Class<?> valueKlass = value.getClass();
                if (String.class.isAssignableFrom(valueKlass)) {
                    cell.setCellValue(String.valueOf(value));
                } else if (Boolean.class.isAssignableFrom(valueKlass)) {
                    cell.setCellValue(Boolean.TRUE.equals(value) ? "是" : "否");
                } else if (Date.class.isAssignableFrom(valueKlass)) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    String format = dateTimeFormatter.format(((Date) value).toInstant());
                    cell.setCellValue(format);
                } else {
                    cell.setCellValue(conversionService.convert(value, String.class));
                }
            }
        }
    }

    public <T> List<T> read(Workbook workbook, int startRowNum, Class<T> klass) {
        Objects.requireNonNull(workbook);
        Objects.requireNonNull(klass);

        List<CellHandler> cellHandlers = takeSetters(klass);

        Sheet sheet = workbook.getSheetAt(0);
        if (Objects.isNull(sheet)) throw new RuntimeException("没有可用sheet");
        return readSheet(startRowNum, sheet, klass, cellHandlers);
    }

    private <T> List<T> readSheet(int startRowNum, Sheet sheet, Class<T> klass, List<CellHandler> setters) {
        if (Objects.isNull(sheet)) return new ArrayList<>();

        List<T> data = new ArrayList<>();
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            int rownum = i + startRowNum;
            Row row = sheet.getRow(rownum);
            if (Objects.isNull(row)) continue;

            T datum;
            try {
                Constructor<T> constructor = klass.getConstructor();
                datum = constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Not found no-arg-constructor in " + klass.getName(), e);
            }

            if (isNotRowEmpty(row) && readRow(datum, row, setters)) {
                data.add(datum);
            }
        }
        return data;
    }

    private static boolean isNotRowEmpty(Row row) {
        if (Objects.isNull(row)) return false;
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK)
                return true;
        }
        return false;
    }

    private <T> boolean readRow(T datum, Row row, List<CellHandler> cellHandlers) {
        boolean hasValue = false;
        for (int cellnum = 0; cellnum < row.getLastCellNum(); cellnum++) {
            Cell cell = row.getCell(cellnum);
            if (Objects.isNull(cell)) continue;
            CellHandler cellHandler = cellHandlers.get(cellnum);
            if (Objects.isNull(cellHandler)) continue;
            Method method = cellHandler.getMethod();
            if (Objects.isNull(method) || !cellHandler.isCanImport()) continue;
            try {
                if (!readValue(cell, method, datum)) {
                    log.info("Read cell: {}:{} raw value:{}, failed", row.getRowNum(), cellnum, cell.getStringCellValue());
                } else {
                    hasValue = true;
                }
            } catch (Exception e) {
                CellHandler reasonHandler = cellHandlers.get(cellHandlers.size() - 1);
                try {
                    Method setReasonMethod = reasonHandler.getMethod();
                    String message = e.getMessage();
                    if (message.contains(":")) {
                        message = message.substring(message.lastIndexOf(":") + 1);
                    }
                    setReasonMethod.invoke(datum, cellHandler.getTitle() + ": " + message);
                    return true;
                } catch (IllegalAccessException | InvocationTargetException e1) {
                    log.error("Failed to write data to setter, ", e1);
                }
            }
        }
        return hasValue;
    }

    private <T> boolean readValue(Cell cell, Method method, T datum) {
        if (!ObjectUtils.allNotNull(cell, method, datum)) return false;
        String cellValue = getCellValueAsString(cell);
        if (StringUtils.isBlank(cellValue)) return false;

        Class<?> valueType = method.getParameterTypes()[0];
        Object value = cellValue;
        if (conversionService.canConvert(String.class, valueType)) {
            value = conversionService.convert(cellValue, valueType);
        }

        try {
            method.invoke(datum, value);
            return true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Failed to write data to setter, ", e);
        }

        return false;
    }

    private static String getCellValueAsString(Cell cell) {
        CellType cellType = cell.getCellType();
        String val = "";

        switch (cellType) {
            case STRING:
                val = cell.getStringCellValue();
                break;

            case NUMERIC:
                DataFormatter dataFormatter = new DataFormatter();
                val = dataFormatter.formatCellValue(cell);
                break;

            case BOOLEAN:
                val = String.valueOf(cell.getBooleanCellValue());
                break;

            case BLANK:
                break;
        }
        return val;
    }

    private <T> List<CellHandler> takeSetters(Class<T> klass) {
        return SETTER_CACHE.computeIfAbsent(klass, c -> Arrays.stream(c.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumn.class))
                .map(field -> {
                    ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                    return new CellHandler(excelColumn.index(), excelColumn.title(), takeSetter(c, field),
                            excelColumn.canImport(), excelColumn.canExport());
                })
                .sorted(Comparator.comparingInt(CellHandler::getIndex))
                .collect(Collectors.toList()));
    }

    private <T> List<CellHandler> takeGetters(Class<T> klass) {
        return GETTER_CACHE.computeIfAbsent(klass, c -> Arrays.stream(c.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumn.class))
                .map(field -> {
                    ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                    return new CellHandler(excelColumn.index(), excelColumn.title(), takeGetter(c, field),
                            excelColumn.canImport(), excelColumn.canExport());
                })
                .sorted(Comparator.comparingInt(CellHandler::getIndex))
                .collect(Collectors.toList()));
    }

    private <T> Method takeGetter(Class<T> klass, Field field) {
        return takeMethod("get", klass, field);
    }

    private <T> Method takeSetter(Class<T> klass, Field field) {
        return takeMethod("set", klass, field);
    }

    private <T> Method takeMethod(String prefix, Class<T> klass, Field field) {
        String name = field.getName();
        String setter = prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
        return Arrays.stream(klass.getMethods())
                .filter(m -> setter.equals(m.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Not found %s method", setter)));
    }

}