package br.com.capitalgains.service.addmovement;

import br.com.capitalgains.calculator.CalculateProfit;
import br.com.capitalgains.domain.StockPortfolio;
import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.mapper.FinantionalTransactionMapper;
import java.math.BigDecimal;

public class AddMovementWithProfit extends AddFinancialTransaction {

    public AddMovementWithProfit(final AddFinancialTransaction addFinancialTransaction) {
        super(addFinancialTransaction);
    }

    @Override
    public void addMovement(final StockPortfolio stockPortfolio,
          final FinancialTransactionDTO financialTransactionDTO) {

        if (financialTransactionDTO.unitCost.compareTo(stockPortfolio.getWeightedAveragePrice()) > 0) {
            final var taxPercentage = TaxRangeEnum.getTaxByValue(financialTransactionDTO.getTransactionValue());
            var taxToBePaid = BigDecimal.ZERO;

            if (BigDecimal.ZERO.compareTo(taxPercentage) < 0) {
                taxToBePaid = calculateTaxAndDiscountLoss(stockPortfolio, financialTransactionDTO, taxPercentage);
            }

            stockPortfolio.addTransaction(FinantionalTransactionMapper.toEntity(financialTransactionDTO, taxToBePaid));

        } else {
            super.addMovement(stockPortfolio, financialTransactionDTO);
        }
    }

    private BigDecimal calculateTaxAndDiscountLoss(final StockPortfolio stockPortfolio,
          final FinancialTransactionDTO financialTransactionDTO, final BigDecimal taxPercentage) {

        final var profitMadeOnTransaction = financialTransactionDTO.unitCost
              .subtract(stockPortfolio.getWeightedAveragePrice())
              .multiply(BigDecimal.valueOf(financialTransactionDTO.quantity));

        final var profitWithoutLoss = CalculateProfit.calculateProfitWithoutPriorLoss(stockPortfolio,
              profitMadeOnTransaction);

        stockPortfolio.removeLoss(profitMadeOnTransaction);

        return profitWithoutLoss.compareTo(BigDecimal.ZERO) == 0
              ? BigDecimal.ZERO : profitWithoutLoss.multiply(taxPercentage).setScale(0);
    }

}
