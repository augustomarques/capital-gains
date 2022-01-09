//package br.com.amarques.capitalgains.entrypoint;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//class FinancialTransactionEntrypointTest {
//
//    @Captor
//    private ArgumentCaptor<String> argumentCaptor;
//    @Mock
//    private PrintStream printStream;
//
//    @Test
//    @Disabled
//    public void validUserInput_ShouldResultInExpectedOutput() throws IOException {
//        String userInput = "[{\"operation\":\"buy\", \"unit-cost\":10, \"quantity\": 100}]";
//        System.setIn(new ByteArrayInputStream(userInput.getBytes()));
//        System.setIn(new ByteArrayInputStream(System.lineSeparator().getBytes()));
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(baos));
//
//        new FinancialTransactionEntrypoint(System.in, printStream).requestFinancialTransactions();
//
//        verify(printStream, times(3)).println(argumentCaptor.capture());
//
//        System.out.println("TUDO QUE FOI PRINT >>> " + baos.toString());
//
//        String[] lines = baos.toString().split(System.lineSeparator());
//        String actual = lines[lines.length - 1];
//
//        // checkout output
//        String expected = "[{\"tax\":0}]";
//        assertEquals(expected, actual);
//    }
//}
