package com.progressoft.induction.transactionsparser;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


public class CsvTransactionParser implements TransactionParser{

    private String delimiter = ",";

    private Transaction parseLine (String line) {
        String[] splitLine = line.split(delimiter);
        Transaction result = new Transaction();

        if (splitLine.length < 4){
            //if there is too little data, returns empty transaction
            result.setDescription("incomplete data");
            return result;
        }

        result.setDescription(splitLine[0]);
        result.setDirection(splitLine[1]);

        try {
            result.setAmount(new BigDecimal(splitLine[2]));
        }catch (RuntimeException e){
            //in case of invalid number, the value becomes -1
            result.setAmount(new BigDecimal(-1));
        }

        result.setCurrency(splitLine[3]);
        return result;
    }

    @Override
    public List<Transaction> parse(File transactionsFile) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
            return reader
                    .lines()
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDelimiter(String d){
        this.delimiter = d;
    }
}
