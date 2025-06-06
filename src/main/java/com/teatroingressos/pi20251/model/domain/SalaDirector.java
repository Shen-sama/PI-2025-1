package com.teatroingressos.pi20251.model.domain;

import java.util.ArrayList;
import java.util.List;

public class SalaDirector {

    private final AreaDirector areaDirector;

    public SalaDirector() {
        this.areaDirector = new AreaDirector();
    }

    public Sala criarSalaCompleta() {
        List<Area> areas = new ArrayList<>();
        areas.add(areaDirector.criarPlateiaA());
        areas.add(areaDirector.criarPlateiaB());
        areas.add(areaDirector.criarCamarote());
        areas.add(areaDirector.criarFrisa());
        areas.add(areaDirector.criarBalcaoNobre());

        return new Sala(areas);
    }

}
