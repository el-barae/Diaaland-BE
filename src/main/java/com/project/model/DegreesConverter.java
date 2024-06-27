package com.project.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class DegreesConverter implements AttributeConverter<List<String>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> degrees) {
        if (degrees == null || degrees.isEmpty()) {
            return null;
        }
        return String.join(SEPARATOR, degrees);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbDegrees) {
        if (dbDegrees == null || dbDegrees.isEmpty()) {
            return null;
        }
        return Arrays.stream(dbDegrees.split(SEPARATOR))
                .collect(Collectors.toList());
    }
}
