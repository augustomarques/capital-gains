package br.com.capitalgains.service;

import br.com.capitalgains.domain.StockPortfolio;
import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.dto.TaxDTO;
import br.com.capitalgains.service.addmovement.AddFinancialTransaction;
import br.com.capitalgains.service.addmovement.AddMovementWithLoss;
import br.com.capitalgains.service.addmovement.AddMovementWithProfit;
import br.com.capitalgains.service.addmovement.AddMovementWithoutProfit;
import br.com.capitalgains.service.addmovement.AddPurchaseMovement;
import java.util.List;

public class TaxPayableCalculatorService {

    private final AddFinancialTransaction addFinancialTransaction = buildChain();

    public List<TaxDTO> calculateTaxes(final List<FinancialTransactionDTO> financialTransactionsDTO) {
        final var stockPortfolio = new StockPortfolio();

        financialTransactionsDTO.forEach(
              financialTransactionDTO -> addFinancialTransaction.addMovement(stockPortfolio, financialTransactionDTO));

        return stockPortfolio.getTaxes()
              .stream()
              .map(TaxDTO::new)
              .toList();
    }

    private AddFinancialTransaction buildChain() {
        return new AddPurchaseMovement(
              new AddMovementWithoutProfit(
                    new AddMovementWithLoss(
                          new AddMovementWithProfit(
                                null))));
    }
}
