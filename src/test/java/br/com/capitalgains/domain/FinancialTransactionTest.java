package br.com.capitalgains.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class FinancialTransactionTest {

    @Test
    void should_create_financial_transaction() {
        final var operation = TypeFinancialTransactionEnum.BUY;
        final var quantity = 10;
        final var unitCost = BigDecimal.valueOf(100);

        final var financialTransaction = new FinancialTransaction(operation, unitCost, quantity, BigDecimal.TEN);

        assertThat(financialTransaction).isNotNull();
        assertThat(financialTransaction.getOperation()).isEqualTo(operation);
        assertThat(financialTransaction.getQuantity()).isEqualTo(quantity);
        assertThat(financialTransaction.getUnitCost()).isEqualTo(unitCost);
        assertThat(financialTransaction.getTax()).isEqualTo(BigDecimal.TEN);
        assertThat(financialTransaction.getTransactionValue()).isEqualTo(BigDecimal.valueOf(1000));
    }

}
