package com.teatroingressos.pi20251.model.builder;

import com.teatroingressos.pi20251.model.domain.Area;
import com.teatroingressos.pi20251.util.PoltronaUtils;

public class AreaBuilder {
    private String nome;
    private int fileiras;
    private int poltronasPorFileira;
    private double preco;

    public AreaBuilder setNome(String nome) {
        this.nome = nome;
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
        return new Area(nome, preco, PoltronaUtils.gerarPoltronas(fileiras, poltronasPorFileira));
    }

}
