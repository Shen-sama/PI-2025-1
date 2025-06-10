package com.teatroingressos.pi20251.app;

import com.teatroingressos.pi20251.model.domain.*;
import com.teatroingressos.pi20251.model.repository.ClienteRepository;
import com.teatroingressos.pi20251.model.repository.IngressoRepository;
import com.teatroingressos.pi20251.model.repository.PecaTeatralRepository;
import com.teatroingressos.pi20251.util.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class MainApp extends Application {

    private static Scene scene;

    private static ClienteRepository clienteRepository;
    private static PecaTeatralRepository pecasRepository;
    private static IngressoRepository ingressoRepository;

    public static Scene getScene() {
        return scene;
    }

    public static ClienteRepository getClienteRepository() {
        return clienteRepository;
    }

    public static PecaTeatralRepository getPecaTeatralRepository() {
        return pecasRepository;
    }

    public static IngressoRepository getIngressoRepository() {
        return ingressoRepository;
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/teatroingressos/pi20251/view/telaInicial.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();

        inicializarRepositorios();
    }

    private static void inicializarRepositorios() {
        clienteRepository = new ClienteRepository();
        clienteRepository.carregarClientesDoBanco();

        pecasRepository = new PecaTeatralRepository();
        pecasRepository.carregarPecasDoBanco();

        ingressoRepository = new IngressoRepository();
        ingressoRepository.carregarIngressosDoBanco();
    }

    public static void main(String[] args) {
        launch();
    }
}