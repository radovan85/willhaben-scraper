package com.radovan.spring.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class StringToTimestampConverter implements Converter<String, Timestamp> {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public Timestamp convert(MappingContext<String, Timestamp> context) {
        String source = context.getSource();
        if (source == null) {
            return null;
        }
        ZonedDateTime zonedDateTime = LocalDateTime.parse(source, dateFormatter).atZone(ZoneId.of("Europe/Vienna"));
        return Timestamp.valueOf(zonedDateTime.toLocalDateTime());
    }
}