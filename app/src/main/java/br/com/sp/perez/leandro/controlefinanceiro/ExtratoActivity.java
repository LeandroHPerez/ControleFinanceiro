package br.com.sp.perez.leandro.controlefinanceiro;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import br.com.sp.perez.leandro.controlefinanceiro.adapter.ContasAdapter;
import br.com.sp.perez.leandro.controlefinanceiro.adapter.ExtratoAdapter;
import br.com.sp.perez.leandro.controlefinanceiro.model.Conta;
import br.com.sp.perez.leandro.controlefinanceiro.model.TipoTransacao;
import br.com.sp.perez.leandro.controlefinanceiro.model.Transacao;
import br.com.sp.perez.leandro.controlefinanceiro.repository.TransacaoRepository;
import br.com.sp.perez.leandro.controlefinanceiro.util.DatePickerFragment;

public class ExtratoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    private Conta conta;

    public static RecyclerView recyclerView;
    private ExtratoAdapter extratosAdapter;
    private List<Transacao> extratos = new ArrayList<>();

    private Spinner spnTipoTransacao;
    private RadioGroup rdGrpTipoExtrato;
    private RadioButton rdBtnTipoExtratoPorPeriodo;
    private RadioButton rdBtnTipoExtratoPorNaturezaTransacao;
    private RadioButton rdBtnTipoExtratoPorTipoTransacao;

    private LinearLayout lytPorPeriodo;
    private EditText editTextDataTransacaoInicial;
    private Button btnEscolherDataInicial;
    private EditText editTextDataTransacaoFinal;
    private Button btnEscolherDataFinal;
    private boolean dataInicial = true;
    private Button btnPesquisar;




    private LinearLayout lytPorNaturezaTransacao;
    private RadioButton rdBtnDebito;
    private RadioButton rdBtnCredito;

    private LinearLayout lytPorTipoTransacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        obterReferenciasComponentesUI();
        initTipoTransacao();
        ajustarListenerRdGrpTipoExtrato(rdGrpTipoExtrato);
        ajustarComponentesUItransacaoPorPeriodo(); //Contra a lógica de exibição dos componentes visuais iniciais
        configurarBotaoEscolherDataInicial();
        configurarBotaoEscolherDataFinal();


        ajustarClickListenerBotaoPesquisar();


        //obtém a conta para geração dos relatórios
        if(getIntent().hasExtra(MainActivity.EXTRA_EXTRATO_RELATORIO)){


            this.conta = (Conta) getIntent().getSerializableExtra(MainActivity.EXTRA_EXTRATO_RELATORIO);
            getSupportActionBar().setSubtitle("Gerar extratos da conta: " + conta.getDescricao());
        }


        //RecyclerView e Adapter
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_extrato);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        //contasAdapter = new ContasAdapter(contas, this);


        extratosAdapter = new ExtratoAdapter(extratos, this);

        recyclerView.setAdapter(extratosAdapter);


        //updateUI();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    private void obterReferenciasComponentesUI() {

        rdGrpTipoExtrato = (RadioGroup) findViewById(R.id.rdGrpTipoExtrato);
        rdBtnTipoExtratoPorPeriodo = (RadioButton) findViewById(R.id.rdBtnTipoExtratoPorPeriodo);
        rdBtnTipoExtratoPorNaturezaTransacao = (RadioButton) findViewById(R.id.rdBtnTipoExtratoPorNaturezaTransacao);
        rdBtnTipoExtratoPorTipoTransacao= (RadioButton) findViewById(R.id.rdBtnTipoExtratoPorTipoTransacao);



        lytPorPeriodo = (LinearLayout) findViewById(R.id.lytPorPeriodo);
        editTextDataTransacaoInicial = ((EditText) findViewById(R.id.editTextDataTransacaoInicial));
        btnEscolherDataInicial = (Button) findViewById(R.id.btnEscolherDataInicial);
        editTextDataTransacaoFinal = ((EditText) findViewById(R.id.editTextDataTransacaoFinal));
        btnEscolherDataFinal = (Button) findViewById(R.id.btnEscolherDataFinal);

        lytPorNaturezaTransacao =  (LinearLayout) findViewById(R.id.lytPorNaturezaTransacao);

        rdBtnDebito = (RadioButton) findViewById(R.id.rdBtnDeditoExtrato);

        rdBtnCredito = (RadioButton) findViewById(R.id.rdBtnCreditoExtrato);

        lytPorTipoTransacao =  (LinearLayout) findViewById(R.id.lytPorTipoTransacao);

        spnTipoTransacao = (Spinner) findViewById(R.id.spinnerTipoTransacaoExtrato);

        btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
    }



    private void ajustarListenerRdGrpTipoExtrato(RadioGroup radioGrp){
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdBtnTipoExtratoPorPeriodo) {
                    //Snackbar.make(group, "Clicou em " + checkedId, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    ajustarComponentesUItransacaoPorPeriodo();

                }

                if (checkedId == R.id.rdBtnTipoExtratoPorNaturezaTransacao) {
                    //Snackbar.make(group, "Clicou em " + checkedId, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    ajustarComponentesUItransacaoPorNaturezaTransacao();

                }

                if (checkedId == R.id.rdBtnTipoExtratoPorTipoTransacao) {
                    //Snackbar.make(group, "Clicou em " + checkedId, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    ajustarComponentesUIporTipoTransacao();

                }



            }
        });

    }




    private void ajustarComponentesUItransacaoPorPeriodo(){
        if (rdBtnTipoExtratoPorPeriodo.isChecked()){ //Extrato por período

            lytPorPeriodo.setVisibility(View.VISIBLE);
            lytPorNaturezaTransacao.setVisibility(View.GONE);
            lytPorTipoTransacao.setVisibility(View.GONE);
        }
        else{

            //linearAgrupadorRepeticaoTransacao.setVisibility(View.VISIBLE);
        }
    }


    private void ajustarComponentesUItransacaoPorNaturezaTransacao(){
        if (rdBtnTipoExtratoPorNaturezaTransacao.isChecked()){ //Extrato por natureza transação (Crédito ou débito)

            lytPorPeriodo.setVisibility(View.GONE);
            lytPorNaturezaTransacao.setVisibility(View.VISIBLE);
            lytPorTipoTransacao.setVisibility(View.GONE);
        }

    }



    private void ajustarComponentesUIporTipoTransacao(){
        if (rdBtnTipoExtratoPorTipoTransacao.isChecked()){ //Extrato por tipo de transação (categorias)

            lytPorPeriodo.setVisibility(View.GONE);
            lytPorNaturezaTransacao.setVisibility(View.GONE);
            lytPorTipoTransacao.setVisibility(View.VISIBLE);
        }

    }


    private void initTipoTransacao() {

        ArrayList<String> tiposTransacaoes = new ArrayList<>();

        for (TipoTransacao tipoTransacao : TipoTransacao.values()) {
            tiposTransacaoes.add(tipoTransacao.getDescricao());
        }

        ArrayAdapter adapter = new ArrayAdapter(ExtratoActivity.this, android.R.layout.simple_spinner_item, tiposTransacaoes);
        //Muda o estilo exibido para a seleção
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnTipoTransacao.setAdapter(adapter);
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

        if(dataInicial == true){
            editTextDataTransacaoInicial.setText(dateFormat.format(calendar.getTime()));
        }


        if(dataInicial == false){
            editTextDataTransacaoFinal.setText(dateFormat.format(calendar.getTime()));
        }


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



    private void configurarBotaoEscolherDataInicial(){
        btnEscolherDataInicial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dataInicial = true;
                showDatePicker(btnEscolherDataInicial);
            }
        });
    }


    private void configurarBotaoEscolherDataFinal(){
        btnEscolherDataFinal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dataInicial = false;
                showDatePicker(btnEscolherDataFinal);
            }
        });
    }




    private void ajustarClickListenerBotaoPesquisar(){
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                if(rdBtnTipoExtratoPorPeriodo.isChecked()){ //Pesquisa por período
                    pesquisaPorPeriodoTransacao();

                }else if(rdBtnTipoExtratoPorNaturezaTransacao.isChecked()){  //Pesquisa por natureza da transação
                    pesquisaPorNaturezaTransacao();

                }else if(rdBtnTipoExtratoPorTipoTransacao.isChecked()){ //Pesquisa por tipo de transação
                    pesquisaPorTipoTransacao();

                }


            }
        });
    }



    private void pesquisaPorPeriodoTransacao(){

    }

    private void pesquisaPorNaturezaTransacao(){

        //DAO
        TransacaoRepository transacaoRepository = new TransacaoRepository(this);

        extratos.clear();
        String naturezaD_ou_C = (rdBtnDebito.isChecked()) ? "D" : "C";
        extratos.addAll(transacaoRepository.listarTransacoesDaContaPorNatureza(conta.getId(), naturezaD_ou_C));


        recyclerView.getAdapter().notifyDataSetChanged();


    }

    private void pesquisaPorTipoTransacao(){

        //DAO
        TransacaoRepository transacaoRepository = new TransacaoRepository(this);

        extratos.clear();
        String naturezaD_ou_C = (rdBtnDebito.isChecked()) ? "D" : "C";
        extratos.addAll(transacaoRepository.listarTransacoesDaContaPorTipo(conta.getId(), spnTipoTransacao.getSelectedItem().toString()));


        recyclerView.getAdapter().notifyDataSetChanged();




    }


    /*
    private void updateUI() {

        extratos.clear();
        extratos.addAll(contaRepository.listarContas());


        recyclerView.getAdapter().notifyDataSetChanged();


        if (recyclerView.getAdapter().getItemCount() == 0){
            txtEmpty.setVisibility(View.VISIBLE);
            txtSaldoAtualTotal.setVisibility(View.VISIBLE);

            //txtSaldoAtualTotal.setVisibility(View.GONE);
            //Saldo atual total
            String saldoAtualTodasAsContas = getResources().getString(R.string.saldo_atual_total) + " " + "0,00";
            txtSaldoAtualTotal.setText(saldoAtualTodasAsContas); //saldo total de todas as contas

        }else{
            txtEmpty.setVisibility(View.GONE);

            //Saldo atual total
            String saldoAtualTodasAsContas = getResources().getString(R.string.saldo_atual_total) + " " + transacaoRepository.obterSaldoAtualTotalDeTodasAsContas().toString() ;
            txtSaldoAtualTotal.setText(saldoAtualTodasAsContas); //saldo total de todas as contas
        }

        */



/*
    private void configurarUI_NaturezaOperacao(){
        if (rdBtnDedito.isChecked()){ //Débito
            edtTxtValor.setTextColor( getResources().getColor(R.color.vermelho));
        }
        else{  //Crédito
            edtTxtValor.setTextColor( getResources().getColor(R.color.verde));
        }

    }*/



}
