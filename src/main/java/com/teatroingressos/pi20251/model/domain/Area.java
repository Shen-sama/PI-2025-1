package com.teatroingressos.pi20251.model.domain;

import com.teatroingressos.pi20251.model.enums.TipoArea;

import java.util.Map;

public class Area {
    private TipoArea tipo;
    private double preco;
    private Map<String, Poltrona> poltronas;

    public Area(TipoArea tipo, double preco, Map<String, Poltrona> poltronas) {
        this.tipo = tipo;
        this.preco = preco;
        this.poltronas = poltronas;
    }

    public TipoArea getTipo() {
        return tipo;
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
