package com.teatroingressos.pi20251.model.domain;

public class Poltrona {
    private String codigo;
    private boolean disponivel;

    public Poltrona(String codigo) {
        this.codigo = codigo;
        this.disponivel = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void ocupar() {
        this.disponivel = false;
    }

    public void liberar() {
        this.disponivel = true;
    }
}
