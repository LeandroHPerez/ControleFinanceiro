package br.com.sp.perez.leandro.controlefinanceiro.model;

import java.io.Serializable;

public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String descricao;
    private String saldo_inicial;
    private String saldo_atual;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSaldo_inicial() {
        return saldo_inicial;
    }

    public void setSaldo_inicial(String saldo_inicial) {
        this.saldo_inicial = saldo_inicial;
    }

    public String getSaldo_atual() {
        return saldo_atual;
    }

    public void setSaldo_atual(String saldo_atual) {
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





