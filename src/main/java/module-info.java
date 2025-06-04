module com.teatroingressos.pi20251 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.teatroingressos.pi20251 to javafx.fxml;
    exports com.teatroingressos.pi20251;
}