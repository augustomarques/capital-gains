package br.com.capitalgains.service.addmovement;

import br.com.capitalgains.domain.StockPortfolio;
import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.mapper.FinantionalTransactionMapper;
import java.math.BigDecimal;

public class AddMovementWithLoss extends AddFinancialTransaction {

    public AddMovementWithLoss(final AddFinancialTransaction addFinancialTransaction) {
        super(addFinancialTransaction);
    }

    @Override
    public void addMovement(final StockPortfolio stockPortfolio,
          final FinancialTransactionDTO financialTransactionDTO) {

        if (financialTransactionDTO.unitCost.compareTo(stockPortfolio.getWeightedAveragePrice()) < 0) {
            final var loss = stockPortfolio.getWeightedAveragePrice()
                  .subtract(financialTransactionDTO.unitCost)
                  .multiply(BigDecimal.valueOf(financialTransactionDTO.quantity));

            stockPortfolio.addLoss(loss);

            stockPortfolio.addTransaction(
                  FinantionalTransactionMapper.toEntity(financialTransactionDTO, BigDecimal.ZERO));

        } else {
            super.addMovement(stockPortfolio, financialTransactionDTO);
        }

    }
}
