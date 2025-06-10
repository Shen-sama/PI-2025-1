package com.teatroingressos.pi20251.model.domain;

import java.util.ArrayList;
import java.util.List;

public class PecaTeatral {
    private long id;

    private String nome;
    private String sinopse;
    private String duracao;
    private List<Sessao> sessoes;

    public PecaTeatral() {
        this.sessoes = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }

    public void setSessoes(List<Sessao> sessoes) {
        this.sessoes = sessoes;
    }

    public void adicionarSessao(Sessao sessao) {
        this.sessoes.add(sessao);
    }
}
