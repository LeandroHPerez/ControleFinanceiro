package br.com.sp.perez.leandro.controlefinanceiro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.sp.perez.leandro.controlefinanceiro.model.Conta;
import br.com.sp.perez.leandro.controlefinanceiro.model.TipoTransacao;
import br.com.sp.perez.leandro.controlefinanceiro.model.Transacao;
import br.com.sp.perez.leandro.controlefinanceiro.repository.ContaRepository;
import br.com.sp.perez.leandro.controlefinanceiro.repository.TransacaoRepository;
import br.com.sp.perez.leandro.controlefinanceiro.util.DatePickerFragment;

public class TransacaoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    private Transacao transacao;
    private TransacaoRepository transacaoRepository;

    private ContaRepository contaRepository;
    private List<Conta> contas = new ArrayList<>();


    private EditText edtTxtDescricao;

    private Spinner spnTipoTransacao;
    private Spinner spnContaDaTransacao;

    private RadioGroup rdGrpNaturezaTransacao;
    private RadioButton rdBtnDedito;


    private EditText edtTxtValor;
    private RadioGroup rdGrpRepeticaoTransacao;
    private RadioButton rdBtnUnicaTransacao;

    private LinearLayout linearAgrupadorRepeticaoTransacao;
    private Spinner spinnerPeriodicidade;
    private EditText editTextQtdDeRepeticao;
    private EditText editTextDataTransacao;
    private Button btnEscolherData;



    List<String> listaPeriodicidade = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        obterReferenciasComponentesUI();
        ajustarListenerRdGrpRepeticaoTransacao(rdGrpRepeticaoTransacao);
        ajustarListenerRdGrpNaturezaTransacao(rdGrpNaturezaTransacao);







        initTipoTransacao();
        initContaDaTransacao();
        configurarSpinnerPeriodicidade(spinnerPeriodicidade);

        //DAO
        //transacaoRepository = new TransacaoRepository(this);

        configurarUI_NaturezaOperacao();

        ajustarComponentesUItransacaoUnicaOuRepete();


        Calendar calendar = Calendar.getInstance();
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        editTextDataTransacao.setText(dateFormat.format(calendar.getTime()));


        configurarBotaoEscolherData();




        //Botão Salvar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();


                salvarAtualizar();
            }
        });





    }


    private void obterReferenciasComponentesUI(){

        edtTxtDescricao = ((EditText) findViewById(R.id.editTextDescricao));
        spnTipoTransacao = (Spinner) findViewById(R.id.spinnerTipoTransacao);
        spnContaDaTransacao = (Spinner) findViewById(R.id.spinnerContaDaTransacao);
        rdGrpNaturezaTransacao = (RadioGroup) findViewById(R.id.rdGrpNaturezaTransacao);
        rdBtnDedito = (RadioButton) findViewById(R.id.rdBtnDedito);
        edtTxtValor = (EditText) findViewById(R.id.editTextValorTransacao);
        rdGrpRepeticaoTransacao = (RadioGroup) findViewById(R.id.rdGrpRepeticaoTransacao);
        rdBtnUnicaTransacao = (RadioButton) findViewById(R.id.rdBtnUnicaTransacao);
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

                    //Snackbar.make(group, "Clicou em " + checkedId, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    configurarUI_NaturezaOperacao();



                }

                if (checkedId == R.id.rdBtnCredito) {
                    //Snackbar.make(group, "Clicou em " + checkedId, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    configurarUI_NaturezaOperacao();

                }



            }
        });

    }

    private void ajustarListenerRdGrpRepeticaoTransacao(RadioGroup radioGrp){
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdBtnUnicaTransacao) {
                    //Snackbar.make(group, "Clicou em " + checkedId, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    ajustarComponentesUItransacaoUnicaOuRepete();

                }

                if (checkedId == R.id.rdBtnRepeticaoTransacao) {
                    //Snackbar.make(group, "Clicou em " + checkedId, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    ajustarComponentesUItransacaoUnicaOuRepete();

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

        listaPeriodicidade.add("Diária");
        listaPeriodicidade.add("Semanal");
        listaPeriodicidade.add("Mensal");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaPeriodicidade);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriodicidade.setAdapter(dataAdapter);
    }



    private void configurarUI_NaturezaOperacao(){
        if (rdBtnDedito.isChecked()){ //Débito
            edtTxtValor.setTextColor( getResources().getColor(R.color.vermelho));
        }
        else{  //Crédito
            edtTxtValor.setTextColor( getResources().getColor(R.color.verde));
        }

    }




    private void salvarAtualizar(){
        String descricao = edtTxtDescricao.getText().toString();
        String tipoDeTransacao = spnTipoTransacao.getSelectedItem().toString();
        String contaDaTransacao = spnContaDaTransacao.getSelectedItem().toString();
        boolean naturezaoperacaoDebito = rdBtnDedito.isChecked();
        String valor = edtTxtValor.getText().toString();
        boolean unicaTransacao = rdBtnUnicaTransacao.isChecked();
        String periodicidade = null;
        String qtdDeRepeticao = null;
        String dataTransacao = null;
        if (unicaTransacao == false){ //Repete
            //Se repete então temos informações em campos adicionais para salvar

            periodicidade = spinnerPeriodicidade.getSelectedItem().toString();
            qtdDeRepeticao = editTextQtdDeRepeticao.getText().toString();
            dataTransacao = editTextDataTransacao.getText().toString();

        }




        //Se os campos não estiverem preenchidos
        if(validaTextoCampo(descricao) && validaTextoCampo(tipoDeTransacao) && validaTextoCampo(contaDaTransacao) && validaTextoCampo(valor)) {
            //Se os campos da seção superior estiverem ok verifica se precisa valdiar os campos da seção inferior (seção de repetiçao)


            if (unicaTransacao == true) { //Única transação, já está td OK, basta salvar

                //Salvar dados incluindo os dados apenas da seção "unica transacao" - não há dados para a repetição - SALVAR

                /*
                if (transacao == null)
                    transacao = new Transacao();

                transacao.setDescricao(descricao);
                transacao.setTipoTransacao(tipoDeTransacao);
                Conta conta = contas.get((int) spnContaDaTransacao.getSelectedItemId());
                transacao.setIdConta(conta.getId());
                String natureza =  (naturezaoperacaoDebito == true) ? "D" : "C"; //Verifica se operação é débito ou crédito
                transacao.setNaturezaTransacao(natureza);
                Double valorParaDebitoCreditoComSinal = Double.parseDouble(valor);
                if(naturezaoperacaoDebito == true){
                    valorParaDebitoCreditoComSinal = valorParaDebitoCreditoComSinal * -1; //inverte o sinal para negativo para a operação de débito, lembrando que no aplicativo o valor digitado sempre é positivo sem sinal, o que muda é a operação ser débito ou crédito
                }
                transacao.setValor(valorParaDebitoCreditoComSinal);
                transacao.setUnica(true);*/

                //popula a transação com os dados da seção superior da tela
                preencherDadosDaTransacaoSecaoSuperior(descricao, tipoDeTransacao, naturezaoperacaoDebito, valor);


                //DAO
                TransacaoRepository transacaoRepository = new TransacaoRepository(this);
                transacaoRepository.salvarAtualizarConta(transacao);

                //Intent de resultado
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();



            }else { //Repete
                //verificar os campos da seção repetição
                if(validaTextoCampo(periodicidade) && validaTextoCampo(qtdDeRepeticao) && validaTextoCampo(dataTransacao)) {
                    //Tudo OK, deve SALVAR para o caso em que há repetição
                    //Salvar dados incluindo os dados da seção de repetição - SALVAR

                    //popula a transação com os dados da seção superior da tela
                    preencherDadosDaTransacaoSecaoSuperior(descricao, tipoDeTransacao, naturezaoperacaoDebito, valor);

                    //Preenche/acrescenta também os daodos de repetição de transação
                    transacao.setPeriodicidade(periodicidade);
                    transacao.setQtdRepeticoes(Long.parseLong(qtdDeRepeticao));
                    transacao.setDataLancamento(dataTransacao);


                    //DAO
                    TransacaoRepository transacaoRepository = new TransacaoRepository(this);
                    transacaoRepository.salvarAtualizarConta(transacao);

                    //Intent de resultado
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();



                }
                else{ //campos da seção Repete ainda não estão ok
                    showSnackBar(edtTxtDescricao, getResources().getString(R.string.preencher_todos_campos_corretamente));
                }

            }


        }
        else{ //os campos da seção superior não estão devidamente preenchidos
            showSnackBar(edtTxtDescricao, getResources().getString(R.string.preencher_todos_campos_corretamente));

        }



    }

    private boolean validaTextoCampo(String valor){
        if(valor == null || valor.isEmpty()){
            return false;
        }
        return true;
    }



    private void preencherDadosDaTransacaoSecaoSuperior(String descricao, String tipoDeTransacao, boolean naturezaoperacaoDebito, String valor){
        if (transacao == null)
            transacao = new Transacao();

        transacao.setDescricao(descricao);
        transacao.setTipoTransacao(tipoDeTransacao);
        Conta conta = contas.get((int) spnContaDaTransacao.getSelectedItemId());
        transacao.setIdConta(conta.getId());
        String natureza =  (naturezaoperacaoDebito == true) ? "D" : "C"; //Verifica se operação é débito ou crédito
        transacao.setNaturezaTransacao(natureza);
        Double valorParaDebitoCreditoComSinal = Double.parseDouble(valor);
        if(naturezaoperacaoDebito == true){
            valorParaDebitoCreditoComSinal = valorParaDebitoCreditoComSinal * -1; //inverte o sinal para negativo para a operação de débito, lembrando que no aplicativo o valor digitado sempre é positivo sem sinal, o que muda é a operação ser débito ou crédito
        }
        transacao.setValor(valorParaDebitoCreditoComSinal);
        transacao.setUnica(true);
    }



    private void ajustarComponentesUItransacaoUnicaOuRepete(){
        if (rdBtnUnicaTransacao.isChecked()){ //Única

            linearAgrupadorRepeticaoTransacao.setVisibility(View.GONE);
        }
        else{  //Repete

            linearAgrupadorRepeticaoTransacao.setVisibility(View.VISIBLE);
        }
    }



    private void showSnackBar(String msg) {
        CoordinatorLayout coordinatorlayout= (CoordinatorLayout)findViewById(R.id.coordlayout);
        Snackbar.make(coordinatorlayout, msg,
                Snackbar.LENGTH_LONG)
                .show();
    }


    private void showSnackBar(View view, String msg) {
        Snackbar.make(view, msg,
                Snackbar.LENGTH_LONG)
                .show();
    }


    /**
     * To receive a callback when the user sets the date.
     * @param view
     * @param year
     * @param month
     * @param day
     */
    @Override //Método de callback da escolha da data
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }


    /**
     * To set date on TextView
     * @param calendar
     */
    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        editTextDataTransacao.setText(dateFormat.format(calendar.getTime()));
    }


    /**
     * This callback method, call DatePickerFragment class,
     * DatePickerFragment class returns calendar view.
     * @param view
     */
    public void showDatePicker(View view){

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date picker");
    }


    private void configurarBotaoEscolherData(){
        btnEscolherData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker(btnEscolherData);
            }
        });
    }








}
