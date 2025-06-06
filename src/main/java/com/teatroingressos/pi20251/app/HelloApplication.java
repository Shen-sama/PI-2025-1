package com.teatroingressos.pi20251.app;

import com.teatroingressos.pi20251.model.domain.Area;
import com.teatroingressos.pi20251.model.domain.Sala;
import com.teatroingressos.pi20251.model.domain.SalaDirector;
import com.teatroingressos.pi20251.util.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/teatroingressos/pi20251/view/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        DatabaseConnection db = DatabaseConnection.getInstancia();
        Connection conn = db.getConexao();

        if (conn != null && !conn.isClosed()) {
            System.out.println("✅ Conexão com o banco de dados estabelecida com sucesso!");
        } else {
            System.out.println("❌ Falha na conexão com o banco de dados.");
        }

        /*SalaDirector salaDirector = new SalaDirector();
        Sala sala = salaDirector.criarSalaCompleta();

        for (Area area : sala.getAreas()) {
            System.out.println("Setor: " + area.getTipo() + " - Preço: R$" + area.getPreco());
            System.out.println("Qtd Poltronas: " + area.getPoltronas().size());
        }*/
    }

    public static void main(String[] args) {
        launch();
    }
}