package com.teatroingressos.pi20251.model.domain;

import java.time.LocalDate;

public class Ingresso {

    private long id;
    private long idSessao;
    private long idCliente;

    private String cpf;
    private String nomePeca;
    private LocalDate dataCompra;
    private String horaInicio;
    private String areaPoltrona;
    private String codPoltrona;
    private double preco;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(long idSessao) {
        this.idSessao = idSessao;
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

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getAreaPoltrona() {
        return areaPoltrona;
    }

    public void setAreaPoltrona(String areaPoltrona) {
        this.areaPoltrona = areaPoltrona;
    }

    public String getCodPoltrona() {
        return codPoltrona;
    }

    public void setCodPoltrona(String codPoltrona) {
        this.codPoltrona = codPoltrona;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getHoraInicio() {
        return horaInicio;
    }
}
