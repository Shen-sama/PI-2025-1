package com.teatroingressos.pi20251.model.builder;

import com.teatroingressos.pi20251.model.domain.Area;
import com.teatroingressos.pi20251.model.enums.TipoArea;

public class AreaBuilder {
    private TipoArea tipo;
    private int fileiras;
    private int poltronasPorFileira;
    private double preco;

    public AreaBuilder setTipo(TipoArea tipo) {
        this.tipo = tipo;
        return this;
    }

    public AreaBuilder setFileiras(int fileiras) {
        this.fileiras = fileiras;
        return this;
    }

    public AreaBuilder setPoltronasPorFileira(int poltronasPorFileira) {
        this.poltronasPorFileira = poltronasPorFileira;
        return this;
    }

    public AreaBuilder setPreco(double preco) {
        this.preco = preco;
        return this;
    }

    public Area build() {
        return new Area(tipo, preco, fileiras, poltronasPorFileira);
    }

}
