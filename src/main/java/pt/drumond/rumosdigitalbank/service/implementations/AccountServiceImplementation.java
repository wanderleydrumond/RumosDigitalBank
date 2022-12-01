package pt.drumond.rumosdigitalbank.service.implementations;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.MovementType;
import pt.drumond.rumosdigitalbank.repository.implementations.AccountListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.implementations.CustomerListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.interfaces.AccountRepository;
import pt.drumond.rumosdigitalbank.repository.interfaces.CustomerRepository;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;
import pt.drumond.rumosdigitalbank.service.interfaces.CustomerService;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.util.ArrayList;

/**
 * Contains all methods responsible for the businees rules related to accounts.
 */
public class AccountServiceImplementation implements AccountService {
    private CustomerService customerServiceImplementation = new CustomerServiceImplementation();
    private AccountRepository accountListRepositoryImplementation = new AccountListRepositoryImplementation();
    private CustomerRepository customerListRepositoryImplementation = new CustomerListRepositoryImplementation();
    private CardService cardServiceImplementation = new CardServiceImplementation();
    private MovementService movimentServiceImplementation = new MovimentServiceImplementation();

    public AccountServiceImplementation() {
    }

    /**
     * Creates a new account.
     *
     * @return the new account created
     */
    @Override
    public Account create(Account account, Customer mainHolder) {
        account.getMovements().add(movimentServiceImplementation.create(account.getBalance(), MovementType.DEPOSIT)); // Adiciona movimento à conta

        return accountListRepositoryImplementation.create(account);
    }

    /**
     * Allows to do several activities and transactions for the logged account.
     */
    @Override
    public Account update(Account account) {

        return accountListRepositoryImplementation.create(account);
    }

    /**
     * Verifies if the first deposit into account has a mininum value of 50.
     *
     * @param depositValue value to be deposited
     * @return <ul>
     * <li>true if the <span style="color:#ffb86c; font-style: italic">depositValue</span> is 50 or superior</li>
     * <li>false if the <span style="color:#ffb86c; font-style: italic">depositValue</span> is minor than 50</li>
     * </ul>
     */
    public boolean validateInitialDeposit(double depositValue) {
        return depositValue >= 50.;
    }

    @Override
    public Account findByCode(String code) {
        return accountListRepositoryImplementation.findByCode(code);
    }

    @Override
    public void addSecondaryHolder(Account loggedAccount, Customer secondaryHolder) {
        customerListRepositoryImplementation.create(secondaryHolder);
        loggedAccount.getSecondaryHolders().add(secondaryHolder);
        accountListRepositoryImplementation.update(loggedAccount);
    }

    @Override
    public void deposit(Account destinationAccount, double depositValue, MovementType movementType) {
        destinationAccount.setBalance(destinationAccount.getBalance() + depositValue);
        destinationAccount.getMovements().add(movimentServiceImplementation.create(depositValue, movementType));
        accountListRepositoryImplementation.update(destinationAccount);
    }

    @Override
    public boolean transfer(Account originAccount, double value, String destinationAccountCode) {
        if (withdraw(value, originAccount, MovementType.TRANSFER_OUT)) {
            deposit(findByCode(destinationAccountCode), value, MovementType.TRANSFER_IN);

            return true;
        }

        return false;
    }

    private boolean withdraw(double value, Account destinationAccount, MovementType movementType) {
        if (destinationAccount.getBalance() < value) {
            return false;
        }
        destinationAccount.setBalance(destinationAccount.getBalance() - value);
        destinationAccount.getMovements().add(movimentServiceImplementation.create(value, movementType));
        return true;
    }

    @Override
    public void payLoan(Account loggedAccount, double value, String creditCardSerialNumber) {
        //TODO implement method
    }

    @Override
    public void deleteSecondaryHolder(Account loggedAccount, Customer secondaryHolder) {
        loggedAccount.getSecondaryHolders().removeIf(customerElement -> customerElement.getNif().equals(secondaryHolder.getNif())); // Excluir o cliente secundário antes de verificar se ele existe em alguma outra conta
        int timesOfCustomerFound = 0;
        // Verificar se o cliente possui alguma outra conta no banco
        ArrayList<Account> allAccounts = accountListRepositoryImplementation.findAll();
        for (Account accountElement : allAccounts) { // Percorrer a lista de contas
            if (accountElement.getMainHolder().getNif().equals(secondaryHolder.getNif())) { // Se encontrar o referido cliente como cliente principal de outra conta
                timesOfCustomerFound++; // acrescenta 1 ao contador
            }
            for (Customer customerElement : accountElement.getSecondaryHolders()) { // dentro do objeto conta, percorre a lista de clientes secundários
                if (customerElement.getNif().equals(secondaryHolder.getNif())) { // Se encontrar o referido cliente como cliente secundário de outra conta
                    timesOfCustomerFound++; // acrescenta 1 ao contador
                }
            }
        }
        // Só remove da lista principal se não achar em lugar algum
        if (timesOfCustomerFound == 0) {
            customerServiceImplementation.delete(secondaryHolder);
        }
    }

    @Override
    public Card addDebitCard(Account loggedAccount, Customer cardHolder) {
        ArrayList<Card> debitCards = getDebitCards(loggedAccount);

        return getCard(cardHolder, debitCards, false, loggedAccount);
    }

    private Card getCard(Customer cardHolder, ArrayList<Card> cards, boolean isCreditCard, Account loggedAccount) {
        if (existsThisTypeCardForThisHolder(cardHolder, cards)) {

            return null;
        } else {
            Card card = cardServiceImplementation.create(cardHolder, isCreditCard);
            loggedAccount.getCards().add(card);
            accountListRepositoryImplementation.update(loggedAccount);

            return card;
        }
    }

    @Override
    public Card addCreditCard(Account loggedAccount, Customer cardHolder) {
        ArrayList<Card> creditCards = getCreditCards(loggedAccount);

        return getCard(cardHolder, creditCards, true, loggedAccount);
    }

    @Override
    public ArrayList<Card> getDebitCards(Account loggedAccount) {

        return accountListRepositoryImplementation.findAllDebitCardsByAccount(loggedAccount);
    }

    @Override
    public ArrayList<Card> getCreditCards(Account loggedAccount) {

        return accountListRepositoryImplementation.findAllCreditCardsByAccount(loggedAccount);
    }

    private boolean existsThisTypeCardForThisHolder(Customer cardHolder, ArrayList<Card> cards) {
        boolean exists = false;
        if (cards.size() > 0) { // Se a conta já tiver o tipo de cartão.
            for (Card cardElement : cards) { // Ver se quem pediu este tipo de cartão já tem um
                if (cardElement.getCardHolder().getNif().equals(cardHolder.getNif())) { // se tiver
                    exists = true;
                    break;
                }
            }
        }

        return exists;
    }

    @Override
    public void loadDatabase() {
        accountListRepositoryImplementation.loadDatabase();
    }

    @Override
    public int getAmountOfSecondaryHolders(Account loggedAccount) {

        return loggedAccount.getSecondaryHolders().size();
    }

    @Override
    public int getAmountOfCreditCards(Account loggedAccount) {

        return accountListRepositoryImplementation.findAllCreditCardsByAccount(loggedAccount).size();
    }

    @Override
    public int getAmountOfDebitCards(Account loggedAccount) {

        return accountListRepositoryImplementation.findAllDebitCardsByAccount(loggedAccount).size();
    }

    @Override
    public void delete(Account accountToBeDeleted) {
        accountListRepositoryImplementation.delete(accountToBeDeleted);
    }
}