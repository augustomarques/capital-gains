package br.com.capitalgains.converter;

import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.BUY;
import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.SELL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.dto.TaxDTO;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JsonConverterTest {

    @Test
    void should_convert_object_list_to_json() {
        final var taxes = List.of(
              new TaxDTO(BigDecimal.ZERO),
              new TaxDTO(BigDecimal.TEN),
              new TaxDTO(BigDecimal.valueOf(2)),
              new TaxDTO(BigDecimal.ONE));

        final var taxesJson = JsonConverter.convertToJson(taxes);

        assertThat(taxesJson).isEqualTo("[{\"tax\":0},{\"tax\":10},{\"tax\":2},{\"tax\":1}]");
    }

    @Test
    void shouls_convert_json_to_object_list() {
        final var json = getJsonWithThreeFinancialTransactions();

        final var financialTransactionsDTO = JsonConverter.convertToObjects(json);

        Assertions.assertThat(financialTransactionsDTO)
              .isNotEmpty()
              .containsExactly(
                    new FinancialTransactionDTO(BUY, BigDecimal.TEN, 100),
                    new FinancialTransactionDTO(SELL, BigDecimal.valueOf(15), 50),
                    new FinancialTransactionDTO(SELL, BigDecimal.valueOf(15), 50));
    }

    @Test
    void should_throw_exception_when_receiving_invalid_json() {
        final var json = "{ invalid json }";
        final Throwable throwable = catchThrowable(() -> JsonConverter.convertToObjects(json));

        assertThat(throwable)
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessageContaining("Could not convert JSON");
    }

    private String getJsonWithThreeFinancialTransactions() {
        return """
                 [
                     {
                         "operation":"buy",
                         "unit-cost":10,
                         "quantity": 100
                     },
                     {
                         "operation":"sell",
                         "unit-cost":15,
                         "quantity": 50
                     },
                     {
                         "operation":"sell",
                         "unit-cost":15,
                         "quantity": 50
                     }
                 ]
              """;
    }
}
