package br.com.capitalgains.dto;

import br.com.capitalgains.domain.TypeFinancialTransactionEnum;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class FinancialTransactionDTO {

    public final TypeFinancialTransactionEnum operation;
    public final BigDecimal unitCost;
    public final Integer quantity;

    public BigDecimal getTransactionValue() {
        return BigDecimal.valueOf(quantity).multiply(unitCost);
    }
}
