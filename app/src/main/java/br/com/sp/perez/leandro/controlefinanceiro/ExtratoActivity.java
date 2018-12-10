package br.com.sp.perez.leandro.controlefinanceiro;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import br.com.sp.perez.leandro.controlefinanceiro.model.TipoTransacao;
import br.com.sp.perez.leandro.controlefinanceiro.util.DatePickerFragment;

public class ExtratoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {


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

        rdBtnDedito = (RadioButton) findViewById(R.id.rdBtnDeditoExtrato);
        rdBtnCredito = (RadioButton) findViewById(R.id.rdBtnCreditoExtrato);

        lytPorTipoTransacao =  (LinearLayout) findViewById(R.id.lytPorTipoTransacao);

        spnTipoTransacao = (Spinner) findViewById(R.id.spinnerTipoTransacaoExtrato);
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

}
