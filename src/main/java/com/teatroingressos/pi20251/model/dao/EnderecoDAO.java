package com.teatroingressos.pi20251.model.dao;

import com.teatroingressos.pi20251.exception.EnderecoException;
import com.teatroingressos.pi20251.model.domain.Endereco;
import com.teatroingressos.pi20251.util.DatabaseConnection;

import java.sql.*;

public class EnderecoDAO {

    public int salvar(Endereco endereco) throws EnderecoException {
        String sql = "INSERT INTO endereco (rua, numero, complemento, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstancia().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getComplemento());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEstado());
            stmt.setString(7, endereco.getCEP());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new EnderecoException("Erro ao salvar endereço: " + e.getMessage());
        }

        return -1;
    }
}
