package br.com.capitalgains.service.addmovement;

import br.com.capitalgains.domain.StockPortfolio;
import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.mapper.FinantionalTransactionMapper;
import java.math.BigDecimal;

public class AddMovementWithoutProfit extends AddFinancialTransaction {

    public AddMovementWithoutProfit(final AddFinancialTransaction addFinancialTransaction) {
        super(addFinancialTransaction);
    }

    @Override
    public void addMovement(final StockPortfolio stockPortfolio,
          final FinancialTransactionDTO financialTransactionDTO) {

        if (financialTransactionDTO.unitCost.compareTo(stockPortfolio.getWeightedAveragePrice()) == 0) {
            stockPortfolio.addTransaction(
                  FinantionalTransactionMapper.toEntity(financialTransactionDTO, BigDecimal.ZERO));

        } else {
            super.addMovement(stockPortfolio, financialTransactionDTO);
        }
    }
}
