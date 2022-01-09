package br.com.capitalgains.entrypoint;

import br.com.capitalgains.converter.JsonConverter;
import br.com.capitalgains.service.TaxPayableCalculatorService;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class FinancialTransactionEntrypoint {

    private static final String DEFAULT_MESSAGE = "Informe a lista de operações financeiras, ou pressione ENTER (linha vazia) para encerrar:";

    private final Scanner scanner;
    private final PrintStream printStream;

    public FinancialTransactionEntrypoint(final InputStream inputStream, final PrintStream printStream) {
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    public void requestFinancialTransactions() {
        printStream.println(DEFAULT_MESSAGE);
        final var line = scanner.nextLine();
        if (!line.equals("")) {
            final var financialTransactions = JsonConverter.convertToObjects(line);
            final var taxes = new TaxPayableCalculatorService().calculateTaxes(financialTransactions);

            printStream.println(new StringBuilder(JsonConverter.convertToJson(taxes)).append(System.lineSeparator()));

            requestFinancialTransactions();
        }
    }
}
