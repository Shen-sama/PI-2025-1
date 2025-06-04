package com.teatroingressos.pi20251.model.domain;

import java.util.Map;

public class Area {
    private String nome;
    private double preco;
    private Map<String, Poltrona> poltronas;

    public Area(String nome, double preco, Map<String, Poltrona> poltronas) {
        this.nome = nome;
        this.preco = preco;
        this.poltronas = poltronas;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public Map<String, Poltrona> getPoltronas() {
        return poltronas;
    }

    public Poltrona getPoltrona(String codigo) {
        return poltronas.get(codigo);
    }
}
