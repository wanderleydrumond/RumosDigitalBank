package pt.drumond.rumosdigitalbank.service.implementations;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.repository.implementations.CardListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.interfaces.CardRepository;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;

import java.util.ArrayList;

public class CardServiceImplementation implements CardService {

    private CardRepository cardRepositoryImplementation = new CardListRepositoryImplementation();

    @Override
    public Card create(Customer cardHolder, boolean isCreditCard) {
        Card card = new Card();
        card.setCardHolder(cardHolder);
        card.setVirgin(true);
        if (isCreditCard) {
            card.setMonthyPlafond(100.);
            card.setPlafondBalance(100.);
        } else {
            card.setMonthyPlafond(0.);
            card.setPlafondBalance(0.);
        }

        return cardRepositoryImplementation.create(card);
    }

    @Override
    public Card update(boolean isVirgin) {
        return null;
    }

    @Override
    public Card update(String pin) {
        return null;
    }

    @Override
    public Card findBySerialNumber(String serialNumber) {
        return null;
    }

    @Override
    public ArrayList<Card> findAllByAccount(Account account) {
        return null;
    }

    @Override
    public ArrayList<Card> loadDatabase(ArrayList<Customer> customers) {

        return cardRepositoryImplementation.loadDatabase(customers);
    }
}