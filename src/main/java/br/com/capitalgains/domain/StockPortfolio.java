package br.com.capitalgains.domain;

import br.com.capitalgains.calculator.CalculateWeightedAveragePrice;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class StockPortfolio {

    @Getter(AccessLevel.PRIVATE)
    private List<FinancialTransaction> financialTransactions = new ArrayList<>();
    private BigDecimal weightedAveragePrice = BigDecimal.ZERO;
    private BigDecimal loss = BigDecimal.ZERO;

    public void addTransaction(final FinancialTransaction financialTransaction) {
        this.financialTransactions.add(financialTransaction);
        calculateWeightedAveragePrice();
    }

    private void calculateWeightedAveragePrice() {
        this.weightedAveragePrice = CalculateWeightedAveragePrice.calculate(this.financialTransactions);
    }

    public void addLoss(final BigDecimal loss) {
        this.loss = this.loss.add(loss);
    }

    public void removeLoss(final BigDecimal loss) {
        if (loss.compareTo(this.loss) >= 0) {
            this.loss = BigDecimal.ZERO;
        } else {
            this.loss = this.loss.subtract(loss);
        }
    }

    public List<BigDecimal> getTaxes() {
        return financialTransactions.stream()
              .map(FinancialTransaction::getTax)
              .toList();
    }
}
