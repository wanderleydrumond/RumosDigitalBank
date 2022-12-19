package pt.drumond.rumosdigitalbank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;

import java.io.IOException;
import java.text.DecimalFormat;

import static java.util.Objects.requireNonNull;

public class MenuMainController {
    @FXML
    private Button buttonMakeLoan, buttonPayLoan;
    @FXML
    private Label labelWelcome, labelBalance;
    @FXML
    private AnchorPane anchorPaneMainMenu;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Account loggedAccount;
    private Card loggedCard;
    private AccountService accountServiceImplementation;

    public MenuMainController() {
        accountServiceImplementation = Main.getBank().getAccountServiceImplementation();
    }

    public Button getButtonMakeLoan() {
        return buttonMakeLoan;
    }

    public Button getButtonPayLoan() {
        return buttonPayLoan;
    }

    public void setLoggedCard(Card loggedCard) {
        this.loggedCard = loggedCard;
    }

    public void setLoggedAccount() {
        this.loggedAccount = accountServiceImplementation.getAccountByCardSerialNumber(loggedCard.getSerialNumber());
    }

    public void setWelcome() {
        labelWelcome.setText("Welcome " + loggedCard.getCardHolder().getName());
    }

    public void setBalance() {
        labelBalance.setText("Balance: " + new DecimalFormat("0.00").format(loggedAccount.getBalance()) + "€");
    }

    @FXML
    protected void deposit(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("deposit-view.fxml"));
        root = fxmlLoader.load();

        // Aqui eu passo as informações de conta e cartão, existentes em MenuMainController e passando-as para DepositController
        DepositController depositController = fxmlLoader.getController();
        depositController.setLoggedCard(loggedCard);
        depositController.setLoggedAccount(loggedAccount);

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void logout(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(requireNonNull(getClass().getResource("login-view.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Rumos Digital Bank ATM");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}