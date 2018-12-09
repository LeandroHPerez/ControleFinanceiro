package br.com.sp.perez.leandro.controlefinanceiro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.sp.perez.leandro.controlefinanceiro.R;
import br.com.sp.perez.leandro.controlefinanceiro.model.Conta;
import br.com.sp.perez.leandro.controlefinanceiro.model.Transacao;
import br.com.sp.perez.leandro.controlefinanceiro.repository.ContaRepository;
import br.com.sp.perez.leandro.controlefinanceiro.repository.TransacaoRepository;

public class ContasAdapter extends RecyclerView.Adapter<ContasAdapter.ContaViewHolder> {


    private static List<Conta> contas;
    private Context context;

    //Listener de cliques
    private static ItemClickListener clickListener;

    private ContaRepository contaRepository;

    public ContasAdapter(List<Conta> contas, Context context) {
        this.contas = contas;
        this.context = context;
    }


    @Override
    public ContaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.linha_conta, parent, false);
        return new ContaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContaViewHolder contaViewHolder, int position) {
        Conta conta = contas.get(position);

        //contaViewHolder.imgConta;
        contaViewHolder.txtDescricao.setText(conta.getDescricao());

        //Calcula o saldo atual
        if(contaRepository == null)
            contaRepository = new ContaRepository(context);
        Double resultado = contaRepository.calcularSaldoAtualConta(context, conta); //a conta é feita dentro do método
        if(resultado == null) //caso o resultado atual seja nulo é porque o saldo atual não é afetado pelas transações (não há transações) e deverá ser exibido com o valor cadastrado
            contaViewHolder.txtSaldoAtual.setText(conta.getSaldo_atual().toString());
        else
            contaViewHolder.txtSaldoAtual.setText(resultado.toString());






        contaViewHolder.txtSaldoInicial.setText(conta.getSaldo_inicial().toString());
        //contaViewHolder.btnDetalhes


    }

    @Override
    public int getItemCount() {
        return contas.size();
    }


    public static class ContaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final ImageView imgConta;
        final TextView txtDescricao;
        final TextView txtSaldoAtual;
        final TextView txtSaldoInicial;
        final Button btnDetalhes;

        public ContaViewHolder(View itemView) {
            super(itemView);

            imgConta = (ImageView)       itemView.findViewById(R.id.imgConta);
            txtDescricao = (TextView)    itemView.findViewById(R.id.txtDescricao);
            txtSaldoAtual = (TextView)   itemView.findViewById(R.id.txtSaldoAtual);
            txtSaldoInicial = (TextView) itemView.findViewById(R.id.txtSaldoInicial);
            btnDetalhes = (Button)       itemView.findViewById(R.id.btnNovaTransacao);


            //Atribui o click listener para a linha
            itemView.setOnClickListener(this);







        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition()); //retorna a posição do clique //Returns the Adapter position of the item represented by this ViewHolder.
        }
    }




    //Listener de cliques
    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    //Interface de clique
    public interface ItemClickListener {
        void onItemClick(int position);
    }












}