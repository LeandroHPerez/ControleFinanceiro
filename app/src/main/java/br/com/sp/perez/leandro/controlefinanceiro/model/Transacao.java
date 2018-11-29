package br.com.sp.perez.leandro.controlefinanceiro.model;

import java.io.Serializable;

public class Transacao implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String descricao;
    private String tipoTransacao;
    private Long idConta;
    private String naturezaTransacao;
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

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Long getIdConta() {
        return idConta;
    }

    public void setIdConta(Long idConta) {
        this.idConta = idConta;
    }

    public String getNaturezaTransacao() {
        return naturezaTransacao;
    }

    public void setNaturezaTransacao(String naturezaTransacao) {
        this.naturezaTransacao = naturezaTransacao;
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
                ", tipoTransacao='" + tipoTransacao + '\'' +
                ", idConta=" + naturezaTransacao +
                ", naturezaTransacao=" + naturezaTransacao +
                ", valor=" + valor +
                ", isUnica=" + isUnica +
                ", periodicidade=" + periodicidade +
                ", qtdRepeticoes=" + qtdRepeticoes +
                ", dataLancamento=" + dataLancamento +
                '}';
    }
}





