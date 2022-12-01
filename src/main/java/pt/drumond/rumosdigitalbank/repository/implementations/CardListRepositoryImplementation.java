package pt.drumond.rumosdigitalbank.repository.implementations;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.repository.interfaces.CardRepository;

import java.util.ArrayList;

public class CardListRepositoryImplementation implements CardRepository {

    ArrayList<Card> tableCards = new ArrayList<>();

    @Override
    public Card create(Card card) {
        card.setSerialNumber(String.valueOf(80 + tableCards.size()));
//        debitCard.setPin(String.valueOf((int)(Math.random() * 4)));
        card.setPin("1234");
        tableCards.add(card);

        return card;
    }

    @Override
    public Card update(Card card) {
        tableCards.set(tableCards.indexOf(card), card);

        return card;
    }

    @Override
    public Card findBySerialNumber(String serialNumber) {

        return tableCards.stream().filter(debitCardElement -> debitCardElement.getSerialNumber().equals(serialNumber)).findFirst().orElse(null);
    }

    @Override
    public ArrayList<Card> findAllByAccount(Account account) {
        //TODO implement method?
        return null;
    }
}