package com.wordparser.demo.configurator.persistance;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

// As jpa version is older than java8, it does not recognize java.time.LocalDateTime
// 1. Option is to prepare a converted
// 2. Option is to update dependency to Jpa 5.0 version
// Source: https://thoughts-on-java.org/persist-localdate-localdatetime-jpa/

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
        return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
    }
}