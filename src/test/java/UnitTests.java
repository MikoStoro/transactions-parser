import com.progressoft.induction.transactionsparser.CsvTransactionParser;
import com.progressoft.induction.transactionsparser.Transaction;
import com.progressoft.induction.transactionsparser.XmlTransactionParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.math.BigDecimal;

public class UnitTests {

    private Transaction expectedTransaction;
    private String testFilePath = "src/test/java/resources/";
    @Before
    public void setup(){
        expectedTransaction = new Transaction();
        expectedTransaction.setDescription("Cash withdrawal");
        expectedTransaction.setDirection("Debit");
        expectedTransaction.setAmount(new BigDecimal(150));
        expectedTransaction.setCurrency("JOD");
    }
    @Test
    public void CsvParsingTest(){
        CsvTransactionParser parser = new CsvTransactionParser();
        Transaction actualTransaction = parser.parse(new File(testFilePath + "csvTest.csv")).get(0);
        Assert.assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void CsvInvalidNumberTest(){
        BigDecimal expectedValue = new BigDecimal(-1);
        CsvTransactionParser parser = new CsvTransactionParser();
        Transaction actualTransaction = parser.parse(new File(testFilePath + "csvInvalidNumber.csv")).get(0);
        BigDecimal actualValue = actualTransaction.getAmount();
        Assert.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void CsvIncompleteRowTest(){
        expectedTransaction.setDescription("incomplete data");

        CsvTransactionParser parser = new CsvTransactionParser();
        Transaction actualTransaction = parser.parse(new File(testFilePath + "csvIncompleteData.csv")).get(0);
    }

    @Test
    public void CsvChangedDelimiterTest(){
        CsvTransactionParser parser = new CsvTransactionParser();
        parser.setDelimiter(";");
        Transaction actualTransaction = parser.parse(new File(testFilePath + "csvDelimiter.csv")).get(0);
        Assert.assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void XmlParsingTest(){
        XmlTransactionParser parser = new XmlTransactionParser();
        Transaction actualTransaction = parser.parse(new File(testFilePath + "xmlTest.xml")).get(0);
        Assert.assertEquals(expectedTransaction,actualTransaction);
    }

    @Test
    public void XmlChangedTagOrderTest(){
        XmlTransactionParser parser = new XmlTransactionParser();
        Transaction actualTransaction = parser.parse(new File(testFilePath + "xmlChangedOrder.xml")).get(0);
        Assert.assertEquals(expectedTransaction,actualTransaction);
    }

    @Test
    public void XmlMissingTagTest(){
        expectedTransaction.setDirection(null);
        XmlTransactionParser parser = new XmlTransactionParser();
        Transaction actualTransaction = parser.parse(new File(testFilePath +"xmlMissingTag.xml")).get(0);
        Assert.assertEquals(expectedTransaction,actualTransaction);
    }

    @Test
    public void XmlDoubledTagTest(){
        expectedTransaction.setDirection("Doubled");
        XmlTransactionParser parser = new XmlTransactionParser();
        Transaction actualTransaction = parser.parse(new File(testFilePath +"xmlDoubledTag.xml")).get(0);
        Assert.assertEquals(expectedTransaction,actualTransaction);
    }

    @Test
    public void XmlInvalidNumberTest(){
        expectedTransaction.setAmount(new BigDecimal(-1));
        XmlTransactionParser parser = new XmlTransactionParser();
        Transaction actualTransaction = parser.parse(new File(testFilePath +"xmlInvalidNumber.xml")).get(0);
        Assert.assertEquals(expectedTransaction,actualTransaction);
    }


}
