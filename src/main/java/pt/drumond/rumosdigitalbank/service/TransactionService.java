package pt.drumond.rumosdigitalbank.service;

import pt.drumond.rumosdigitalbank.model.Transaction;
import pt.drumond.rumosdigitalbank.model.TransactionType;

import java.time.LocalDate;

/**
 * Contains all methods responsible for the businees rules related to transactions.
 */
public class TransactionService {
    /**
     * Creates a new transaction.
     *
     * @param value           to be used on the created transaction
     * @param transactionType the type of the created transaction
     * @return a new transaction
     */
    public Transaction createTransaction(double value, TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDate.now());
        transaction.setValue(value);
        transaction.setType(transactionType);

        return transaction;
    }
}