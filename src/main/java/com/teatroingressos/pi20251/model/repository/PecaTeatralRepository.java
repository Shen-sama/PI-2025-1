package com.teatroingressos.pi20251.model.repository;

import com.teatroingressos.pi20251.exception.PersistenciaException;
import com.teatroingressos.pi20251.model.dao.PecaTeatralDAO;
import com.teatroingressos.pi20251.model.domain.PecaTeatral;
import com.teatroingressos.pi20251.util.AlertUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PecaTeatralRepository {
    private final Map<String, PecaTeatral> pecasPorNome;

    public PecaTeatralRepository() {
        pecasPorNome = new HashMap<>();
    }

    public Map<String, PecaTeatral> getPecasPorNome() {
        return pecasPorNome;
    }

    public void cadastrar(PecaTeatral peca) {
        pecasPorNome.put(peca.getNome(), peca);
    }

    public void carregarPecasDoBanco() {
        PecaTeatralDAO pecaTeatralDAO = new PecaTeatralDAO();

        try {
            List<PecaTeatral> lista = pecaTeatralDAO.buscarTodas();
            for (PecaTeatral p : lista) {
                if (!pecasPorNome.containsKey(p.getNome())) {
                    cadastrar(p);
                }
            }
        } catch (PersistenciaException e) {
            AlertUtils.mostrarErro("Erro ao carregar peças", e.getMessage());
        }
    }
}
