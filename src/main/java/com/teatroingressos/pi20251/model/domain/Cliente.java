package com.teatroingressos.pi20251.model.domain;

import java.time.LocalDate;

public class Cliente {
    private long id;
    private String nome;
    private String cpf;
    private String telefone;
    private Endereco endereco;
    private LocalDate dataNascimento;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
