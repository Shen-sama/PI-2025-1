package com.teatroingressos.pi20251.controller;

import com.teatroingressos.pi20251.app.MainApp;
import com.teatroingressos.pi20251.util.SceneSwap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class TelaInicialController {

    @FXML
    private Button btnAdministrador;

    @FXML
    private Button btnAtendimento;

    @FXML
    void iniciarAdministrador(ActionEvent event) throws IOException {
        SceneSwap.setRoot(MainApp.getScene(), "");
    }

    @FXML
    void iniciarAtendimento(ActionEvent event) throws IOException {
        SceneSwap.setRoot(MainApp.getScene(), "");
    }
}
