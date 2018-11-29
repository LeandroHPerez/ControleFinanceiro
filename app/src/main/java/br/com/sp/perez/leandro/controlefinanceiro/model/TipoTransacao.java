package br.com.sp.perez.leandro.controlefinanceiro.model;

public enum TipoTransacao {

    ALIMENTACAO("Alimentação"), SAUDE("Saúde"), TRANSPORTE("Transporte"), MORADIA("moradia"), EDUCACAO("Educação"), LAZER("Lazer"), TARIFAS_BANCARIAS("Tarifas bancárias"),
    LUZ("Luz"), AGUA("Água"), TELEFONE("Telefone");


    private String descricao;

    private TipoTransacao(String descricao) {
        this.descricao = descricao;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }



    public static TipoTransacao getTipoTransacao(int pos) {
        for (TipoTransacao tipoTransacao : TipoTransacao.values()) {
            if (tipoTransacao.ordinal() == pos) {
                return tipoTransacao;
            }
        }
        return null;
    }
}
