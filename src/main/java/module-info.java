module com.teatroingressos.pi20251 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;


    opens com.teatroingressos.pi20251.view to javafx.fxml;
    exports com.teatroingressos.pi20251.app;
    opens com.teatroingressos.pi20251.app to javafx.fxml;
    exports com.teatroingressos.pi20251.controller;
    opens com.teatroingressos.pi20251.controller to javafx.fxml;
    opens com.teatroingressos.pi20251.model.domain to javafx.base;
}