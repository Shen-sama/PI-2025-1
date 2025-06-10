package com.teatroingressos.pi20251.controller;

import com.teatroingressos.pi20251.app.MainApp;
import com.teatroingressos.pi20251.util.SceneSwap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class TelaInicialController {

    @FXML
    void iniciarAdministrador(ActionEvent event) throws IOException {
        SceneSwap.setRoot(MainApp.getScene(), "telaMenuAdministrador");
    }

    @FXML
    void iniciarAtendimento(ActionEvent event) throws IOException {
        SceneSwap.setRoot(MainApp.getScene(), "telaCompra");
    }
}
