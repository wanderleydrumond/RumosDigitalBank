package pt.drumond.rumosdigitalbank.service.implementations;

import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.enums.ResponseType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.repository.interfaces.AccountRepository;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;
import pt.drumond.rumosdigitalbank.service.interfaces.CustomerService;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Contains all methods responsible for the businees rules related to accounts.
 */
public class AccountServiceImplementation implements AccountService {
    private CustomerService customerServiceImplementation;
    private AccountRepository accountListRepositoryImplementation;
    private CardService cardServiceImplementation;
    private MovementService movimentServiceImplementation;

    public AccountServiceImplementation(CustomerService customerServiceImplementation, MovementService movementServiceImplementation, CardService cardServiceImplementation, AccountRepository accountListRepositoryImplementation) {
        this.customerServiceImplementation = customerServiceImplementation;
        this.movimentServiceImplementation = movementServiceImplementation;
        this.cardServiceImplementation = cardServiceImplementation;
        this.accountListRepositoryImplementation = accountListRepositoryImplementation;
    }

    /**
     * Creates a new account.
     *
     * @return the new account created
     */
    @Override
    public Account create(Account account, Customer mainHolder) {
        account.getMovements().add(movimentServiceImplementation.create(account.getBalance(), MovementType.DEPOSIT)); // Adiciona movimento ?? conta

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
    public Account getByCode(String code) {
        return accountListRepositoryImplementation.findByCode(code);
    }

    @Override
    public boolean addSecondaryHolder(Account loggedAccount, Customer secondaryHolder) {
        ArrayList<Customer> secondaryHolders = loggedAccount.getSecondaryHolders();
        if (secondaryHolders.stream().anyMatch(customerElement -> customerElement.getNif().equals(secondaryHolder.getNif())) || loggedAccount.getMainHolder().getNif().equals(secondaryHolder.getNif())) {
            return false;
        }
        secondaryHolders.add(secondaryHolder);
        accountListRepositoryImplementation.update(loggedAccount);
        return true;
    }

    @Override
    public boolean deposit(Account destinationAccount, double depositValue, MovementType movementType) {
        destinationAccount.setBalance(destinationAccount.getBalance() + depositValue);
        destinationAccount.getMovements().add(movimentServiceImplementation.create(depositValue, movementType));

        return accountListRepositoryImplementation.update(destinationAccount) != null;
    }

    @Override
    public ResponseType transfer(Account originAccount, double value, String destinationAccountCode) {
        Account destinationAccount = getByCode(destinationAccountCode); // Procura a conta de destino
        if (destinationAccount == null) { // Se n??o encontrar
            return ResponseType.INEXISTENT;
        }
        ResponseType responseTypeWithdraw = withdraw(value, originAccount, MovementType.TRANSFER_OUT); // Faz o saque e recebe a resposta

        if (responseTypeWithdraw.equals(ResponseType.SUCCESS)) { // Se o retorno do m??todo de saque (com o valor determinado e o tipo de movimento configurado como transfer??ncia de sa??da, que em tese, ?? a mesma coisa que o saque) for do tipo enum SUCCESS
            deposit(destinationAccount, value, MovementType.TRANSFER_IN);// Ent??o faz o dep??sito na conta a ser pesquisada
        }
        return responseTypeWithdraw; // S?? pode ser SUCCESS ou INSUFFICIENT_BALANCE
    }

    @Override
    public ResponseType withdraw(double value, Account accountToBeDebited, MovementType movementType) {
        if (movementType.equals(MovementType.WITHDRAW)) { // Se o tipo de movimenta????o for saque (pode ser TRANFER_OUT ou WITHDRAW)
            ArrayList<Movement> withdraws = accountListRepositoryImplementation.findAllSpecificMovements(MovementType.WITHDRAW, accountToBeDebited); // busca todos os movimentos do tipo sque feitos pelo cliente
            LocalDate today = LocalDate.now();
            double amountWithdrawToday = 0;
            for (Movement withdrawElement : withdraws) { // Percorrer a lista de movimentos, cujo tipo ?? saque
                if (withdrawElement.getDate().isEqual(today)) { // Se a data do sque for igual ?? data de hoje
                    amountWithdrawToday += withdrawElement.getValue(); // Incrementa do valor de saque di??rio
                }
            }
            if (amountWithdrawToday >= 500.) { // Se o valor do saque di??rio for maior ou igual a 500
                return ResponseType.WITHDRAW_OVERFLOW; // N??o ?? poss??vel realizar a opera????o
            }
        }
        if (accountToBeDebited.getBalance() < value) { // Se o saldo da conta for infasrior ao valor do saquue
            return ResponseType.INSUFFICIENT_BALANCE; // N??o ?? poss??vel realizar a opera????o
        }
        accountToBeDebited.setBalance(accountToBeDebited.getBalance() - value); // atualiza o saldo da conta
        accountToBeDebited.getMovements().add(movimentServiceImplementation.create(value, movementType)); // adiciona um movimento ?? conta do mesmo tipo passado por par??metro (TRANSFER_OUT ou  WITHDRAW)
        return ResponseType.SUCCESS; // Opera????o realizada com sucesso
    }

    @Override
    public boolean deleteSecondaryHolder(Account loggedAccount, Customer secondaryHolder) {
        Card creditCardOwnedByCustomerToBeDeleted = null, debitCardOwnedByCustomerToBeDeleted = null;

        for (Card cardElement : accountListRepositoryImplementation.findAllCreditCardsByAccount(loggedAccount)) { // busca todos os cart??es de cr??dito da conta logada
            if (cardElement.getCardHolder().getNif().equals(secondaryHolder.getNif())) { // Se o nif do dono do cart??o for igual no NIF do titular que dever ser removido
                creditCardOwnedByCustomerToBeDeleted = cardElement;
            }
        }

        if (creditCardOwnedByCustomerToBeDeleted != null && creditCardOwnedByCustomerToBeDeleted.getPlafondBalance() < creditCardOwnedByCustomerToBeDeleted.getMonthyPlafond()) { //Se esse titular secund??rio tiver cart??o de cr??dito e n??o tiver d??vida no mesmo
            return false;
        }

        for (Card cardElement : accountListRepositoryImplementation.findAllDebitCardsByAccount(loggedAccount)) { // busca todos os cart??es de d??bito da conta logada
            if (cardElement.getCardHolder().getNif().equals(secondaryHolder.getNif())) { // Se o nif do dono do cart??o for igual no NIF do titular que dever ser removido
                debitCardOwnedByCustomerToBeDeleted = cardElement;
            }
        }

        loggedAccount.getSecondaryHolders().removeIf(customerElement -> customerElement.getNif().equals(secondaryHolder.getNif())); // Excluir o cliente secund??rio antes de verificar se ele existe em alguma outra conta
        loggedAccount.getCards().removeIf(cardElement -> cardElement.getCardHolder().getNif().equals(secondaryHolder.getNif())); // Excluir todos os cart??es daquele cliente na conta logada

        accountListRepositoryImplementation.update(loggedAccount); // atualiza a situa????o dessa conta na base de dados

        if (debitCardOwnedByCustomerToBeDeleted != null) {
            cardServiceImplementation.delete(debitCardOwnedByCustomerToBeDeleted); // deleta o cart??o de d??bito da base de dados
        }

        if (creditCardOwnedByCustomerToBeDeleted != null) {
            cardServiceImplementation.delete(creditCardOwnedByCustomerToBeDeleted); // deleta o cart??o de cr??dito da base de dados
        }

        int timesOfCustomerFound = 0;
        // Verificar se o cliente possui alguma outra conta no banco
        ArrayList<Account> allAccounts = accountListRepositoryImplementation.findAll();
        for (Account accountElement : allAccounts) { // Percorrer a lista de contas
            if (accountElement.getMainHolder().getNif().equals(secondaryHolder.getNif())) { // Se encontrar o referido cliente como cliente principal de outra conta
                timesOfCustomerFound++; // acrescenta 1 ao contador
            }
            for (Customer customerElement : accountElement.getSecondaryHolders()) { // dentro do objeto conta, percorre a lista de clientes secund??rios
                if (customerElement.getNif().equals(secondaryHolder.getNif())) { // Se encontrar o referido cliente como cliente secund??rio de outra conta
                    timesOfCustomerFound++; // acrescenta 1 ao contador
                }
            }
        }
        // S?? remove da lista principal se n??o achar em alguma outra conta
        if (timesOfCustomerFound == 0) {
            customerServiceImplementation.delete(secondaryHolder);
        }

        return true;
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

    @Override
    public Customer findCustomerByNif(String nif, Account loggedAccount) {
        Customer customer;
        if (loggedAccount.getMainHolder().getNif().equals(nif)) {
            customer = loggedAccount.getMainHolder();
        } else {
            customer = loggedAccount.getSecondaryHolders().stream().filter(customerElement -> customerElement.getNif().equals(nif)).findFirst().orElse(null);
        }

        return customer;
    }

    @Override
    public Boolean isMainHolder(Customer customerToBeDeleted, Account loggedAccount) {
        return loggedAccount.getMainHolder().getNif().equals(customerToBeDeleted.getNif());
    }

    @Override
    public Card getCardBySerialNumberOnCurrentAccount(Account loggedAccount, String cardSerialNumber) {
        return loggedAccount.getCards().stream().filter(cardElement -> cardElement.getSerialNumber().equals(cardSerialNumber)).findFirst().orElse(null);
    }

    @Override
    public Account getAccountByCardSerialNumber(String cardSerialNumber) {
        return accountListRepositoryImplementation.findByCardSerialNumber(cardSerialNumber);
    }

    private boolean existsThisTypeCardForThisHolder(Customer cardHolder, ArrayList<Card> cards) {
        boolean exists = false;
        if (cards.size() > 0) { // Se a conta j?? tiver o tipo de cart??o.
            for (Card cardElement : cards) { // Ver se quem pediu este tipo de cart??o j?? tem um
                if (cardElement.getCardHolder().getNif().equals(cardHolder.getNif())) { // se tiver
                    exists = true;
                    break;
                }
            }
        }

        return exists;
    }

    @Override
    public void loadDatabase(ArrayList<Customer> customers, ArrayList<Card> cards, ArrayList<Movement> movements) {
        accountListRepositoryImplementation.loadDatabase(customers, cards, movements);
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
    public ResponseType delete(Account accountToBeDeleted) {
        if (accountToBeDeleted.getCards().stream().filter(cardElement -> cardElement.getMonthyPlafond() > 0. && cardElement.getPlafondBalance() < cardElement.getMonthyPlafond()).collect(Collectors.toCollection(ArrayList::new)).size() > 0) { // na conta, percorre a lista de cart??es e busca todos os cart??es de cr??dito que tenham d??vida, caso exista algum
            return ResponseType.THERE_ARE_DEBTS; // H?? cart??es de cr??dito em d??bito
        }
        if (accountToBeDeleted.getBalance() > 0.) { // Caso o saldo da conta n??o esteja zerado
            return ResponseType.BALANCE_BIGGER_THAN_ZERO;
        }

        ArrayList<Customer> allCustomersInAccountToBeDeleted = new ArrayList<>(accountToBeDeleted.getSecondaryHolders());
        allCustomersInAccountToBeDeleted.add(accountToBeDeleted.getMainHolder());

        accountListRepositoryImplementation.delete(accountToBeDeleted);

        HashSet<Customer> customersThatAreInAnotherAccount = new HashSet<>();
        ArrayList<Account> allAccounts = accountListRepositoryImplementation.findAll();

        for (Account anotherAccount : allAccounts) {
            for (Customer customerElement : allCustomersInAccountToBeDeleted) {
                if (anotherAccount.getMainHolder().getNif().equals(customerElement.getNif())) {
                    customersThatAreInAnotherAccount.add(customerElement);
                    break;
                }
                for (Customer secondaryHolderInAnotherAccount : anotherAccount.getSecondaryHolders()) {
                    if (secondaryHolderInAnotherAccount.getNif().equals(customerElement.getNif())) {
                        customersThatAreInAnotherAccount.add(customerElement);
                        break;
                    }
                }
            }
        }
        boolean hasAnotherAccount = false;
        for (Customer customerElement : allCustomersInAccountToBeDeleted) {
            for (Customer customerThatAreInAnotherAccount : customersThatAreInAnotherAccount) {
                if (customerElement.getNif().equals(customerThatAreInAnotherAccount.getNif())) {
                    hasAnotherAccount = true;
                }
            }
            if (!hasAnotherAccount) {
                customerServiceImplementation.delete(customerElement);
            }
            hasAnotherAccount = false;
        }

        return ResponseType.SUCCESS;
    }
}