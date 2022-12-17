package pt.drumond.rumosdigitalbank;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import pt.drumond.rumosdigitalbank.model.Account;
import pt.drumond.rumosdigitalbank.model.Card;
import pt.drumond.rumosdigitalbank.service.interfaces.AccountService;

public class MenuMainController {
    @FXML
    private AnchorPane anchorPaneMainMenu;

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
}
