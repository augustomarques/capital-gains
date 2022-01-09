package br.com.capitalgains.calculator;

import br.com.capitalgains.domain.StockPortfolio;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculateProfit {

    public static BigDecimal calculateProfitWithoutPriorLoss(final StockPortfolio stockPortfolio,
          final BigDecimal profitMadeOnTransaction) {

        if (stockPortfolio.getLoss().compareTo(BigDecimal.ZERO) > 0) {

            if (profitMadeOnTransaction.compareTo(stockPortfolio.getLoss()) >= 0) {
                return profitMadeOnTransaction.subtract(stockPortfolio.getLoss());
            }
            return BigDecimal.ZERO;
        }

        return profitMadeOnTransaction;
    }
}
