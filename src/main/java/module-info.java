module pt.drumond.rumosdigitalbank {
    requires javafx.controls;
    requires javafx.fxml;

    exports pt.drumond.rumosdigitalbank;
    opens pt.drumond.rumosdigitalbank to javafx.fxml;
}