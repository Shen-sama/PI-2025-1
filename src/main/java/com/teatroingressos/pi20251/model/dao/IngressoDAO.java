package com.teatroingressos.pi20251.model.dao;

import com.teatroingressos.pi20251.exception.IngressoException;
import com.teatroingressos.pi20251.exception.PersistenciaException;
import com.teatroingressos.pi20251.model.domain.Ingresso;
import com.teatroingressos.pi20251.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngressoDAO {

    public int salvar(Ingresso ingresso) throws PersistenciaException {
        String sql = """
        INSERT INTO ingresso (dataCompra, poltrona, areaPoltrona, cpfComprador, IDCliente, IDSessao, preco)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection conexao = DatabaseConnection.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(ingresso.getDataCompra()));
            stmt.setString(2, ingresso.getCodPoltrona());
            stmt.setString(3, ingresso.getAreaPoltrona());
            stmt.setString(4, ingresso.getCpf());

            if (ingresso.getIdCliente() > 0) {
                stmt.setLong(5, ingresso.getIdCliente());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setLong(6, ingresso.getIdSessao());
            stmt.setBigDecimal(7, BigDecimal.valueOf(ingresso.getPreco()));

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new PersistenciaException("Falha ao inserir o ingresso, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new PersistenciaException("Falha ao obter o ID gerado do ingresso.");
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao salvar ingresso no banco de dados.", e);
        }

    }

    public List<Ingresso> buscarTodosIngressos()  throws PersistenciaException {
        List<Ingresso> ingressos = new ArrayList<>();

        String sql = """
            SELECT
                i.ID AS ingresso_id, i.dataCompra, i.areaPoltrona, i.poltrona, i.cpfComprador, i.IDSessao, i.IDCliente, i.preco,
                s.horaInicio, p.nome AS nomePeca
            FROM ingresso i
            JOIN sessao s ON i.IDSessao = s.ID
            JOIN peca p ON s.IDPeca = p.ID
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

                ingresso.setHoraInicio(rs.getString("horaInicio"));
                ingresso.setNomePeca(rs.getString("nomePeca"));
                ingresso.setPreco(rs.getBigDecimal("preco").doubleValue());

                ingressos.add(ingresso);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar ingressos no banco.", e);
        }

        return ingressos;
    }

    public List<Ingresso> buscarPorCpf(String cpf) throws PersistenciaException {
        String sql = """
        SELECT i.*, s.horaInicio AS horarioSessao, p.nome AS nomePeca
        FROM ingresso i
        JOIN sessao s ON i.IDSessao = s.ID
        JOIN peca p ON s.IDPeca = p.ID
        WHERE i.cpfComprador = ?
    """;

        List<Ingresso> lista = new ArrayList<>();

        try (Connection conexao = DatabaseConnection.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ingresso ingresso = new Ingresso();
                    ingresso.setId(rs.getLong("id"));
                    ingresso.setDataCompra(rs.getDate("dataCompra").toLocalDate());
                    ingresso.setCodPoltrona(rs.getString("poltrona"));
                    ingresso.setAreaPoltrona(rs.getString("areaPoltrona"));
                    ingresso.setCpf(rs.getString("cpfComprador"));

                    if (rs.getInt("IDCliente") > 0) {
                        ingresso.setIdCliente(rs.getInt("IDCliente"));
                    }

                    ingresso.setIdSessao(rs.getLong("IDSessao"));

                    ingresso.setHoraInicio(rs.getString("horarioSessao"));
                    ingresso.setNomePeca(rs.getString("nomePeca"));
                    ingresso.setPreco(rs.getBigDecimal("preco").doubleValue());

                    lista.add(ingresso);
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar ingressos por CPF.", e);
        }

        return lista;
    }
}
