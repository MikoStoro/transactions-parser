package com.progressoft.induction.transactionsparser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class XmlTransactionParser implements TransactionParser {


    private void updateTransaction(Node n, Transaction t){
        String name = n.getNodeName();

        switch (name){
            case "Description":
                t.setDescription(n.getTextContent());
                break;
            case "Direction":
                t.setDirection(n.getTextContent());
                break;
            case "Amount":
                Node child = n.getFirstChild();
                while(child != null) {
                    updateTransaction(child, t);
                    child = child.getNextSibling();
                }
                break;
            case "Value":
                try{
                    t.setAmount(new BigDecimal(n.getTextContent()));
                }catch (RuntimeException e){
                    //in case of invalid number, the value becomes -1
                    t.setAmount(new BigDecimal(-1));
                }
                break;
            case "Currency":
                t.setCurrency(n.getTextContent());
                break;
            default:
                break;
        }

    }

    @Override
    public List<Transaction> parse(File transactionsFile) {
        DocumentBuilder builder = null;
        List<Transaction> result = new ArrayList<Transaction>();
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(transactionsFile);
            document.getDocumentElement().normalize();

            NodeList transactions = document.getElementsByTagName("Transaction");

            for (int i =0; i<transactions.getLength(); i++){

                Transaction transaction = new Transaction();
                Node attribute = transactions.item(i).getFirstChild();

                while (attribute != null){
                    updateTransaction(attribute,transaction);
                    attribute = attribute.getNextSibling();
                }

                result.add(transaction);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }



        return result;
    }
}
