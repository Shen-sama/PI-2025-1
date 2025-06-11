package com.teatroingressos.pi20251.model.repository;

import com.teatroingressos.pi20251.exception.PersistenciaException;
import com.teatroingressos.pi20251.model.dao.IngressoDAO;
import com.teatroingressos.pi20251.model.domain.Cliente;
import com.teatroingressos.pi20251.model.domain.Ingresso;
import com.teatroingressos.pi20251.model.domain.PecaTeatral;
import com.teatroingressos.pi20251.util.AlertUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngressoRepository {
    private final Map<Long, Ingresso> ingressosPorID;

    public IngressoRepository() {
        ingressosPorID = new HashMap<>();
    }

    public Map<Long, Ingresso> getIngressosPorID() {
        return ingressosPorID;
    }

    public void cadastrar(Ingresso ingresso) {
        ingressosPorID.put(ingresso.getId(), ingresso);
    }

    public void carregarIngressosDoBanco() {
        IngressoDAO ingressoDAO = new IngressoDAO();

        try {
            List<Ingresso> lista = ingressoDAO.buscarTodosIngressos();
            for (Ingresso i : lista) {
                cadastrar(i);
            }
        } catch (PersistenciaException e) {
            AlertUtils.mostrarErro("Erro ao carregar ingressos", e.getMessage());
        }
    }
}
