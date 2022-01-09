package br.com.capitalgains.domain;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class FinancialTransaction {

    private final TypeFinancialTransactionEnum operation;
    private final BigDecimal unitCost;
    private final Integer quantity;
    private final BigDecimal tax;

    public FinancialTransaction(final TypeFinancialTransactionEnum operation, final BigDecimal unitCost,
          final Integer quantity, final BigDecimal tax) {
        this.operation = operation;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.tax = tax;
    }

    public BigDecimal getTransactionValue() {
        return BigDecimal.valueOf(quantity).multiply(unitCost);
    }

}
