package br.com.capitalgains.mapper;

import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.BUY;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.capitalgains.dto.FinancialTransactionDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class FinantionalTransactionMapperTest {

    @Test
    void should_convert_dto_into_entity() {
        final var financialTransactionDTO = new FinancialTransactionDTO(BUY, BigDecimal.TEN, 100);

        final var financialTransaction = FinantionalTransactionMapper.toEntity(financialTransactionDTO,
              BigDecimal.ZERO);

        assertThat(financialTransaction.getOperation()).isEqualTo(BUY);
        assertThat(financialTransaction.getQuantity()).isEqualTo(100);
        assertThat(financialTransaction.getUnitCost()).isEqualTo(BigDecimal.TEN);
        assertThat(financialTransaction.getTransactionValue()).isEqualTo(BigDecimal.valueOf(1000));
    }
}
