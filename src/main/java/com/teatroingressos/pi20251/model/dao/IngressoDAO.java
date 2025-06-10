package com.teatroingressos.pi20251.model.dao;

import com.teatroingressos.pi20251.exception.IngressoException;
import com.teatroingressos.pi20251.exception.PersistenciaException;
import com.teatroingressos.pi20251.model.domain.Ingresso;
import com.teatroingressos.pi20251.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngressoDAO {

    public int salvar(Ingresso ingresso, int idSessao, int idCliente) throws IngressoException {
        String sql = "INSERT INTO ingresso (dataCompra, areaPoltrona, poltrona, cpfComprador, idSessao, idCliente) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstancia().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(ingresso.getDataCompra()));
            stmt.setString(2, ingresso.getAreaPoltrona());
            stmt.setString(3, ingresso.getCodPoltrona());
            stmt.setString(4, ingresso.getCpf());
            stmt.setInt(5, idSessao);
            stmt.setInt(6, idCliente);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new IngressoException("Erro ao salvar ingresso: " + e.getMessage());
        }

        return -1;

    }

    public List<Ingresso> buscarTodosIngressos()  throws PersistenciaException {
        List<Ingresso> ingressos = new ArrayList<>();

        String sql = """
            SELECT
                i.ID AS ingresso_id, i.dataCompra, i.areaPoltrona, i.poltrona, i.cpfComprador, i.IDSessao, i.IDCliente
            FROM ingresso i
        """;

        try (Connection conexao = DatabaseConnection.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ingresso ingresso = new Ingresso();
                ingresso.setId(rs.getInt("ingresso_ID"));
                ingresso.setDataCompra(rs.getDate("dataCompra").toLocalDate());
                ingresso.setAreaPoltrona(rs.getString("areaPoltrona"));
                ingresso.setCodPoltrona(rs.getString("poltrona"));
                ingresso.setCpf(rs.getString("cpfComprador"));
                ingresso.setIdSessao(rs.getInt("IDSessao"));

                if (rs.getInt("IDCliente") > 0) {
                    ingresso.setIdCliente(rs.getInt("IDCliente"));
                }

                ingressos.add(ingresso);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar ingressos no banco.", e);
        }

        return ingressos;
    }
}
