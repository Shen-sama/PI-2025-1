package com.teatroingressos.pi20251.service;

import com.teatroingressos.pi20251.exception.PecaException;
import com.teatroingressos.pi20251.exception.SessaoException;
import com.teatroingressos.pi20251.model.dao.PecaTeatralDAO;
import com.teatroingressos.pi20251.model.dao.SessaoDAO;
import com.teatroingressos.pi20251.model.domain.PecaTeatral;
import com.teatroingressos.pi20251.model.domain.Sessao;

public class PecaTeatralService {

    private final PecaTeatralDAO pecaTeatralDAO = new PecaTeatralDAO();
    private final SessaoDAO sessaoDAO = new SessaoDAO();

    public PecaTeatral cadastrarPeca(PecaTeatral peca) throws PecaException {
        int idPeca = pecaTeatralDAO.salvar(peca);
        if (idPeca <= 0) {
            throw new PecaException("Não foi possível salvar a peça no banco de dados.");
        }

        peca.setId(idPeca);
        return peca;
    }

    public void adicionarSessao(PecaTeatral peca, Sessao sessao) throws SessaoException {
        if (peca.getId() <= 0) {
            throw new SessaoException("Peça não possui ID válido para adicionar sessão.");
        }

        int idSessao = sessaoDAO.salvar(sessao, (int) peca.getId());
        if (idSessao <= 0) {
            throw new SessaoException("Não foi possível salvar a sessão no banco de dados.");
        }

        sessao.setId(idSessao);
        peca.adicionarSessao(sessao);
    }
}
