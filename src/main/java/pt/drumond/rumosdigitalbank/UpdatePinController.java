package pt.drumond.rumosdigitalbank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.service.interfaces.CardService;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class UpdatePinController {
    @FXML
    private AnchorPane anchorPaneUpdatePin;
    @FXML
    private Label labelUpdatePin, labelInsertPin, labelConfirmPin;
    @FXML
    private PasswordField textFieldNewPin, textFieldConfirmPin;
    @FXML
    private Button buttonCancel, buttonConfirm;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private CardService cardServiceImplementation;

    public UpdatePinController() {
        cardServiceImplementation = Main.getBank().getCardServiceImplementation();
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
    protected void confirm(ActionEvent actionEvent) {
        if (Boolean.FALSE.equals(textFieldNewPin.getText().equals(textFieldConfirmPin.getText()))) {
            //TODO trocar o PIN do cartão
        }
    }
}