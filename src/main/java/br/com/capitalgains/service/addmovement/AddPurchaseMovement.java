package br.com.capitalgains.service.addmovement;

import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.BUY;

import br.com.capitalgains.domain.StockPortfolio;
import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.mapper.FinantionalTransactionMapper;
import java.math.BigDecimal;

public class AddPurchaseMovement extends AddFinancialTransaction {

    public AddPurchaseMovement(final AddFinancialTransaction addFinancialTransaction) {
        super(addFinancialTransaction);
    }

    @Override
    public void addMovement(final StockPortfolio stockPortfolio,
          final FinancialTransactionDTO financialTransactionDTO) {

        if (BUY == financialTransactionDTO.operation) {
            stockPortfolio.addTransaction(
                  FinantionalTransactionMapper.toEntity(financialTransactionDTO, BigDecimal.ZERO));

        } else {
            super.addMovement(stockPortfolio, financialTransactionDTO);
        }
    }
}
