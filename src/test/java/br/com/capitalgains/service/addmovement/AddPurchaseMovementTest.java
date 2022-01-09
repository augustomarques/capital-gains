package br.com.capitalgains.service.addmovement;

import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.BUY;
import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.SELL;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.capitalgains.domain.StockPortfolio;
import br.com.capitalgains.dto.FinancialTransactionDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class AddPurchaseMovementTest {

    @Test
    void should_not_generate_tax_or_loss_for_the_purchase_transaction() {
        final var stockPortfolio = new StockPortfolio();
        final var financialTransactionDTO = new FinancialTransactionDTO(BUY, BigDecimal.TEN, 100);

        new AddPurchaseMovement(null).addMovement(stockPortfolio, financialTransactionDTO);

        assertThat(stockPortfolio.getTaxes())
              .hasSize(1)
              .contains(BigDecimal.ZERO);

        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void should_not_process_the_transaction_when_it_is_not_a_purchase() {
        final var stockPortfolio = new StockPortfolio();
        final var financialTransactionDTO = new FinancialTransactionDTO(SELL, BigDecimal.TEN, 100);

        new AddPurchaseMovement(null).addMovement(stockPortfolio, financialTransactionDTO);

        assertThat(stockPortfolio.getTaxes()).isEmpty();
        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.ZERO);
    }
}
