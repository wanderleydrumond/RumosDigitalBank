package pt.drumond.rumosdigitalbank.service.interfaces;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.DebitCard;

import java.util.ArrayList;

public interface DebitCardService {
    DebitCard create(Customer cardHolder);
    DebitCard update(boolean isVirgin);
    DebitCard update(String pin);
    DebitCard findBySerialNumber(String serialNumber);
    ArrayList<DebitCard> findAllByAccount(Account account);
}