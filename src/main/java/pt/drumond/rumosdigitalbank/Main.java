package pt.drumond.rumosdigitalbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.controller.Bank;
import pt.drumond.rumosdigitalbank.repository.implementations.jdbc.AccountJDBCRepositoryImplemention;
import pt.drumond.rumosdigitalbank.repository.implementations.jdbc.CustomerJDBCRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.implementations.jdbc.MovementJDBCRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.implementations.list.CardListRepositoryImplementation;
import pt.drumond.rumosdigitalbank.repository.interfaces.AccountRepository;
import pt.drumond.rumosdigitalbank.repository.interfaces.CardRepository;
import pt.drumond.rumosdigitalbank.repository.interfaces.CustomerRepository;
import pt.drumond.rumosdigitalbank.repository.interfaces.MovementRepository;
import pt.drumond.rumosdigitalbank.service.implementations.jdbc.AccountJDBCServiceImplementation;
import pt.drumond.rumosdigitalbank.service.implementations.jdbc.CustomerJDBCServiceImplementation;
import pt.drumond.rumosdigitalbank.service.implementations.jdbc.MovementJDBCServiceImplementation;
import pt.drumond.rumosdigitalbank.service.implementations.list.CardListServiceImplementation;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;
import pt.drumond.rumosdigitalbank.service.interfaces.CustomerService;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.io.IOException;
import java.sql.SQLException;

import static java.util.Objects.requireNonNull;

public class Main extends Application {
    private static Bank bank;

    public static Bank getBank() {
        return bank;
    }

    public static void main(String[] args) throws SQLException {
        CustomerRepository customerRepositoryImplementation = new CustomerJDBCRepositoryImplementation(); // utilizando JDBC
        CustomerService customerServiceImplementation = new CustomerJDBCServiceImplementation(customerRepositoryImplementation);
        CardRepository cardRepositoryImplementation = new CardListRepositoryImplementation();
        CardService cardServiceImplementation = new CardListServiceImplementation(cardRepositoryImplementation);
        MovementRepository movementRepositoryImplementation = new MovementJDBCRepositoryImplementation(); // utilizando JDBC
        MovementService movementServiceImplementation = new MovementJDBCServiceImplementation(movementRepositoryImplementation); // utilizando JDBC
        AccountRepository accountRepositoryImplementation = new AccountJDBCRepositoryImplemention(); // utilizando JDBC
        AccountService accountServiceImplementation = new AccountJDBCServiceImplementation(customerServiceImplementation, movementServiceImplementation, cardServiceImplementation, accountRepositoryImplementation);

        bank = new Bank(customerServiceImplementation, cardServiceImplementation, movementServiceImplementation, accountServiceImplementation);
        bank.initialMenu();
    }

    public void startSelectedApp(int option) throws SQLException {
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
        stage.getIcons().add(new Image(requireNonNull(getClass().getResourceAsStream("images/logo.png"))));
        stage.setTitle("Rumos Digital Bank ATM");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        /*stage.setOnCloseRequest(windowEvent -> {
            stage.close();
            bank.initialMenu(); // https://stackoverflow.com/questions/24320014/how-to-call-launch-more-than-once-in-java
        });*/
    }
}