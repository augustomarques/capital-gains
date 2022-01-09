package br.com.capitalgains.converter;

import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.dto.TaxDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
    }

    public static String convertToJson(final List<TaxDTO> taxes) {
        try {
            return objectMapper.writeValueAsString(taxes);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException(
                  MessageFormat.format("Could not convert taxes to JSON [Error: {0}]", exception.getMessage()),
                  exception);
        }
    }

    public static List<FinancialTransactionDTO> convertToObjects(final String json) {
        try {
            return List.of(objectMapper.readValue(json, FinancialTransactionDTO[].class));
        } catch (IOException exception) {
            throw new IllegalArgumentException(
                  MessageFormat.format("Could not convert JSON [Error: {0}]", exception.getMessage()), exception);
        }
    }
}
