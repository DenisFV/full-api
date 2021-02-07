package ru.denisfv.fullapi.architecture.mvc.util;

import com.github.tennaito.rsql.misc.ArgumentFormatException;
import com.github.tennaito.rsql.misc.ArgumentParser;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class RsqlSupportArgumentParser implements ArgumentParser {
    public RsqlSupportArgumentParser() {
        super();
    }

    public <T> T parse(final String argument, final Class<T> type) {

        log.info("Parsing argument ''{}'' as type {}, thread {}", argument, type.getSimpleName(), Thread.currentThread().getName());

        // Nullable object
        if (argument == null || "null".equals(argument.trim().toLowerCase())) {
            return null;
        }

        // common types
        try {
            if (type.equals(String.class)) {
                return (T) argument;
            }
            if (type.equals(Integer.class) || type.equals(int.class)) {
                return (T) Integer.valueOf(argument);
            }
            if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                return (T) Boolean.valueOf(argument);
            }
            if (type.isEnum()) {
                return (T) Enum.valueOf((Class<Enum>) type, argument);
            }
            if (type.equals(Float.class) || type.equals(float.class)) {
                return (T) Float.valueOf(argument);
            }
            if (type.equals(Double.class) || type.equals(double.class)) {
                return (T) Double.valueOf(argument);
            }
            if (type.equals(Long.class) || type.equals(long.class)) {
                return (T) Long.valueOf(argument);
            }
            if (type.equals(BigDecimal.class)) {
                return (T) new BigDecimal(argument);
            }
        } catch (IllegalArgumentException ex) {
            throw new ArgumentFormatException(argument, type);
        }

        // date
        if (type.equals(Date.class)) {
            return (T) parseDate(argument, type);
        }

        if (type.equals(LocalDate.class)) {
            return (T) parseLocalDate(argument, type);
        }

        if (type.equals(LocalDateTime.class)) {
            return (T) parseLocalDateTime(argument, type);
        }

        // try to parse via valueOf(String s) method
        try {
            log.info("Trying to get and invoke valueOf(String s) method on {}", type);
            Method method = type.getMethod("valueOf", String.class);
            return (T) method.invoke(type, argument);
        } catch (InvocationTargetException ex) {
            throw new ArgumentFormatException(argument, type);
        } catch (ReflectiveOperationException ex) {
            log.error("{} does not have method valueOf(String s) or method is inaccessible", type);
            throw new IllegalArgumentException("Cannot parse argument type " + type);
        }
    }

    private <T> Date parseDate(final String argument, final Class<T> type) {
        try {
            return new SimpleDateFormat(Constants.DATE_TIME_FORMAT_RU).parse(argument);
        } catch (ParseException ex) {
            log.info("Not a date time format, lets try with date format. (parseDate)");
        }
        try {
            return new SimpleDateFormat(Constants.DATE_FORMAT_RU).parse(argument);
        } catch (ParseException ex1) {
            throw new ArgumentFormatException(argument, type);
        }
    }

    /**
     * Изм. для LocalDate
     *
     * @param argument
     * @param type
     * @param <T>
     * @return
     */
    private <T> LocalDate parseLocalDate(final String argument, final Class<T> type) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT_RU);
            return LocalDate.parse(argument, formatter);
        } catch (Exception ex) {
            log.info("Not a date time format, lets try with date format.");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_RU);
            return LocalDate.parse(argument, formatter);
        } catch (Exception ex) {
            throw new ArgumentFormatException(argument, type);
        }
    }

    /**
     * Изм. для LocalDateTime
     *
     * @param argument
     * @param type
     * @param <T>
     * @return
     */
    private <T> LocalDateTime parseLocalDateTime(final String argument, final Class<T> type) {
        try {
            DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT_RU);
            return LocalDateTime.parse(argument, FORMATTER);
        } catch (Exception ex) {
            log.info("Not a date time format, lets try with date format. (parseLocalDateTime)");
        }
        try {
            DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_RU);
            return LocalDateTime.parse(argument, FORMATTER);
        } catch (Exception ex) {
            throw new ArgumentFormatException(argument, type);
        }
    }

    /* (non-Javadoc)
     * @see br.tennaito.rsql.misc.ArgumentParser#parse(java.util.List, java.lang.Class)
     */
    public <T> List<T> parse(final List<String> arguments, final Class<T> type) {
        List<T> castedArguments = new ArrayList<>(arguments.size());
        for (String argument : arguments) {
            castedArguments.add(this.parse(argument, type));
        }
        return castedArguments;
    }
}
