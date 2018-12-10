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

    //Listener de cliques
   // private static ItemClickListener clickListener;
    private static ItemClickListenerCustom clickListenerCustom;





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

        //Calcula o saldo atual
        if(contaRepository == null)
            contaRepository = new ContaRepository(context);
        Double resultado = transacaoRepository.obterSaldoAtualTotalDeTodasAsContas(); //a conta é feita dentro do método
        if(resultado == null) //caso o resultado atual seja nulo é porque o saldo atual não é afetado pelas transações (não há transações) e deverá ser exibido com o valor cadastrado
            contaViewHolder.txtSaldoAtual.setText(transacao.getDescricao().toString());
        else
            contaViewHolder.txtSaldoAtual.setText(resultado.toString());






        contaViewHolder.txtSaldoInicial.setText(transacao.getDescricao().toString());
        //contaViewHolder.btnDetalhes
    }


    @Override
    public int getItemCount() {
        return transacoes.size();
    }


    public static class ExtratoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final ImageView imgConta;
        final TextView txtDescricao;
        final TextView txtSaldoAtual;
        final TextView txtSaldoInicial;
        final Button btnGerarExtratoConta;

        public ExtratoViewHolder(View itemView) {
            super(itemView);

            imgConta = (ImageView)       itemView.findViewById(R.id.imgConta);
            txtDescricao = (TextView)    itemView.findViewById(R.id.txtDescricao);
            txtSaldoAtual = (TextView)   itemView.findViewById(R.id.txtSaldoAtual);
            txtSaldoInicial = (TextView) itemView.findViewById(R.id.txtSaldoInicial);
            btnGerarExtratoConta = (Button)       itemView.findViewById(R.id.btnGerarExtratoDaConta);


            //Atribui o click listener para a linha
            itemView.setOnClickListener(this);
            btnGerarExtratoConta.setOnClickListener(this);

/*
            //Gerar relatório
            btnGerarExtratoConta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), "Item Clicado!", Toast.LENGTH_SHORT).show();

                    int position = getAdapterPosition(); //posição item clicado

                    Conta conta = contas.get(position);


                    //ContaRepository contaRepository = new ContaRepository(v.getContext());

                    Intent intent = new Intent(v.getContext(), ExtratoActivity.class);
                    intent.putExtra(EXTRA_EXTRATO_RELATORIO, conta);


                }
            });

            */


        }

        @Override
        public void onClick(View v) {
            //if (clickListener != null)
                //clickListener.onItemClick(getAdapterPosition()); //retorna a posição do clique //Returns the Adapter position of the item represented by this ViewHolder.
           //Toast.makeText(v.getContext(),"Entrou on click", Toast.LENGTH_SHORT).show();

            if (clickListenerCustom != null)
                if (v.getId() == R.id.btnGerarExtratoDaConta) {
                    clickListenerCustom.onItemClickBotaoGerarRelatorio(getAdapterPosition()); //retorna a posição do clique //Returns the Adapter position of the item represented by this ViewHolder.
            } else if (v.getId() == R.id.linhaConta)
                    clickListenerCustom.onItemClickCard(getAdapterPosition()); //retorna a posição do clique //Returns the Adapter position of the item represented by this ViewHolder.
        }

        }









/*

    //Listener de cliques
    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    //Interface de clique
    public interface ItemClickListener {
        void onItemClick(int position);
    }*/






    //Listener de cliques
    public void setClickListenerCustom(ItemClickListenerCustom itemListener) {
        clickListenerCustom = itemListener;
    }

    //Interface de clique
    public interface ItemClickListenerCustom {
        void onItemClickCard(int position);
        void onItemClickBotaoGerarRelatorio(int position);
    }




}