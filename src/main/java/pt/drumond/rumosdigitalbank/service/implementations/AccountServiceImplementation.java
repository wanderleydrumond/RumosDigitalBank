package pt.drumond.rumosdigitalbank.service.implementations;

import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.enums.ResponseType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.repository.implementations.AccountListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.interfaces.AccountRepository;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;
import pt.drumond.rumosdigitalbank.service.interfaces.CustomerService;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Contains all methods responsible for the businees rules related to accounts.
 */
public class AccountServiceImplementation implements AccountService {
    private CustomerService customerServiceImplementation = new CustomerServiceImplementation();
    private AccountRepository accountListRepositoryImplementation = new AccountListRepositoryImplementation();
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
    public void deposit(Account destinationAccount, double depositValue, MovementType movementType) {
        destinationAccount.setBalance(destinationAccount.getBalance() + depositValue);
        destinationAccount.getMovements().add(movimentServiceImplementation.create(depositValue, movementType));
        accountListRepositoryImplementation.update(destinationAccount);
    }

    @Override
    public boolean transfer(Account originAccount, double value, String destinationAccountCode) {
        if (withdraw(value, originAccount, MovementType.TRANSFER_OUT).equals(ResponseType.SUCCESS)) { // Se o retorno do método de saque (com o valor determinado e o tipo de movimento configurado como transferência de saída, que em tese, é a mesma coisa que o saque) for do tipo enum SUCCESS
            deposit(findByCode(destinationAccountCode), value, MovementType.TRANSFER_IN);// Então faz o depósito na conta a ser pesquisada

            return true;
        }
        return false;
    }

    @Override
    public ResponseType withdraw(double value, Account accountToBeDebited, MovementType movementType) {
        if (movementType.equals(MovementType.WITHDRAW)) { // Se o tipo de movimentação for saque (pode ser TRANFER_OUT ou WITHDRAW)
            ArrayList<Movement> withdraws = accountListRepositoryImplementation.findAllSpecificMovements(MovementType.WITHDRAW, accountToBeDebited); // busca todos os movimentos do tipo sque feitos pelo cliente
            LocalDate today = LocalDate.now();
            double amountWithdrawToday = 0;
            for (Movement withdrawElement : withdraws) { // Percorrer a lista de movimentos, cujo tipo é saque
                if (withdrawElement.getDate().isEqual(today)) { // Se a data do sque for igual à data de hoje
                    amountWithdrawToday += withdrawElement.getValue(); // Incrementa do valor de saque diário
                }
            }
            if (amountWithdrawToday >= 500.) { // Se o valor do saque diário for maior ou igual a 500
                return ResponseType.WITHDRAW_OVERFLOW; // Não é possível realizar a operação
            }
        }
        if (accountToBeDebited.getBalance() < value) { // Se o saldo da conta for infasrior ao valor do saquue
            return ResponseType.INSUFFICIENT_BALANCE; // Não é possível realizar a operação
        }
        accountToBeDebited.setBalance(accountToBeDebited.getBalance() - value); // atualiza o saldo da conta
        accountToBeDebited.getMovements().add(movimentServiceImplementation.create(value, movementType)); // adiciona um movimento à conta do mesmo tipo passado por parâmetro (TRANSFER_OUT ou  WITHDRAW)
        return ResponseType.SUCCESS; // Operação realizada com sucesso
    }

    @Override
    public boolean deleteSecondaryHolder(Account loggedAccount, Customer secondaryHolder) {
        Card creditCardOwnedByCustomerToBeDeleted = null, debitCardOwnedByCustomerToBeDeleted = null;

        for (Card cardElement : accountListRepositoryImplementation.findAllCreditCardsByAccount(loggedAccount)) { // busca todos os cartões de crédito da conta logada
            if (cardElement.getCardHolder().getNif().equals(secondaryHolder.getNif())) { // Se o nif do dono do cartão for igual no NIF do titular que dever ser removido
                creditCardOwnedByCustomerToBeDeleted = cardElement;
            }
        }

        if (creditCardOwnedByCustomerToBeDeleted != null && creditCardOwnedByCustomerToBeDeleted.getPlafondBalance() < creditCardOwnedByCustomerToBeDeleted.getMonthyPlafond()) { //Se esse titular secundário tiver cartão de crédito e não tiver dívida no mesmo
            return false;
        }

        for (Card cardElement : accountListRepositoryImplementation.findAllDebitCardsByAccount(loggedAccount)) { // busca todos os cartões de débito da conta logada
            if (cardElement.getCardHolder().getNif().equals(secondaryHolder.getNif())) { // Se o nif do dono do cartão for igual no NIF do titular que dever ser removido
                debitCardOwnedByCustomerToBeDeleted = cardElement;
            }
        }

        loggedAccount.getSecondaryHolders().removeIf(customerElement -> customerElement.getNif().equals(secondaryHolder.getNif())); // Excluir o cliente secundário antes de verificar se ele existe em alguma outra conta
        loggedAccount.getCards().removeIf(cardElement -> cardElement.getCardHolder().getNif().equals(secondaryHolder.getNif())); // Excluir todos os cartões daquele cliente na conta logada

        accountListRepositoryImplementation.update(loggedAccount); // atualiza a situação dessa conta na base de dados

        if (debitCardOwnedByCustomerToBeDeleted != null) {
            cardServiceImplementation.delete(debitCardOwnedByCustomerToBeDeleted); // deleta o cartão de débito da base de dados
        }

        if (creditCardOwnedByCustomerToBeDeleted != null) {
            cardServiceImplementation.delete(creditCardOwnedByCustomerToBeDeleted); // deleta o cartão de crédito da base de dados
        }

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
        // Só remove da lista principal se não achar em alguma outra conta
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
    public void delete(Account accountToBeDeleted) {//TODO implement method
        /*
        1. Verificar se tem cartões de crédito
        2. Se tiver cartões de crédito, os mesmos não podem estar em dívida
        3. O saldo da conta tem de estar a 0
        4. Verificar se os clientes não tem outra conta
        4.1. Se não estiverem em outra conta, excluí-los da tabela de clientes (ver deleteSecondaryHolders)
        4.2. Se estiverem em outra conta, excluí-los somente na conta atual
         */
        if (accountToBeDeleted.getCards().stream().filter(cardElement -> cardElement.getMonthyPlafond() > 0. && cardElement.getPlafondBalance() < cardElement.getMonthyPlafond()).collect(Collectors.toCollection(ArrayList::new)).size() > 0) { // na conta, percorre a lista de cartões e busca todos os cartões de crédito que tenham dívida, caso exista algum
//            return ResponseType.THERE_ARE_DEBTS; // Há cartões de crédito em débito
        }
        if (accountToBeDeleted.getBalance() > 0.) { // Caso o saldo da conta não esteja zerado
//            return ResponseType.BALANCE_BIGGER_THAN_ZERO;
        }
        /*ArrayList<Customer> customersThatAreInAnotherAccount = accountListRepositoryImplementation.findAll().stream().filter(
                accountElement -> accountElement.getMainHolder().getNif().equals(accountToBeDeleted.getMainHolder().getNif()) // se o titular principal desta conta também é titular principal em outra
                        ||
                accountElement.getSecondaryHolders().stream().filter(customerElement1 -> customerElement1.getNif().equals(accountToBeDeleted.getSecondaryHolders().stream().filter(customerElement3 -> customerElement3.getNif().equals())) // se algum titular secundário da conta logada também é titular secundário em alguma outra conta
                        ||
                accountElement.getMainHolder().getNif().equals() // se o titular principal dessa conta for titular secundário em outra conta
                        ||
                accountElement.getSecondaryHolders().stream().filter(customerElement2 -> customerElement2.getNif().equals()) ));// se algum titular secundário da conta logada for titular principal de outra conta*/

        ArrayList<Customer> customersThatAreInAnotherAccount = new ArrayList<>();
        ArrayList<Account> allAccounts = accountListRepositoryImplementation.findAll();

        for (Account anotherAccount : allAccounts) {
            // se o titular principal desta conta também é titular principal em outra
            if (anotherAccount.getMainHolder().getNif().equals(accountToBeDeleted.getMainHolder().getNif())) {
                customersThatAreInAnotherAccount.add(accountToBeDeleted.getMainHolder());
            }

            for (Customer secondaryHolderInAnotherAccount : anotherAccount.getSecondaryHolders()) {
                // se algum titular secundário da conta logada for titular principal de outra conta
                if (secondaryHolderInAnotherAccount.getNif().equals(accountToBeDeleted.getMainHolder().getNif())) {
                    customersThatAreInAnotherAccount.add(accountToBeDeleted.getMainHolder());
                }

                for (Customer secondaryHolderInAccountToBeDeleted : accountToBeDeleted.getSecondaryHolders()) {
                    // se algum titular secundário da conta logada também é titular secundário em alguma outra conta
                    if (secondaryHolderInAnotherAccount.getNif().equals(secondaryHolderInAccountToBeDeleted.getNif())) {
                        customersThatAreInAnotherAccount.add(secondaryHolderInAccountToBeDeleted);
                    }

                    // se algum titular secundário da conta logada também é titular principal em alguma outra conta
                    if (secondaryHolderInAccountToBeDeleted.getNif().equals(anotherAccount.getMainHolder().getNif())) {
                        customersThatAreInAnotherAccount.add(secondaryHolderInAccountToBeDeleted);
                    }
                }
            }
        }
        System.out.println("Clientes da serem removidos:"); //TODO to be deleted
        customersThatAreInAnotherAccount.forEach(System.out::println); //TODO to be deleted

        accountListRepositoryImplementation.delete(accountToBeDeleted); //TODO uncomment this
    }
}