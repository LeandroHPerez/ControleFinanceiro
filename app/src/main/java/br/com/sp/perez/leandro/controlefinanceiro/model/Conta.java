package br.com.sp.perez.leandro.controlefinanceiro.model;

import java.io.Serializable;

public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String descricao;
    private Double saldo_inicial;
    private Double saldo_atual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getSaldo_inicial() {
        return saldo_inicial;
    }

    public void setSaldo_inicial(Double saldo_inicial) {
        this.saldo_inicial = saldo_inicial;
    }

    public Double getSaldo_atual() {
        return saldo_atual;
    }

    public void setSaldo_atual(Double saldo_atual) {
        this.saldo_atual = saldo_atual;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id='" + id + '\'' +
                ", descricao='" + descricao  + '\'' +
                ", saldo_inicial='" + saldo_inicial + '\'' +
                ", saldo_atual=" + saldo_atual +
                '}';
    }
}





