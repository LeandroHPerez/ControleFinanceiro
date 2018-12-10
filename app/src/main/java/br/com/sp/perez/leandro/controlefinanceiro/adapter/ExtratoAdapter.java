package br.com.sp.perez.leandro.controlefinanceiro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.sp.perez.leandro.controlefinanceiro.R;
import br.com.sp.perez.leandro.controlefinanceiro.model.Transacao;
import br.com.sp.perez.leandro.controlefinanceiro.model.Transacao;
import br.com.sp.perez.leandro.controlefinanceiro.repository.ContaRepository;
import br.com.sp.perez.leandro.controlefinanceiro.repository.TransacaoRepository;

public class ExtratoAdapter extends Adapter<ExtratoAdapter.ExtratoViewHolder> {


    private static List<Transacao> transacoes;
    private Context context;




    private ContaRepository contaRepository;
    private TransacaoRepository transacaoRepository;

    public ExtratoAdapter(List<Transacao> transacoes, Context context) {
        this.transacoes = transacoes;
        this.context = context;
    }


    @Override
    public ExtratoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.linha_extrato, parent, false);
        return new ExtratoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExtratoViewHolder contaViewHolder, int position) {
        Transacao transacao = transacoes.get(position);

        //contaViewHolder.imgConta;
        contaViewHolder.txtDescricao.setText(transacao.getDescricao());
        contaViewHolder.txtTipoTransacao.setText(transacao.getTipoTransacao());
        contaViewHolder.txtNatureza.setText(transacao.getNaturezaTransacao());
        contaViewHolder.txtValor.setText(transacao.getValor().toString());
        contaViewHolder.txtDataLancamento.setText(transacao.getDataLancamento());
    }


    @Override
    public int getItemCount() {
        return transacoes.size();
    }


    public static class ExtratoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView txtDescricao;
        final TextView txtTipoTransacao;
        final TextView txtNatureza;
        final TextView txtValor;
        final TextView txtDataLancamento;

        public ExtratoViewHolder(View itemView) {
            super(itemView);


            txtDescricao = (TextView)    itemView.findViewById(R.id.txtDescricao);
            txtTipoTransacao = (TextView)   itemView.findViewById(R.id.txtTipoTransacao);
            txtNatureza = (TextView) itemView.findViewById(R.id.txtNatureza);
            txtValor = (TextView) itemView.findViewById(R.id.txtValor);
            txtDataLancamento = (TextView) itemView.findViewById(R.id.txtDataLancamento);



            //Atribui o click listener para a linha
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            //Não faz nada, mas fica de molde para uma evolução de código futura
        }

        }




}