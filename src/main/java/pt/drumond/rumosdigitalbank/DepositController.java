package pt.drumond.rumosdigitalbank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;

import java.io.IOException;

public class DepositController {
    @FXML
    private TextField textFieldValue;
    private AccountService accountServiceImplementation = Main.getBank().getAccountServiceImplementation();

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Card loggedCard;
    private Account loggedAccount;

    public void setLoggedCard(Card loggedCard) {
        this.loggedCard = loggedCard;
    }

    public void setLoggedAccount(Account loggedAccount) {
        this.loggedAccount = loggedAccount;
    }

    @FXML
    protected void deposit(ActionEvent actionEvent) throws IOException {
        boolean answer = accountServiceImplementation.deposit(loggedAccount, Double.parseDouble(textFieldValue.getText()), MovementType.DEPOSIT);
        Alert alert;
        if (answer) {
            // Alert sucesso
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deposit ATM");
            alert.setHeaderText(null);
            alert.setContentText("Deposit successfully perfomed");

        } else {
            //Alert erro
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Deposit ATM");
            alert.setHeaderText("Error:");
            alert.setContentText("Deposit not perfomed");
        }
        loadMainScreen(actionEvent);

        alert.showAndWait();
    }

    @FXML
    protected void cancel(ActionEvent actionEvent) throws IOException {
        loadMainScreen(actionEvent);
    }

    private void loadMainScreen(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu-main-view.fxml"));
        root = fxmlLoader.load();

        MenuMainController menuMainController = fxmlLoader.getController();
        menuMainController.setLoggedCard(loggedCard);
        if (loggedCard.getMonthyPlafond() == 0.) { // Verifica se o cartão é de débito, se for
            menuMainController.getButtonMakeLoan().setVisible(false); // esconde o botão fazer empréstimo
            menuMainController.getButtonPayLoan().setVisible(false); // esconde o botão pagar empréstimo
        }
        menuMainController.setLoggedAccount();
        menuMainController.setWelcome();
        menuMainController.setBalance();

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void formatTextFieldToNumbersOnly() {
        textFieldValue.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textFieldValue.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }
}