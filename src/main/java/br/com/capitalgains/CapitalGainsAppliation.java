package br.com.capitalgains;

import br.com.capitalgains.entrypoint.FinancialTransactionEntrypoint;

public class CapitalGainsAppliation {

    public static void main(String[] args) {
        new FinancialTransactionEntrypoint(System.in, System.out).requestFinancialTransactions();
    }
}
