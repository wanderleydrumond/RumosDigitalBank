package pt.drumond.rumosdigitalbank.repository.implementations;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.DebitCard;
import pt.drumond.rumosdigitalbank.repository.interfaces.DebitCardRepository;

import java.util.ArrayList;

public class DebitCardListRepositoryImplementation implements DebitCardRepository {

    ArrayList<DebitCard> tableDebitCards = new ArrayList<>();

    @Override
    public DebitCard create(DebitCard debitCard) {
        debitCard.setSerialNumber(String.valueOf(80 + tableDebitCards.size()));
//        debitCard.setPin(String.valueOf((int)(Math.random() * 4)));
        debitCard.setPin("1234");
        tableDebitCards.add(debitCard);

        return debitCard;
    }

    @Override
    public DebitCard update(DebitCard debitCard) {
        tableDebitCards.set(tableDebitCards.indexOf(debitCard), debitCard);

        return debitCard;
    }

    @Override
    public DebitCard findBySerialNumber(String serialNumber) {

        return tableDebitCards.stream().filter(debitCardElement -> debitCardElement.getSerialNumber().equals(serialNumber)).findFirst().orElse(null);
    }

    @Override
    public ArrayList<DebitCard> findAllByAccount(Account account) {
        //TODO implement method?
        return null;
    }
}