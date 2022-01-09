package br.com.capitalgains.service.addmovement;

import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.BUY;
import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.SELL;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.capitalgains.domain.StockPortfolio;
import br.com.capitalgains.dto.FinancialTransactionDTO;
import br.com.capitalgains.mapper.FinantionalTransactionMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class AddMovementWithProfitTest {

    @Test
    void should_calculate_sales_tax_with_profit_above_the_weighted_average_price() {
        final var stockPortfolio = new StockPortfolio();
        stockPortfolio.addTransaction(FinantionalTransactionMapper
              .toEntity(new FinancialTransactionDTO(BUY, BigDecimal.valueOf(200), 100), BigDecimal.ZERO));
        final var financialTransactionDTO = new FinancialTransactionDTO(SELL, BigDecimal.valueOf(250), 100);

        new AddMovementWithProfit(null).addMovement(stockPortfolio, financialTransactionDTO);

        assertThat(stockPortfolio.getTaxes())
              .hasSize(2)
              .containsExactly(BigDecimal.ZERO, BigDecimal.valueOf(1000));

        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    void should_calculate_sales_tax_with_a_profit_above_the_weighted_average_price_discounting_the_previous_loss() {
        final var stockPortfolio = new StockPortfolio();
        stockPortfolio.addTransaction(FinantionalTransactionMapper
              .toEntity(new FinancialTransactionDTO(BUY, BigDecimal.valueOf(200), 100), BigDecimal.ZERO));
        stockPortfolio.addTransaction(FinantionalTransactionMapper
              .toEntity(new FinancialTransactionDTO(SELL, BigDecimal.valueOf(100), 30), BigDecimal.ZERO));
        stockPortfolio.addLoss(BigDecimal.valueOf(3000));
        final var financialTransactionDTO = new FinancialTransactionDTO(SELL, BigDecimal.valueOf(500), 70);

        new AddMovementWithProfit(null).addMovement(stockPortfolio, financialTransactionDTO);

        assertThat(stockPortfolio.getTaxes())
              .hasSize(3)
              .containsExactly(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.valueOf(3600));

        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    void should_deduct_all_tax_from_previous_losses_leaving_no_residual_loss() {
        final var stockPortfolio = new StockPortfolio();
        stockPortfolio.addTransaction(FinantionalTransactionMapper
              .toEntity(new FinancialTransactionDTO(BUY, BigDecimal.valueOf(2000), 100), BigDecimal.ZERO));
        stockPortfolio.addTransaction(FinantionalTransactionMapper
              .toEntity(new FinancialTransactionDTO(SELL, BigDecimal.valueOf(1000), 50), BigDecimal.ZERO));
        stockPortfolio.addLoss(BigDecimal.valueOf(50000));
        final var financialTransactionDTO = new FinancialTransactionDTO(SELL, BigDecimal.valueOf(3000), 50);

        new AddMovementWithProfit(null).addMovement(stockPortfolio, financialTransactionDTO);

        assertThat(stockPortfolio.getTaxes())
              .hasSize(3)
              .containsExactly(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    void should_deduct_all_tax_from_previous_losses_remaining_with_pending_loss() {
        final var stockPortfolio = new StockPortfolio();
        stockPortfolio.addTransaction(FinantionalTransactionMapper
              .toEntity(new FinancialTransactionDTO(BUY, BigDecimal.valueOf(2000), 100), BigDecimal.ZERO));
        stockPortfolio.addTransaction(FinantionalTransactionMapper
              .toEntity(new FinancialTransactionDTO(SELL, BigDecimal.valueOf(1000), 50), BigDecimal.ZERO));
        stockPortfolio.addLoss(BigDecimal.valueOf(50000));
        final var financialTransactionDTO = new FinancialTransactionDTO(SELL, BigDecimal.valueOf(3000), 10);

        new AddMovementWithProfit(null).addMovement(stockPortfolio, financialTransactionDTO);

        assertThat(stockPortfolio.getTaxes())
              .hasSize(3)
              .containsExactly(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.valueOf(40000));
    }

    @Test
    void should_not_generate_tax_or_loss_when_selling_below_the_minimum_value_for_tax_calculation() {
        final var stockPortfolio = new StockPortfolio();
        final var purchaseFinancialTransactionDTO = new FinancialTransactionDTO(BUY, BigDecimal.valueOf(90), 200);
        final var financialTransactionDTO = new FinancialTransactionDTO(SELL, BigDecimal.valueOf(99), 200);
        final var addMovementWithProfit = new AddMovementWithProfit(null);

        addMovementWithProfit.addMovement(stockPortfolio, purchaseFinancialTransactionDTO);
        addMovementWithProfit.addMovement(stockPortfolio, financialTransactionDTO);

        assertThat(stockPortfolio.getTaxes())
              .hasSize(2)
              .containsExactly(BigDecimal.ZERO, BigDecimal.ZERO);

        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.ZERO);
    }

}
