module com.example.avengersatm {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.avengersatm to javafx.fxml;
    exports com.example.avengersatm;
}