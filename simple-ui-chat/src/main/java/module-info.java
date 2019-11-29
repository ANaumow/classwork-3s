module company {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens company to javafx.fxml;
    exports company;

}