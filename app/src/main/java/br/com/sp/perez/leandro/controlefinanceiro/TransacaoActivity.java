package br.com.sp.perez.leandro.controlefinanceiro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.sp.perez.leandro.controlefinanceiro.model.Conta;
import br.com.sp.perez.leandro.controlefinanceiro.model.TipoTransacao;
import br.com.sp.perez.leandro.controlefinanceiro.model.Transacao;
import br.com.sp.perez.leandro.controlefinanceiro.repository.ContaRepository;
import br.com.sp.perez.leandro.controlefinanceiro.repository.TransacaoRepository;

public class TransacaoActivity extends AppCompatActivity {

    private Transacao transacao;
    private TransacaoRepository transacaoRepository;

    private ContaRepository contaRepository;
    private List<Conta> contas = new ArrayList<>();


    private EditText edtTxtDescricao;

    private Spinner spnTipoTransacao;
    private Spinner spnContaDaTransacao;

    private RadioGroup rdGrpNaturezaTransacao;


    private EditText edtTxtValor;
    private RadioGroup rdGrpRepeticaoTransacao;

    private LinearLayout linearAgrupadorRepeticaoTransacao;
    private Spinner spinnerPeriodicidade;
    private EditText editTextQtdDeRepeticao;
    private EditText editTextDataTransacao;
    private Button btnEscolherData;



    List<String> periodicidade = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        obterReferenciasComponentesUI();
        ajustarListenerRdGrpRepeticaoTransacao(rdGrpRepeticaoTransacao);
        ajustarListenerRdGrpNaturezaTransacao(rdGrpNaturezaTransacao);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();


                salvarAtualizar();
            }
        });





        initTipoTransacao();
        initContaDaTransacao();
        configurarSpinnerPeriodicidade(spinnerPeriodicidade);

        //DAO
        //transacaoRepository = new TransacaoRepository(this);

    }


    private void obterReferenciasComponentesUI(){

        edtTxtDescricao = ((EditText) findViewById(R.id.editTextDescricao));
        spnTipoTransacao = (Spinner) findViewById(R.id.spinnerTipoTransacao);
        spnContaDaTransacao = (Spinner) findViewById(R.id.spinnerContaDaTransacao);
        rdGrpNaturezaTransacao = (RadioGroup) findViewById(R.id.rdGrpNaturezaTransacao);
        edtTxtValor = (EditText) findViewById(R.id.editTextValorTransacao);
        rdGrpRepeticaoTransacao = (RadioGroup) findViewById(R.id.rdGrpRepeticaoTransacao);
        linearAgrupadorRepeticaoTransacao = (LinearLayout) findViewById(R.id.linearAgrupadorRepeticaoTransacao);
        spinnerPeriodicidade = (Spinner) findViewById(R.id.spinnerPeriodicidade);
        editTextQtdDeRepeticao = (EditText) findViewById(R.id.editTextQtdDeRepeticao);
        editTextDataTransacao = (EditText) findViewById(R.id.editTextDataTransacao);
        btnEscolherData = (Button) findViewById(R.id.btnEscolherData);
    }

    private void ajustarListenerRdGrpNaturezaTransacao(RadioGroup radioGrp){
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdBtnDedito) {

                    Snackbar.make(group, "Clicou em " + checkedId, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }

                if (checkedId == R.id.rdBtnCredito) {
                    Snackbar.make(group, "Clicou em " + checkedId, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }



            }
        });

    }

    private void ajustarListenerRdGrpRepeticaoTransacao(RadioGroup radioGrp){
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdBtnUnicaTransacao) {

                    Snackbar.make(group, "Clicou em " + checkedId, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }

                if (checkedId == R.id.rdBtnRepeticaoTransacao) {
                    Snackbar.make(group, "Clicou em " + checkedId, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }



            }
        });

    }





    private void initTipoTransacao() {

        ArrayList<String> tiposTransacaoes = new ArrayList<>();

        for (TipoTransacao tipoTransacao : TipoTransacao.values()) {
            tiposTransacaoes.add(tipoTransacao.getDescricao());
        }

        ArrayAdapter adapter = new ArrayAdapter(TransacaoActivity.this, android.R.layout.simple_spinner_item, tiposTransacaoes);
        //Muda o estilo exibido para a seleção
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnTipoTransacao.setAdapter(adapter);
    }


    private void initContaDaTransacao() {

        contaRepository = new ContaRepository(this);
        contas.clear();
        contas.addAll(contaRepository.listarContas());


        ArrayList<String> descricaoContas = new ArrayList<>();

        for (Conta conta : contas) {
            descricaoContas.add(conta.getDescricao());
        }


        ArrayAdapter adapter = new ArrayAdapter(TransacaoActivity.this, android.R.layout.simple_spinner_item, descricaoContas);
        //Muda o estilo exibido para a seleção
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnContaDaTransacao.setAdapter(adapter);
    }



    private void configurarSpinnerPeriodicidade(Spinner spinnerPeriodicidade){

        periodicidade.add("Diária");
        periodicidade.add("Semanal");
        periodicidade.add("Mensal");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, periodicidade);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodicidade.setAdapter(dataAdapter);
    }



    private void configurarUI_NaturezaOperacao(){

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

            if (transacao == null)
                transacao = new Transacao();

            transacao.setDescricao(descricao);

            /*
            transacao.setSaldo_inicial(Double.parseDouble(saldoInicial));
            transacao.setSaldo_atual(Double.parseDouble(saldoAtual));
            */


            //DAO
            TransacaoRepository transacaoRepository = new TransacaoRepository(this);
            transacaoRepository.salvarAtualizarConta(transacao);

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
