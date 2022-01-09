package br.com.capitalgains.mapper;

import br.com.capitalgains.domain.FinancialTransaction;
import br.com.capitalgains.dto.FinancialTransactionDTO;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FinantionalTransactionMapper {

    public static FinancialTransaction toEntity(final FinancialTransactionDTO financialTransactionDTO,
          final BigDecimal tax) {
        return new FinancialTransaction(financialTransactionDTO.operation, financialTransactionDTO.unitCost,
              financialTransactionDTO.quantity, tax);
    }
}