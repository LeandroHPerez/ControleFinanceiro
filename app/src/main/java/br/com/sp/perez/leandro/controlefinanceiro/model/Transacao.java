package br.com.sp.perez.leandro.controlefinanceiro.model;

import java.io.Serializable;

public class Transacao implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String descricao;
    private String tipo_transacao;
    private Long id_conta;
    private String natureza_transacao;
    private Double valor;
    private Boolean isUnica;
    private String periodicidade;
    private Long qtdRepeticoes;
    private String dataLancamento;


    public Transacao() {
    }

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

    public String getTipo_transacao() {
        return tipo_transacao;
    }

    public void setTipo_transacao(String tipo_transacao) {
        this.tipo_transacao = tipo_transacao;
    }

    public Long getId_conta() {
        return id_conta;
    }

    public void setId_conta(Long id_conta) {
        this.id_conta = id_conta;
    }

    public String getNatureza_transacao() {
        return natureza_transacao;
    }

    public void setNatureza_transacao(String natureza_transacao) {
        this.natureza_transacao = natureza_transacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getUnica() {
        return isUnica;
    }

    public void setUnica(Boolean unica) {
        isUnica = unica;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public Long getQtdRepeticoes() {
        return qtdRepeticoes;
    }

    public void setQtdRepeticoes(Long qtdRepeticoes) {
        this.qtdRepeticoes = qtdRepeticoes;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id='" + id + '\'' +
                ", descricao='" + descricao  + '\'' +
                ", tipo_transacao='" + tipo_transacao + '\'' +
                ", id_conta=" + natureza_transacao +
                ", natureza_transacao=" + natureza_transacao +
                ", valor=" + valor +
                ", isUnica=" + isUnica +
                ", periodicidade=" + periodicidade +
                ", qtdRepeticoes=" + qtdRepeticoes +
                ", dataLancamento=" + dataLancamento +
                '}';
    }
}





