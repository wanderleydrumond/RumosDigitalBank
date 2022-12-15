package pt.drumond.rumosdigitalbank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;
import pt.drumond.rumosdigitalbank.service.interfaces.CustomerService;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.io.IOException;
import java.net.URL;

import static java.util.Objects.requireNonNull;

public class LoginController {
    @FXML
    private AnchorPane anchorPaneWelcome;
    @FXML
    private TextField textFieldCardSerialnumber;
    @FXML
    private PasswordField passwordFieldCardPin;
    @FXML
    private Button buttonCancel, buttonLogin;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private CustomerService customerServiceImplementation;
    private CardService cardServiceImplementation;
    private MovementService movementServiceImplementation;
    private AccountService accountServiceImplementation;

    public LoginController() {
        customerServiceImplementation = Main.getBank().getCustomerServiceImplementation();
        cardServiceImplementation = Main.getBank().getCardServiceImplementation();
        movementServiceImplementation = Main.getBank().getMovementServiceImplementation();
        accountServiceImplementation = Main.getBank().getAccountServiceImplementation();
    }

    @FXML
    protected void login(ActionEvent actionEvent) throws IOException {
        Card card = cardServiceImplementation.findBySerialNumber(textFieldCardSerialnumber.getText());
        if (card == null) {
            System.out.println("Serial number inválido"); // TODO trocar este print por algo gráfico
        } else {
            if (card.getPin().equals(passwordFieldCardPin.getText())) { // confere se este serial number existe
                URL url;
                if (card.isVirgin()) {
                    url = getClass().getResource("update-pin-view.fxml");
                } else {
                    url = getClass().getResource("menu-main-view.fxml");
                }
                root = FXMLLoader.load(requireNonNull(url));
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                textFieldCardSerialnumber.setText(""); // limpa o campo do serial number
                passwordFieldCardPin.setText(""); // limpa o campo do PIN
                System.out.println("PIN inválido"); // TODO trocar este print por algo gráfico
            }
        }
    }
}