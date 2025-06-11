package com.teatroingressos.pi20251.model.domain;

import java.util.List;

public class Sala {
    private List<Area> areas;

    public Sala(List<Area> areas) {
        this.areas = areas;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public Area getAreaPorNome(String nomeArea) {
        for (Area area : this.areas) {
            if (nomeArea.equalsIgnoreCase(area.getTipo().toString())) {
                return area;
            }
        }

        return null;
    }
}
