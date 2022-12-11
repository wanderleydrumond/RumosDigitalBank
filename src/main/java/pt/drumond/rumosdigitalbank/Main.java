package pt.drumond.rumosdigitalbank;

import javafx.application.Application;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.controller.Bank;
import pt.drumond.rumosdigitalbank.javafx.LoginController;
import pt.drumond.rumosdigitalbank.repository.implementations.AccountListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.implementations.CardListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.implementations.CustomerListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.implementations.MovimentListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.interfaces.AccountRepository;
import pt.drumond.rumosdigitalbank.repository.interfaces.CardRepository;
import pt.drumond.rumosdigitalbank.repository.interfaces.CustomerRepository;
import pt.drumond.rumosdigitalbank.repository.interfaces.MovementListRepository;
import pt.drumond.rumosdigitalbank.service.implementations.AccountServiceImplementation;
import pt.drumond.rumosdigitalbank.service.implementations.CardServiceImplementation;
import pt.drumond.rumosdigitalbank.service.implementations.CustomerServiceImplementation;
import pt.drumond.rumosdigitalbank.service.implementations.MovimentServiceImplementation;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;
import pt.drumond.rumosdigitalbank.service.interfaces.CustomerService;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.io.IOException;

public class Main extends Application {

//    private CustomerRepository customerListRepositoryImplementation = new CustomerListRepositoryImplementation();
    /**
     * Object used to give access to methods from service layer from customer.
     */
//    private CustomerService customerServiceImplementation = new CustomerServiceImplementation(customerListRepositoryImplementation);

    //    private CustomerService customerServiceImplementation = new CustomerServiceImplementation(new CustomerListRepositoryImplementation()); TODO ver essa implementação
//    private CardRepository cardRepositoryImplementation = new CardListRepositoryImplementation();
//    private CardService cardServiceImplementation = new CardServiceImplementation(cardRepositoryImplementation);
//    private MovementListRepository movementListRepositoryImplementation = new MovimentListRepositoryImplementation();
//    private MovementService movementServiceImplementation = new MovimentServiceImplementation(movementListRepositoryImplementation);

//    private AccountRepository accountListRepositoryImplementation = new AccountListRepositoryImplementation();
    /**
     * Object used to give access to methods from service layer from account.
     */
//    private AccountService accountServiceImplementation = new AccountServiceImplementation(customerServiceImplementation, movementServiceImplementation, cardServiceImplementation, accountListRepositoryImplementation);
    static Bank bank;

    public static void main(String[] args) {
        CustomerRepository customerListRepositoryImplementation = new CustomerListRepositoryImplementation();
        CustomerService customerServiceImplementation = new CustomerServiceImplementation(customerListRepositoryImplementation);
        CardRepository cardRepositoryImplementation = new CardListRepositoryImplementation();
        CardService cardServiceImplementation = new CardServiceImplementation(cardRepositoryImplementation);
        MovementListRepository movementListRepositoryImplementation = new MovimentListRepositoryImplementation();
        MovementService movementServiceImplementation = new MovimentServiceImplementation(movementListRepositoryImplementation);
        AccountRepository accountListRepositoryImplementation = new AccountListRepositoryImplementation();
        AccountService accountServiceImplementation = new AccountServiceImplementation(customerServiceImplementation, movementServiceImplementation, cardServiceImplementation, accountListRepositoryImplementation);

        bank = new Bank(customerServiceImplementation, cardServiceImplementation, movementServiceImplementation, accountServiceImplementation);
        bank.initialMenu();
    }

    public void startSelectedApp(int option) {
        switch (option) {
            case 1 -> launch();
            case 2 -> bank.startAppManagement();
            default -> System.exit(0);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rumos Digital Bank ATM");
        stage.setScene(scene);
        stage.show();*/
        new LoginController().setStage(stage);
    }
}