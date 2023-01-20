package pt.drumond.rumosdigitalbank.service.implementations.jdbc;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.repository.interfaces.CardRepository;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;

import java.util.ArrayList;
import java.util.List;

public class CardJDBCServiceImplementation implements CardService {

    private CardRepository cardRepositoryImplementation;

    public CardJDBCServiceImplementation(CardRepository cardRepositoryImplementation) {
        this.cardRepositoryImplementation  = cardRepositoryImplementation;
    }

    @Override
    public Card create(Customer cardHolder, boolean isCreditCard) {
        // Used only on Lists
        return null;
    }

    @Override
    public Card create(Customer cardHolder, boolean isCreditCard, Account loggedAccount) {
        Card card = new Card();
        card.setCardHolder(cardHolder);
        card.setVirgin(true);
        card.setPin("1234");
        card.setAccount(loggedAccount);
        if (isCreditCard) {
            card.setMonthyPlafond(100.);
            card.setPlafondBalance(100.);
        }
        return cardRepositoryImplementation.create(card, isCreditCard);
    }

    @Override
    public Card update(String pin, Card card) {
        card.setPin(pin);
        card.setVirgin(false);
        return cardRepositoryImplementation.update(card);
    }

    @Override
    public Card getBySerialNumber(String serialNumber) {
        return cardRepositoryImplementation.findBySerialNumber(serialNumber);
    }

    @Override
    public List<Card> findAllByAccount(Account account) {
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

        card.setPlafondBalance(card.getPlafondBalance() + value); // atualiza o saldo do plafond do cartão com o valor pago
        cardRepositoryImplementation.update(card); // atualiza o cartão na base de dados
        return true;
    }

    @Override
    public boolean makeLoan(Card card, double value) {
        if (card.getPlafondBalance() < value) { // Se não houver plafond disponível
            return false;
        }

        card.setPlafondBalance(card.getPlafondBalance() - value); // atualiza o saldo plafond do cartão com o valor sacado
        cardRepositoryImplementation.update(card);
        return true;
    }

    @Override
    public int getAmountOfCards(int loggedAccountId) {
        return cardRepositoryImplementation.countCards(loggedAccountId, false);
    }

    @Override
    public List<Card> loadDatabase(ArrayList<Customer> customers) {
        return cardRepositoryImplementation.loadDatabase(customers);
    }
}