package com.teatroingressos.pi20251.model.repository;

import com.teatroingressos.pi20251.exception.PersistenciaException;
import com.teatroingressos.pi20251.model.dao.ClienteDAO;
import com.teatroingressos.pi20251.model.domain.Cliente;
import com.teatroingressos.pi20251.util.AlertUtils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteRepository {
    private final Map<String, Cliente> clientesPorCpf;

    public ClienteRepository() {
        clientesPorCpf = new HashMap<>();
    }

    public Map<String, Cliente> getClientesPorCpf() {
        return clientesPorCpf;
    }

    public void cadastrar(Cliente cliente) {
        clientesPorCpf.put(cliente.getCpf(), cliente);
    }

    public void carregarClientesDoBanco() {
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            List<Cliente> lista = clienteDAO.buscarTodos();
            for (Cliente c : lista) {
                clientesPorCpf.put(c.getCpf(), c);
            }
        } catch (PersistenciaException e) {
            AlertUtils.mostrarErro("Erro ao carregar clientes", e.getMessage());
        }
    }
}
