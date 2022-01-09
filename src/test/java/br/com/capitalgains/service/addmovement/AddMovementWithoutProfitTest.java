package br.com.capitalgains.service.addmovement;

import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.BUY;
import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.SELL;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.capitalgains.domain.StockPortfolio;
import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.mapper.FinantionalTransactionMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class AddMovementWithoutProfitTest {

    @Test
    void should_not_generate_tax_or_loss_when_selling_at_purchase_price() {
        final var stockPortfolio = new StockPortfolio();
        stockPortfolio.addTransaction(
              FinantionalTransactionMapper.toEntity(new FinancialTransactionDTO(BUY, BigDecimal.TEN, 100),
                    BigDecimal.ZERO));
        final var financialTransactionDTO = new FinancialTransactionDTO(SELL, BigDecimal.TEN, 100);
        final var addMovementWithoutProfit = new AddMovementWithoutProfit(null);

        addMovementWithoutProfit.addMovement(stockPortfolio, financialTransactionDTO);

        assertThat(stockPortfolio.getTaxes())
              .hasSize(2)
              .containsExactly(BigDecimal.ZERO, BigDecimal.ZERO);

        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.ZERO);
    }
}
