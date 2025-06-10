package com.teatroingressos.pi20251.model.dao;

import com.teatroingressos.pi20251.exception.ClienteException;
import com.teatroingressos.pi20251.exception.PersistenciaException;
import com.teatroingressos.pi20251.model.domain.Cliente;
import com.teatroingressos.pi20251.model.domain.Endereco;
import com.teatroingressos.pi20251.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public int salvar(Cliente cliente, int idEndereco) throws ClienteException {
        String sql = "INSERT INTO cliente (nome, cpf, dataNascimento, telefone, IDEndereco) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstancia().getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(4, cliente.getTelefone());
            stmt.setInt(5, idEndereco);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new ClienteException("Erro ao salvar cliente: " + e.getMessage());
        }

        return -1;
    }

    public List<Cliente> buscarTodos() throws PersistenciaException {
        List<Cliente> clientes = new ArrayList<>();

        String sql = """
            SELECT
                c.ID AS cliente_id, c.nome, c.cpf, c.dataNascimento, c.telefone,
                e.ID AS endereco_id, e.rua, e.numero, e.complemento, e.bairro, e.cidade, e.estado, e.cep
            FROM cliente c
            JOIN endereco e ON c.IDEndereco = e.ID
        """;

        try (Connection conexao = DatabaseConnection.getInstancia().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Endereco endereco = new Endereco();
                endereco.setId(rs.getInt("endereco_id"));
                endereco.setRua(rs.getString("rua"));
                endereco.setNumero(rs.getString("numero"));
                endereco.setComplemento(rs.getString("complemento"));
                endereco.setBairro(rs.getString("bairro"));
                endereco.setCidade(rs.getString("cidade"));
                endereco.setEstado(rs.getString("estado"));
                endereco.setCEP(rs.getString("cep"));

                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(endereco);

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar clientes no banco.", e);
        }

        return clientes;
    }
}
