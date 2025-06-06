package com.teatroingressos.pi20251.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instancia;
    private Connection conexao;

    private DatabaseConnection() throws SQLException  {
        String url = "jdbc:MySQL://localhost:3306/pi2025_1";
        String usuario = "root";
        String senha = "123456";

        conexao = null;
        conexao = DriverManager.getConnection(url, usuario, senha);
    }

    public static DatabaseConnection getInstancia() throws SQLException  {
        if (instancia == null) {
            instancia = new DatabaseConnection();
        }
        return instancia;
    }

    public Connection getConexao() {
        return conexao;
    }
}
