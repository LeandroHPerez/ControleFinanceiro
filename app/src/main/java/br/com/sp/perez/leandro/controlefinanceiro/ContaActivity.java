package br.com.sp.perez.leandro.controlefinanceiro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.sp.perez.leandro.controlefinanceiro.model.Conta;
import br.com.sp.perez.leandro.controlefinanceiro.repository.ContaRepository;

public class ContaActivity extends AppCompatActivity {

    private Conta conta;


    ImageView imgViewConta;
    EditText edtTxtDescricao;
    EditText edtTxtSaldoInicial;
    EditText edtTxtSaldoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setSubtitle("Cadastro de conta");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                salvarAtualizar();
            }
        });

        obterReferenciasComponentesUI();


        //Modo edição
        if(getIntent().hasExtra(MainActivity.EXTRA_CONTA)){

            getSupportActionBar().setSubtitle("Edição de conta");
            this.conta = (Conta) getIntent().getSerializableExtra(MainActivity.EXTRA_CONTA);

            edtTxtDescricao.setText(conta.getDescricao());
            edtTxtSaldoInicial.setText(conta.getSaldo_inicial().toString());
            edtTxtSaldoAtual.setText(conta.getSaldo_atual().toString());

        }

    }



    private void obterReferenciasComponentesUI(){

        imgViewConta = (ImageView) findViewById(R.id.imgConta);
        edtTxtDescricao = (EditText) findViewById(R.id.editTextDescricao);
        edtTxtSaldoInicial = (EditText) findViewById(R.id.editTextSaldoInicial);
        edtTxtSaldoAtual = (EditText) findViewById(R.id.editTextSaldoAtual);
    }


    private void salvarAtualizar(){
        String descricao = ((EditText) findViewById(R.id.editTextDescricao)).getText().toString();
        String saldoInicial = ((EditText) findViewById(R.id.editTextSaldoInicial)).getText().toString();
        String saldoAtual = ((EditText) findViewById(R.id.editTextSaldoAtual)).getText().toString();

        if(conta == null)
            conta = new Conta();

        conta.setDescricao(descricao);
        conta.setSaldo_inicial(Double.parseDouble(saldoInicial));
        conta.setSaldo_atual(Double.parseDouble(saldoAtual));


        //DAO
        ContaRepository contaRepository = new ContaRepository(this);
        contaRepository.salvarAtualizarConta(conta);

        //Intent de resultado
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();

    }

}
