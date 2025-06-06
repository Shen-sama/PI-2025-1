package com.teatroingressos.pi20251.model.domain;

import com.teatroingressos.pi20251.model.builder.AreaBuilder;
import com.teatroingressos.pi20251.model.enums.TipoArea;

public class AreaDirector {

    public Area criarPlateiaA() {
        return new AreaBuilder()
                .setTipo(TipoArea.PLATEIA_A)
                .setFileiras(5)
                .setPoltronasPorFileira(5)
                .setPreco(40)
                .build();
    }

    public Area criarPlateiaB() {
        return new AreaBuilder()
                .setTipo(TipoArea.PLATEIA_B)
                .setFileiras(10)
                .setPoltronasPorFileira(10)
                .setPreco(60)
                .build();
    }

    public Area criarCamarote() {
        return new AreaBuilder()
                .setTipo(TipoArea.CAMAROTE)
                .setFileiras(5)
                .setPoltronasPorFileira(10)
                .setPreco(80)
                .build();
    }

    public Area criarFrisa() {
        return new AreaBuilder()
                .setTipo(TipoArea.FRISA)
                .setFileiras(6)
                .setPoltronasPorFileira(5)
                .setPreco(120)
                .build();
    }

    public Area criarBalcaoNobre() {
        return new AreaBuilder()
                .setTipo(TipoArea.BALCAO_NOBRE)
                .setFileiras(5)
                .setPoltronasPorFileira(10)
                .setPreco(250)
                .build();
    }
}
