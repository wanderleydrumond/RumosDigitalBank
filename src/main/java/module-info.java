module pt.drumond.rumosdigitalbank {
    requires javafx.controls;
    requires javafx.fxml;


    opens pt.drumond.rumosdigitalbank to javafx.fxml;
    exports pt.drumond.rumosdigitalbank;
}