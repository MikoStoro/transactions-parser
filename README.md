## Transactions Parser

The transaction parser program, reads financial transactions from files and parses them into objects of type `Transaction`.

You are expected to implement two parsers; `CsvTransactionParser` and `XmlTransactionParser`, both of them should implement the interface `TransactionParser`. 
The `CsvTransactionParser` should read CSV files and return them as `List<Transaction>`
The `XmlTransactionParser` should read XML files and return them as `List<Transaction>`

You can use any external library for parsing.


## Sample files

The project includes CSV and XML sample files, see the resources directory.
 

## Optional Bonus Requirements

Write unit tests to test your code