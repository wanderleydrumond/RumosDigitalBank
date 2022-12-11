package pt.drumond.rumosdigitalbank.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.Main;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;

import java.io.IOException;

public class LoginController {
    @FXML
    public TextField textFieldCardSerialnumber;
    @FXML
    public PasswordField passwordFieldCardPin;
    @FXML
    public Button buttonLogin, buttonCancel;
    @FXML
    private AnchorPane anchorPaneWelcome;
    private Stage stage;
//    private CardService cardServiceImplementation = new CardServiceImplementation();
    private CardService cardServiceImplementation;

    public void setStage(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        this.stage.setTitle("Rumos Digital Bank ATM");
        this.stage.setResizable(false);
        this.stage.setScene(scene);
        this.stage.show();
    }

    @FXML
    protected void logout(ActionEvent actionEvent) {
        getStageInstance();
        System.out.println("Logged out"); //TODO to be deleted
        System.out.println(actionEvent.getEventType()); // TODO to be deleted
        stage.close();
    }

    public void login(ActionEvent actionEvent) throws IOException { //TODO remove unused argument
        Card card = cardServiceImplementation.findBySerialNumber(textFieldCardSerialnumber.getText());

        if (card.getPin().equals(passwordFieldCardPin.getText())) {
            if (card.isVirgin()) {
                new UpdatePinController().setStage();
            } else {
                new MenuMainController().setStage();
            }
            getStageInstance();
            stage.close();
        } else {
            System.out.println("Invalid PIN");
        }
    }

    private void getStageInstance() { //TODO este método deve ser implementado em um único lugar
        stage = (Stage) anchorPaneWelcome.getScene().getWindow();
    }
}