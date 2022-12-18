package pt.drumond.rumosdigitalbank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class MenuMainController {
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

    public void setLoggedCard(Card loggedCard) {
        this.loggedCard = loggedCard;
    }

    public void setLoggedAccount() {
        this.loggedAccount = accountServiceImplementation.getAccountByCardSerialNumber(loggedCard.getSerialNumber());;
    }

    public void setWelcome() {
        labelWelcome.setText("Welcome " + loggedCard.getCardHolder().getName());
    }

    public void setBalance() {
        labelBalance.setText("Balance: " + loggedAccount.getBalance() + "€");
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
