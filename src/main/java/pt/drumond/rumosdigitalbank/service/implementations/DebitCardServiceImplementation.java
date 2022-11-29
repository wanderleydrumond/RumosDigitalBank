package pt.drumond.rumosdigitalbank.service.implementations;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.DebitCard;
import pt.drumond.rumosdigitalbank.repository.implementations.DebitCardListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.interfaces.DebitCardRepository;
import pt.drumond.rumosdigitalbank.service.interfaces.DebitCardService;

import java.util.ArrayList;

public class DebitCardServiceImplementation implements DebitCardService {

    private DebitCardRepository debitCardRepositoryImplementation = new DebitCardListRepositoryImplementation();

    @Override
    public DebitCard create(Customer cardHolder) {
        DebitCard debitCard = new DebitCard();
        debitCard.setCardHolder(cardHolder);
        debitCard.setVirgin(true);

        return debitCardRepositoryImplementation.create(debitCard);
    }

    @Override
    public DebitCard update(boolean isVirgin) {
        return null;
    }

    @Override
    public DebitCard update(String pin) {
        return null;
    }

    @Override
    public DebitCard findBySerialNumber(String serialNumber) {
        return null;
    }

    @Override
    public ArrayList<DebitCard> findAllByAccount(Account account) {
        return null;
    }
}