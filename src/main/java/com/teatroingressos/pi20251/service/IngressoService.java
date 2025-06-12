package com.teatroingressos.pi20251.service;

import com.teatroingressos.pi20251.exception.IngressoException;
import com.teatroingressos.pi20251.exception.PersistenciaException;
import com.teatroingressos.pi20251.model.dao.IngressoDAO;
import com.teatroingressos.pi20251.model.domain.Area;
import com.teatroingressos.pi20251.model.domain.Ingresso;
import com.teatroingressos.pi20251.model.domain.Poltrona;
import com.teatroingressos.pi20251.model.domain.Sessao;

import java.util.List;
import java.util.Map;

public class IngressoService {
    private final IngressoDAO ingressoDAO = new IngressoDAO();

    public List<Ingresso> buscarPorCpf(String cpf) throws PersistenciaException {
        return ingressoDAO.buscarPorCpf(cpf);
    }

    public void comprarIngresso(Ingresso ingresso, Sessao sessao) throws PersistenciaException, IngressoException {
        int id = ingressoDAO.salvar(ingresso);
        if (id <= 0) {
            throw new PersistenciaException("Erro ao salvar ingresso no banco.");
        }
        ingresso.setId(id);
        sessao.adicionarIngresso(ingresso);

        String areaIngresso = ingresso.getAreaPoltrona();
        String codPoltrona = ingresso.getCodPoltrona();

        for (Area area : sessao.getSala().getAreas()) {
            if (areaIngresso.equalsIgnoreCase(area.getTipo().toString())) {
                Map<String, Poltrona> mapaPoltronas = area.getPoltronas();

                Poltrona poltrona = mapaPoltronas.get(codPoltrona);
                poltrona.ocupar();
                break;
            }
        }
    }
}
