package com.teatroingressos.pi20251.model.enums;

public enum TipoArea {
    PLATEIA_A("Platéia A"),
    PLATEIA_B("Platéia B"),
    CAMAROTE("Camarote"),
    FRISA("Frisa"),
    BALCAO_NOBRE("Balcão Nobre");

    private final String nomeCorreto;

    TipoArea(String nomeCorreto) {
        this.nomeCorreto = nomeCorreto;
    }

    @Override
    public String toString() {
        return nomeCorreto;
    }
}
