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
    public Card update(Card card) {
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
    public void delete(Card cardOwnedByCustomerToBeDeleted) {
        cardRepositoryImplementation.delete(cardOwnedByCustomerToBeDeleted);
    }

    @Override
    public boolean payLoan(Card card, double value) {
        if (card.getPlafondBalance() + value > card.getMonthyPlafond()) { // Se o valor a ser pago é maior que o valor em dívida
            return false;
        }

        card.setPlafondBalance(card.getPlafondBalance() + value); // atualiza o plafond do cartão com o valor pago
        update(card); // atualiza o cartão na base de dados
        return true;
    }

    @Override
    public ArrayList<Card> loadDatabase(ArrayList<Customer> customers) {
        return cardRepositoryImplementation.loadDatabase(customers);
    }
}