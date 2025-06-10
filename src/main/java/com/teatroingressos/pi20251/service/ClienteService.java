package com.teatroingressos.pi20251.service;

import com.teatroingressos.pi20251.exception.ClienteException;
import com.teatroingressos.pi20251.exception.EnderecoException;
import com.teatroingressos.pi20251.model.dao.ClienteDAO;
import com.teatroingressos.pi20251.model.dao.EnderecoDAO;
import com.teatroingressos.pi20251.model.domain.Cliente;

public class ClienteService {

    private final EnderecoDAO enderecoDAO = new EnderecoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    public Cliente cadastrarCliente(Cliente cliente) throws ClienteException, EnderecoException {
        int idEndereco = enderecoDAO.salvar(cliente.getEndereco());
        if (idEndereco <= 0) {
            throw new EnderecoException("Não foi possível salvar o endereço no banco de dados.");
        }

        int idCliente = clienteDAO.salvar(cliente, idEndereco);
        if (idCliente <= 0) {
            throw new ClienteException("Não foi possível salvar o cliente no banco de dados.");
        }

        cliente.getEndereco().setId(idEndereco);
        cliente.setId(idCliente);
        return cliente;
    }
}
