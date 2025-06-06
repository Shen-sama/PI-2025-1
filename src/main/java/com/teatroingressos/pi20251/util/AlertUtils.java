package com.teatroingressos.pi20251.util;

import javafx.scene.control.Alert;

public class AlertUtils {

    public static void mostrarErro(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static void mostrarInfo(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informação");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static void mostrarSucesso(String mensagem) {
        mostrarInfo("Sucesso", mensagem);
    }

}
