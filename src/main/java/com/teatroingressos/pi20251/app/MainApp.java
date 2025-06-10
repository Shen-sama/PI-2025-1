package com.teatroingressos.pi20251.app;

import com.teatroingressos.pi20251.model.domain.Cliente;
import com.teatroingressos.pi20251.model.domain.Sala;
import com.teatroingressos.pi20251.model.domain.SalaDirector;
import com.teatroingressos.pi20251.model.repository.ClienteRepository;
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

    public static Scene getScene() {
        return scene;
    }

    public static ClienteRepository getClienteRepository() {
        return clienteRepository;
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/com/teatroingressos/pi20251/view/telaInicial.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();

        inicializarSala();
        inicializarRepositorios();
    }

    private static void inicializarSala() {
        SalaDirector salaDirector = new SalaDirector();
        Sala sala = salaDirector.criarSalaCompleta();
    }

    private static void inicializarRepositorios() {
        clienteRepository = new ClienteRepository();
        clienteRepository.carregarClientesDoBanco();
    }

    public static void main(String[] args) {
        launch();
    }
}