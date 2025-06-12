package com.teatroingressos.pi20251.model.repository;

import com.teatroingressos.pi20251.app.MainApp;
import com.teatroingressos.pi20251.exception.PersistenciaException;
import com.teatroingressos.pi20251.model.dao.IngressoDAO;
import com.teatroingressos.pi20251.model.dao.PecaTeatralDAO;
import com.teatroingressos.pi20251.model.domain.Ingresso;
import com.teatroingressos.pi20251.model.domain.PecaTeatral;
import com.teatroingressos.pi20251.model.domain.Sessao;
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

    public void carregarPecasComIngressos() {
        PecaTeatralDAO pecaTeatralDAO = new PecaTeatralDAO();
        IngressoDAO ingressoDAO = new IngressoDAO();

        try {
            List<PecaTeatral> listaPecas = pecaTeatralDAO.buscarTodas();
            for (PecaTeatral p : listaPecas) {
                if (!pecasPorNome.containsKey(p.getNome())) {
                    cadastrar(p);
                }
            }

            List<Ingresso> ingressos = ingressoDAO.buscarTodosIngressos();

            associarIngressosASessoes(ingressos, listaPecas);

        } catch (PersistenciaException e) {
            AlertUtils.mostrarErro("Erro ao carregar peças/ingressos", e.getMessage());
        }
    }

    public void atualizarDisponibilidadeSessaoRepositorio(String nomePeca, String horario, boolean disponivel) {
        PecaTeatral pecaTeatral = this.pecasPorNome.get(nomePeca);

        for (Sessao sessao : pecaTeatral.getSessoes()) {
            if (sessao.getHorario().equalsIgnoreCase(horario)) {
                sessao.setDisponivel(disponivel);
                break;
            }
        }
    }

    private void associarIngressosASessoes(List<Ingresso> ingressos, List<PecaTeatral> pecas) {
        for (Ingresso ingresso : ingressos) {
            for (PecaTeatral peca : pecas) {
                if (!peca.getNome().equalsIgnoreCase(ingresso.getNomePeca())) continue;

                for (Sessao sessao : peca.getSessoes()) {
                    if (sessao.getId() == ingresso.getIdSessao()) {
                        sessao.adicionarIngresso(ingresso);
                        sessao.ocuparPoltronaAreaIngresso(ingresso);
                        break;
                    }
                }
            }
        }
    }
}
