package com.teatroingressos.pi20251.model.domain;

import java.util.HashMap;
import java.util.Map;

public class Sessao {
    private long id;
    private long idPeca;

    private String horario;
    private Sala sala;
    private Map<String, Ingresso> ingressos;

    private boolean disponivel;

    public Sessao() {
        SalaDirector salaDirector = new SalaDirector();
        this.sala = salaDirector.criarSalaCompleta();
        this.ingressos = new HashMap<>();
        this.disponivel = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(long idPeca) {
        this.idPeca = idPeca;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Sala getSala() {
        return sala;
    }

    public Map<String, Ingresso> getIngressos() {
        return ingressos;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void habilitar() {
        this.disponivel = true;
    }

    public void desabilitar() {
        this.disponivel = false;
    }

    public void adicionarIngresso(Ingresso ingresso) {
        this.ingressos.put(ingresso.getCodPoltrona(), ingresso);
    }


}
