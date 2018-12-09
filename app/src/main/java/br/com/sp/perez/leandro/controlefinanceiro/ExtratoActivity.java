package br.com.sp.perez.leandro.controlefinanceiro;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class ExtratoActivity extends AppCompatActivity {


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

}
