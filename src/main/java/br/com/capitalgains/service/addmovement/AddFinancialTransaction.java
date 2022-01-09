package br.com.capitalgains.service.addmovement;

import br.com.capitalgains.domain.StockPortfolio;
import br.com.capitalgains.dto.FinancialTransactionDTO;
import java.util.Objects;

public abstract class AddFinancialTransaction {

    private final AddFinancialTransaction addFinancialTransaction;

    protected AddFinancialTransaction(final AddFinancialTransaction addFinancialTransaction) {
        this.addFinancialTransaction = addFinancialTransaction;
    }

    public void addMovement(final StockPortfolio stockPortfolio,
          final FinancialTransactionDTO financialTransactionDTO) {
        if (Objects.nonNull(addFinancialTransaction)) {
            addFinancialTransaction.addMovement(stockPortfolio, financialTransactionDTO);
        }
    }

}
