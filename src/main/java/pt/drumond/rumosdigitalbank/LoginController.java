package pt.drumond.rumosdigitalbank;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;

import java.io.IOException;
import java.net.URL;

public class LoginController {
    @FXML
    private AnchorPane anchorPaneWelcome;
    @FXML
    private TextField textFieldCardSerialnumber;
    @FXML
    private PasswordField passwordFieldCardPin;
    @FXML
    private Button buttonCancel, buttonLogin;
    @FXML
    private Label labelErrorMessage;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private CardService cardServiceImplementation;


    public LoginController() {
        cardServiceImplementation = Main.getBank().getCardServiceImplementation();
    }

    @FXML
    protected void login(ActionEvent actionEvent) throws IOException {
        Card card = cardServiceImplementation.findBySerialNumber(textFieldCardSerialnumber.getText());
        if (card == null) {
            textFieldCardSerialnumber.setText(""); // limpa o campo do serial number
            passwordFieldCardPin.setText(""); // limpa o campo do PIN
            labelErrorMessage.setLayoutX(124);
            labelErrorMessage.setText("Invalid Serial Number"); // Exibe mensagem de erro
            textFieldCardSerialnumber.getStyleClass().add("error"); // adiciona a classe CSS error NÃO FUNCIONA!
            new Timeline(new KeyFrame(Duration.millis(2000), actionEventElement -> {
                labelErrorMessage.setText("");
                textFieldCardSerialnumber.getStyleClass().remove("error"); // remove a classe CSS error NÃO FUNCIONA!
            })).play(); // limpa a mensagem após dois segundos
        } else {
            if (card.getPin().equals(passwordFieldCardPin.getText())) { // confere se este serial number existe
                URL url;
                if (card.isVirgin()) {
                    url = getClass().getResource("update-pin-view.fxml");
                    // Carrega a informações para abrir uma nova tela
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    root = fxmlLoader.load();

                    // Pegando todas as informações que eu preciso passar para a página que será carregada
                    UpdatePinController updatePinController = fxmlLoader.getController();
                    updatePinController.setLoggedCard(card);
                } else {
                    url = getClass().getResource("menu-main-view.fxml");
                    // Carrega a informações para abrir uma nova tela
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    root = fxmlLoader.load();

                    // Pegando todas as informações que eu preciso passar para a página que será carregada
                    MenuMainController menuMainController = fxmlLoader.getController();
                    menuMainController.setLoggedCard(card);
                    menuMainController.setWelcome();
                }


                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                textFieldCardSerialnumber.setText(""); // limpa o campo do serial number
                passwordFieldCardPin.setText(""); // limpa o campo do PIN
                labelErrorMessage.setLayoutX(152);
                labelErrorMessage.setText("Invalid PIN"); // Exibe mensagem de erro
                passwordFieldCardPin.getStyleClass().add("error"); // adiciona a classe error CSS NÃO FUNCIONA!
                new Timeline(new KeyFrame(Duration.millis(2000), actionEventElement -> {
                    labelErrorMessage.setText("");
                    passwordFieldCardPin.getStyleClass().remove("error"); // remove a classe error CSS NÃO FUNCIONA!
                })).play(); // limpa a mensagem após dois segundos
            }
        }
    }
}