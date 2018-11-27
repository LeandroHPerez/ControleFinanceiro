package br.com.sp.perez.leandro.controlefinanceiro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

                salvarAtualizar();
            }
        });

        obterReferenciasComponentesUI();

        tratarEventoDigitacao();


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


    private void tratarEventoDigitacao(){
        //Trata evento de digitação
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String a = s.toString();
                edtTxtSaldoAtual.setText(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        //Adiciona o listener de digitação
        edtTxtSaldoInicial.addTextChangedListener(textWatcher);
    }


    private void salvarAtualizar(){
        String descricao = ((EditText) findViewById(R.id.editTextDescricao)).getText().toString();
        String saldoInicial = ((EditText) findViewById(R.id.editTextSaldoInicial)).getText().toString();
        String saldoAtual = ((EditText) findViewById(R.id.editTextSaldoAtual)).getText().toString();

        //Se os campos não estiverem preenchidos
        if (descricao == null ||  descricao.equals("")
                || saldoInicial == null || saldoInicial.equals("")
                || saldoAtual == null || saldoAtual.equals("")) {

            showSnackBar("Por favor preencher todos os campos");

        }else {  //Se os campos estiverem preenchidos salva/atualiza

            if (conta == null)
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




    private void showSnackBar(String msg) {
        CoordinatorLayout coordinatorlayout= (CoordinatorLayout)findViewById(R.id.coordlayout);
        Snackbar.make(coordinatorlayout, msg,
                Snackbar.LENGTH_LONG)
                .show();
    }

}
