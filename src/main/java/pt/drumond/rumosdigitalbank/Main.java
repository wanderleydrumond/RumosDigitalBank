package pt.drumond.rumosdigitalbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.controller.Bank;
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

import static java.util.Objects.requireNonNull;

public class Main extends Application {
    private static Bank bank;

    public static Bank getBank() {
        return bank;
    }

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
        Parent root = FXMLLoader.load(requireNonNull(getClass().getResource("login-view.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Rumos Digital Bank ATM");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}