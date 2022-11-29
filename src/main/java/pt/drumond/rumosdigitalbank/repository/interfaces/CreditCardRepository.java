package pt.drumond.rumosdigitalbank.repository.interfaces;

import pt.drumond.rumosdigitalbank.model.CreditCard;
import pt.drumond.rumosdigitalbank.model.DebitCard;

public interface CreditCardRepository {
    CreditCard save(CreditCard creditCard);
    CreditCard update(CreditCard creditCard);
    CreditCard findBySerialNumber(String serialNumber);
}