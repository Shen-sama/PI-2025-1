package com.teatroingressos.pi20251.model.dao;


import com.teatroingressos.pi20251.exception.SessaoException;
import com.teatroingressos.pi20251.model.domain.Sessao;
import com.teatroingressos.pi20251.util.DatabaseConnection;

import java.sql.*;

public class SessaoDAO {

    public int salvar(Sessao sessao, int idPeca) throws SessaoException {
        String sql = "INSERT INTO sessao (horaInicio, disponivel, IDPeca) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstancia().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sessao.getHorario());
            stmt.setBoolean(2, sessao.isDisponivel());
            stmt.setInt(3, idPeca);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new SessaoException("Erro ao salvar sessão: " + e.getMessage());
        }

        return -1;
    }

    public void atualizarDisponibilidade(String nomePeca, String horario, boolean disponivel) throws SessaoException {
        String sql = """
        UPDATE sessao
        SET disponivel = ?
        WHERE IDPeca = (SELECT ID FROM peca WHERE nome = ?)
        AND horaInicio = ?
    """;

        try (Connection conn = DatabaseConnection.getInstancia().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, disponivel);
            stmt.setString(2, nomePeca);
            stmt.setString(3, horario);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SessaoException("Nenhuma sessão encontrada para atualizar.");
            }

        } catch (SQLException e) {
            throw new SessaoException("Erro ao atualizar disponibilidade: " + e.getMessage());
        }
    }
}
