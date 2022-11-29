package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.DebitCard;

import java.util.ArrayList;

public interface DebitCardRepository {
    DebitCard create(DebitCard debitCard);
    DebitCard update(DebitCard debitCard);
    DebitCard findBySerialNumber(String serialNumber);
    ArrayList<DebitCard> findAllByAccount(Account account);
}