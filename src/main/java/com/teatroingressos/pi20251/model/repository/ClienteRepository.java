package com.teatroingressos.pi20251.model.repository;

import com.teatroingressos.pi20251.model.domain.Cliente;

import java.util.HashMap;
import java.util.Map;

public class ClienteRepository {
    private final Map<String, Cliente> clientesPorCpf = new HashMap<>();

    public void cadastrar(Cliente cliente) {
        clientesPorCpf.put(cliente.getCpf(), cliente);
    }
}
