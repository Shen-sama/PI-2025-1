package com.teatroingressos.pi20251.service;

import com.teatroingressos.pi20251.exception.IngressoException;
import com.teatroingressos.pi20251.model.dao.IngressoDAO;
import com.teatroingressos.pi20251.model.domain.Ingresso;
import com.teatroingressos.pi20251.model.domain.Sessao;

public class IngressoService {
    private final IngressoDAO ingressoDAO = new IngressoDAO();

    public Ingresso comprarIngresso(Ingresso ingresso, Sessao sessao, int idCliente) throws IngressoException {
        int idIngresso = ingressoDAO.salvar(ingresso, (int) sessao.getId(), idCliente);
        if (idIngresso <= 0) {
            throw new IngressoException("Não foi possível salvar o ingresso no banco de dados.");
        }

        ingresso.setId(idIngresso);
        return ingresso;
    }
}
