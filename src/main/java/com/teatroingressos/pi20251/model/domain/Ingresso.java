package com.teatroingressos.pi20251.model.domain;

import java.time.LocalDate;

public class Ingresso {

    private long id;
    private long idPeca;
    private long idCliente;

    private String cpf;
    private String nomePeca;
    private LocalDate dataPeca;
    private String horaInicio;
    private String codPoltrona;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(long idPeca) {
        this.idPeca = idPeca;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
    }

    public LocalDate getDataPeca() {
        return dataPeca;
    }

    public void setDataPeca(LocalDate dataPeca) {
        this.dataPeca = dataPeca;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getCodPoltrona() {
        return codPoltrona;
    }

    public void setCodPoltrona(String codPoltrona) {
        this.codPoltrona = codPoltrona;
    }
}
