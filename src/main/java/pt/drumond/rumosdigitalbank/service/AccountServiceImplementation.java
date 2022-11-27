package pt.drumond.rumosdigitalbank.service;

import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Customer;
import pt.drumond.rumosdigitalbank.model.MovementType;
import pt.drumond.rumosdigitalbank.repository.AccountListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.AccountRepository;
import pt.drumond.rumosdigitalbank.repository.CustomerListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.CustomerRepository;

/**
 * Contains all methods responsible for the businees rules related to accounts.
 */
public class AccountServiceImplementation implements AccountService {
    private CustomerService customerServiceImplementation = new CustomerServiceImplementation();
    private AccountRepository accountListRepositoryImplementation = new AccountListRepositoryImplementation();
    private CustomerRepository customerListRepositoryImplementation = new CustomerListRepositoryImplementation();
    private MovementService movementService = new MovementService();

    public AccountServiceImplementation() {
    }

    /**
     * Creates a new account.
     *
     * @return the new account created
     */
    @Override
    public Account create(Account account) {
        account.getMovements().add(movementService.createTransaction(account.getBalance(), MovementType.DEPOSIT));

        return accountListRepositoryImplementation.save(account);
    }

    /**
     * Allows to do several activities and transactions for the logged account.
     */
    @Override
    public Account update(Account account) {

        return accountListRepositoryImplementation.save(account);
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
        customerListRepositoryImplementation.save(secondaryHolder);
        loggedAccount.getSecondaryHolders().add(secondaryHolder);
        accountListRepositoryImplementation.update(loggedAccount);
    }

    @Override
    public void deposit(Account loggedAccount, double value) {
        //TODO implement method
    }

    @Override
    public void transfer(Account loggedAccount, double value, String destinyAccountCode) {
        //TODO implement method
    }

    @Override
    public void payLoan(Account loggedAccount, double value, String creditCardSerialNumber) {
        //TODO implement method
    }

    @Override
    public void deleteSecondaryHolder(Account loggedAccount, Customer secondaryHolder) {
        //TODO implement method
    }

    @Override
    public void addDebitCard(Account loggedAccount, Customer cardHolder) {
        //TODO implement method
    }

    @Override
    public void addCreditCard(Account loggedAccount, Customer cardHolder) {
        //TODO implement method
    }

    @Override
    public void loadDatabase() {
        accountListRepositoryImplementation.loadDatabase();
    }
}