package br.com.capitalgains.it;

import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.BUY;
import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.SELL;

import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.dto.TaxDTO;
import br.com.capitalgains.service.TaxPayableCalculatorService;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CapitalGainsIT {

    @Test
    void sholt_calculate_taxes() {
        final var financialTransactionsDTO = List.of(
              new FinancialTransactionDTO(BUY, BigDecimal.valueOf(100), 10000),
              new FinancialTransactionDTO(SELL, BigDecimal.valueOf(50), 5000),
              new FinancialTransactionDTO(SELL, BigDecimal.valueOf(200), 4000),
              new FinancialTransactionDTO(BUY, BigDecimal.valueOf(200), 5000),
              new FinancialTransactionDTO(SELL, BigDecimal.valueOf(100), 1000),
              new FinancialTransactionDTO(BUY, BigDecimal.valueOf(150), 10000),
              new FinancialTransactionDTO(SELL, BigDecimal.valueOf(100), 4000),
              new FinancialTransactionDTO(SELL, BigDecimal.valueOf(300), 10000));

        final var taxes = new TaxPayableCalculatorService().calculateTaxes(financialTransactionsDTO);

        Assertions.assertThat(taxes)
              .hasSize(8)
              .containsExactly(
                    new TaxDTO(BigDecimal.valueOf(0)),
                    new TaxDTO(BigDecimal.valueOf(0)),
                    new TaxDTO(BigDecimal.valueOf(30000)),
                    new TaxDTO(BigDecimal.valueOf(0)),
                    new TaxDTO(BigDecimal.valueOf(0)),
                    new TaxDTO(BigDecimal.valueOf(0)),
                    new TaxDTO(BigDecimal.valueOf(0)),
                    new TaxDTO(BigDecimal.valueOf(281400)));
    }
}
