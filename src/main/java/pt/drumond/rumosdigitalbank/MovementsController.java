package pt.drumond.rumosdigitalbank;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.drumond.rumosdigitalbank.enums.MovementType;
import pt.drumond.rumosdigitalbank.enums.ResponseType;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.model.Movement;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;
import pt.drumond.rumosdigitalbank.service.interfaces.MovementService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MovementsController {
    public ObservableList<Movement> movements;
    @FXML
    private TableView<Movement> tableViewMovements;
    @FXML
    private TableColumn<Movement, LocalDate> tableColumnDate;
    @FXML
    private TableColumn<Movement, ResponseType> tableColumnType;
    @FXML
    private TableColumn<Movement, Double> tableColumnValue;
    private AccountService accountServiceImplementation = Main.getBank().getAccountServiceImplementation();
    private MovementService movementServiceImplementation = Main.getBank().getMovementServiceImplementation();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Card loggedCard;
    private Account loggedAccount;
    private ArrayList<Movement> allMovements;

    public void setLoggedCard(Card loggedCard) {
        this.loggedCard = loggedCard;
    }

    public void setLoggedAccount(Account loggedAccount) {
        this.loggedAccount = loggedAccount;
    }

    @FXML
    protected void showOnlyDeposits() {
        ArrayList<Movement> depositsArrayList = getMovements().stream().filter(movementElement -> movementElement.getType().equals(MovementType.DEPOSIT)).collect(Collectors.toCollection(ArrayList::new));

        ObservableList<Movement> depositsObservableList = FXCollections.observableList(depositsArrayList);

        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableViewMovements.setItems(depositsObservableList);
    }

    @FXML
    protected void showOnlyTransfers() {
        ArrayList<Movement> depositsArrayList = getMovements().stream().filter(movementElement -> movementElement.getType().equals(MovementType.TRANSFER_IN) || movementElement.getType().equals(MovementType.TRANSFER_OUT)).collect(Collectors.toCollection(ArrayList::new));

        ObservableList<Movement> depositsObservableList = FXCollections.observableList(depositsArrayList);

        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableViewMovements.setItems(depositsObservableList);
    }
    @FXML
    protected void showOnlyWithdraws() {
        ArrayList<Movement> depositsArrayList = getMovements().stream().filter(movementElement -> movementElement.getType().equals(MovementType.WITHDRAW)).collect(Collectors.toCollection(ArrayList::new));

        ObservableList<Movement> depositsObservableList = FXCollections.observableList(depositsArrayList);

        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableViewMovements.setItems(depositsObservableList);
    }

    @FXML
    protected void showAll() {
        ObservableList<Movement> movementObservableList = getMovements();
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableViewMovements.setItems(movementObservableList);
    }

    private ObservableList<Movement> getMovements() {
        allMovements = loggedAccount.getMovements();

        if (allMovements != null) {
            movements = FXCollections.observableList(allMovements);
        }
        return movements;
    }

    @FXML
    protected void back(ActionEvent actionEvent) throws IOException {
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
        } else {
            menuMainController.setLabelMonthlyPlafond(); // mostra o limite mensal do cartão
            menuMainController.setLabelPlafondBalance(); // mostra o saldo disponível do limite do cartão
        }
        menuMainController.setLoggedAccount();
        menuMainController.setWelcome();
        menuMainController.setAccountBalance();

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}