package br.com.capitalgains.service.addmovement;

import java.math.BigDecimal;

public enum TaxRangeEnum {

    TAX_FREE_RANGE(BigDecimal.valueOf(0), BigDecimal.valueOf(0)),
    TWENTY_PERCENT_TAX_RANGE(BigDecimal.valueOf(20001), BigDecimal.valueOf(0.2));

    private final BigDecimal initialValue;
    private final BigDecimal tax;

    TaxRangeEnum(final BigDecimal initialValue, final BigDecimal tax) {
        this.initialValue = initialValue;
        this.tax = tax;
    }

    public static BigDecimal getTaxByValue(final BigDecimal financialTransactionValue) {
        var taxPercentage = BigDecimal.ZERO;
        for (final TaxRangeEnum taxRangeEnum : TaxRangeEnum.values()) {
            if (financialTransactionValue.compareTo(taxRangeEnum.initialValue) >= 0) {
                taxPercentage = taxRangeEnum.tax;
            } else {
                return taxPercentage;
            }
        }
        return taxPercentage;
    }
}
