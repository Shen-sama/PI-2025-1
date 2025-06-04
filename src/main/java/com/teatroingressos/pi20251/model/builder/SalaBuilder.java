package com.teatroingressos.pi20251.model.builder;

import com.teatroingressos.pi20251.model.domain.Area;
import com.teatroingressos.pi20251.model.domain.Sala;

import java.util.ArrayList;
import java.util.List;

public class SalaBuilder {
    private List<Area> areas = new ArrayList<>();

    public SalaBuilder adicionarSetor(Area area) {
        areas.add(area);
        return this;
    }

    public Sala build() {
        Sala sala = new Sala();
        sala.setAreas(areas);
        return sala;
    }
}
