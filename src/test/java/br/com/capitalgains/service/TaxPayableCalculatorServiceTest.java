package br.com.capitalgains.service;

import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.BUY;
import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.SELL;

import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.dto.TaxDTO;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TaxPayableCalculatorServiceTest {

    @Test
    void should_return_a_tax_for_each_financial_transaction() {
        final var financialTransactionsDTO = List.of(
              new FinancialTransactionDTO(BUY, BigDecimal.TEN, 100),
              new FinancialTransactionDTO(SELL, BigDecimal.valueOf(15), 50),
              new FinancialTransactionDTO(SELL, BigDecimal.valueOf(15), 50));

        final var taxes = new TaxPayableCalculatorService().calculateTaxes(financialTransactionsDTO);

        Assertions.assertThat(taxes)
              .isNotEmpty()
              .hasSize(3)
              .contains(new TaxDTO(BigDecimal.ZERO), new TaxDTO(BigDecimal.ZERO), new TaxDTO(BigDecimal.ZERO));
    }
}
