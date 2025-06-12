package com.teatroingressos.pi20251.model.domain;

import com.teatroingressos.pi20251.model.enums.TipoArea;
import com.teatroingressos.pi20251.util.PoltronaUtils;

import java.util.Map;

public class Area {
    private TipoArea tipo;
    private int fileiras;
    private int poltronasPorFileira;
    private double preco;
    private Map<String, Poltrona> poltronas;

    public Area(TipoArea tipo, double preco, int fileiras, int poltronasPorFileira) {
        this.tipo = tipo;
        this.preco = preco;
        this.fileiras = fileiras;
        this.poltronasPorFileira = poltronasPorFileira;
        this.poltronas = PoltronaUtils.gerarPoltronas(fileiras, poltronasPorFileira);
    }

    public TipoArea getTipo() {
        return tipo;
    }

    public int getPoltronasPorFileira() {
        return poltronasPorFileira;
    }

    public double getPreco() {
        return preco;
    }

    public Map<String, Poltrona> getPoltronas() {
        return poltronas;
    }


}
