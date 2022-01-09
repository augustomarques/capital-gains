package br.com.capitalgains.calculator;

import br.com.capitalgains.domain.FinancialTransaction;
import br.com.capitalgains.domain.TypeFinancialTransactionEnum;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculateWeightedAveragePrice {

    public static BigDecimal calculate(final List<FinancialTransaction> financialTransactions) {
        if (financialTransactions.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return financialTransactions.stream()
              .filter(financialOperation -> TypeFinancialTransactionEnum.BUY == financialOperation.getOperation())
              .map(FinancialTransaction::getTransactionValue)
              .reduce(BigDecimal.ZERO, BigDecimal::add)
              .divide(BigDecimal.valueOf(getTotalPurchaseShares(financialTransactions)), 0, RoundingMode.HALF_UP);
    }

    private static int getTotalPurchaseShares(final List<FinancialTransaction> financialTransactions) {
        return financialTransactions.stream()
              .filter(financialOperation -> TypeFinancialTransactionEnum.BUY == financialOperation.getOperation())
              .mapToInt(FinancialTransaction::getQuantity)
              .sum();
    }
}
