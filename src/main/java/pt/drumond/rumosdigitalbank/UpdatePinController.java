package pt.drumond.rumosdigitalbank;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.util.Duration;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class UpdatePinController {
    @FXML
    private Label labelErrorMessage;
    @FXML
    private PasswordField passwordFieldNewPin, passwordFieldConfirmPin;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Card loggedCard;


    private CardService cardServiceImplementation;

    public UpdatePinController() {
        cardServiceImplementation = Main.getBank().getCardServiceImplementation();
    }

    public void setLoggedCard(Card loggedCard) {
        this.loggedCard = loggedCard;
    }
    @FXML
    protected void cancel(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(requireNonNull(getClass().getResource("login-view.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Rumos Digital Bank ATM");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void confirm(ActionEvent actionEvent) throws IOException {
        if (passwordFieldNewPin.getText().equals(passwordFieldConfirmPin.getText())) {
            Card loggedCard = cardServiceImplementation.update(passwordFieldConfirmPin.getText(), this.loggedCard); // troca o PIN do cartão

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
        } else {
            passwordFieldNewPin.setText("");
            passwordFieldConfirmPin.setText("");
            labelErrorMessage.setText("PIN numbers do not match");
            passwordFieldNewPin.getStyleClass().add("error");
            passwordFieldConfirmPin.getStyleClass().add("error");
            new Timeline(new KeyFrame(Duration.millis(2000), actionEventElement -> {
                labelErrorMessage.setText("");
                passwordFieldNewPin.getStyleClass().remove("error");
                passwordFieldConfirmPin.getStyleClass().remove("error");
            })).play(); // limpa a mensagem após dois segundos
        }
    }
}