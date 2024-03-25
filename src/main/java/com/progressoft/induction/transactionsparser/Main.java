package com.progressoft.induction.transactionsparser;

import java.io.Console;
import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CsvTransactionParser csv = new CsvTransactionParser();
        XmlTransactionParser xml = new XmlTransactionParser();

        List<Transaction> csvResult = csv.parse(new File("src/main/resources/transactions.csv"));
        csvResult.forEach((item) -> System.out.println(item.toString()));

        System.out.println("\n");

        List<Transaction> xmlResult = xml.parse(new File("src/main/resources/transactions.xml"));
        xmlResult.forEach((item) -> System.out.println(item.toString()));

    }

}
