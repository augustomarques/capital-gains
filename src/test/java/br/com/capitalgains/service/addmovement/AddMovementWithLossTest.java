package br.com.capitalgains.service.addmovement;

import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.BUY;
import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.SELL;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.capitalgains.domain.StockPortfolio;
import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.mapper.FinantionalTransactionMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class AddMovementWithLossTest {

    @Test
    void should_generate_loss_when_the_sales_price_is_less_than_weighted_average_price() {
        final var stockPortfolio = new StockPortfolio();
        stockPortfolio.addTransaction(FinantionalTransactionMapper
              .toEntity(new FinancialTransactionDTO(BUY, BigDecimal.TEN, 10), BigDecimal.ZERO));
        final var financialTransactionDTO = new FinancialTransactionDTO(SELL, BigDecimal.ONE, 10);

        new AddMovementWithLoss(null).addMovement(stockPortfolio, financialTransactionDTO);

        assertThat(stockPortfolio.getTaxes())
              .hasSize(2)
              .containsExactly(BigDecimal.ZERO, BigDecimal.ZERO);

        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.valueOf(90));
    }

    @Test
    void should_not_generate_loss_when_the_selling_price_is_greater_than_weighted_average_price() {
        final var stockPortfolio = new StockPortfolio();
        stockPortfolio.addTransaction(FinantionalTransactionMapper
              .toEntity(new FinancialTransactionDTO(BUY, BigDecimal.ONE, 10), BigDecimal.ZERO));
        final var financialTransactionDTO = new FinancialTransactionDTO(SELL, BigDecimal.valueOf(2), 10);

        new AddMovementWithLoss(null).addMovement(stockPortfolio, financialTransactionDTO);

        assertThat(stockPortfolio.getTaxes())
              .hasSize(1)
              .containsExactly(BigDecimal.ZERO);

        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.ZERO);
    }
}
