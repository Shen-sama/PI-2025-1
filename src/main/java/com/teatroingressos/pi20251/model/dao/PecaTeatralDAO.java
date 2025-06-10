package com.teatroingressos.pi20251.model.dao;

import com.teatroingressos.pi20251.exception.EnderecoException;
import com.teatroingressos.pi20251.exception.PecaException;
import com.teatroingressos.pi20251.exception.PersistenciaException;
import com.teatroingressos.pi20251.model.domain.PecaTeatral;
import com.teatroingressos.pi20251.model.domain.Sessao;
import com.teatroingressos.pi20251.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PecaTeatralDAO {

    public int salvar(PecaTeatral pecaTeatral) throws PecaException {
        String sql = "INSERT INTO peca (nome, sinopse, duracao) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstancia().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pecaTeatral.getNome());
            stmt.setString(2, pecaTeatral.getSinopse());
            stmt.setString(3, pecaTeatral.getDuracao());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new PecaException("Erro ao salvar peça: " + e.getMessage());
        }

        return -1;
    }

    public List<PecaTeatral> buscarTodas() throws PersistenciaException {
        List<PecaTeatral> pecas = new ArrayList<>();

        String sqlPeca = "SELECT * FROM peca";
        String sqlSessao = "SELECT * FROM sessao WHERE IDPeca = ?";

        try (Connection conn = DatabaseConnection.getInstancia().getConexao();
             PreparedStatement stmtPeca = conn.prepareStatement(sqlPeca);
             ResultSet rsPeca = stmtPeca.executeQuery()) {

            while (rsPeca.next()) {
                PecaTeatral peca = new PecaTeatral();
                long idPeca = rsPeca.getLong("ID");

                peca.setId(idPeca);
                peca.setNome(rsPeca.getString("nome"));
                peca.setSinopse(rsPeca.getString("sinopse"));
                peca.setDuracao(rsPeca.getString("duracao"));

                try (PreparedStatement stmtSessao = conn.prepareStatement(sqlSessao)) {
                    stmtSessao.setLong(1, idPeca);
                    try (ResultSet rsSessao = stmtSessao.executeQuery()) {
                        while (rsSessao.next()) {
                            Sessao sessao = new Sessao();
                            sessao.setId(rsSessao.getLong("ID"));
                            sessao.setHorario(rsSessao.getString("horaInicio"));
                            sessao.setIdPeca(idPeca);
                            sessao.setDisponivel(rsSessao.getBoolean("disponivel"));

                            peca.adicionarSessao(sessao);
                        }
                    }
                }

                pecas.add(peca);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar peças teatrais: " + e.getMessage());
        }

        return pecas;
    }
}
