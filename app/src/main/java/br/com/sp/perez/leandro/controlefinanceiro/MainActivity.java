package br.com.sp.perez.leandro.controlefinanceiro;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.sp.perez.leandro.controlefinanceiro.adapter.ContasAdapter;
import br.com.sp.perez.leandro.controlefinanceiro.model.Conta;
import br.com.sp.perez.leandro.controlefinanceiro.repository.ContaRepository;
import br.com.sp.perez.leandro.controlefinanceiro.repository.MotherRepository;
import br.com.sp.perez.leandro.controlefinanceiro.repository.TransacaoRepository;

public class MainActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    private ContasAdapter contasAdapter;
    private List<Conta> contas = new ArrayList<>();

    private MotherRepository motherRepository;
    private ContaRepository contaRepository;
    private TransacaoRepository transacaoRepository;


    private TextView txtEmpty;
    private TextView txtSaldoAtualTotal;

    FloatingActionButton fab, fab1, fab2, fab3;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    View fabBGLayout;
    boolean isFABOpen=false;


    //private static final String OPERACAO_ADICIONAR_CONTA = "OPERACAO_ADICIONAR_CONTA";
    private final int REQUEST_CODE_NOVA_CONTA = 0;
    private final int REQUEST_CODE_EDITAR_CONTA = 1;
    public static final String EXTRA_CONTA = "EXTRA_CONTA";
    public static final String EXTRA_EXTRATO_RELATORIO = "EXTRA_EXTRATO_RELATORIO";

    private final int REQUEST_CODE_NOVA_TRANSACAO = 2;
    private final int REQUEST_CODE_GERAR_EXTRATO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);    //float action button Agrupador
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);  //Nova conta
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);  //Nova transação
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        ///Ajusta o clicque para float buttons
        ajustarOnClickListenerFOABS();


        txtEmpty = (TextView) findViewById(R.id.empty_view);

        txtSaldoAtualTotal = (TextView) findViewById(R.id.saldoAtualTotal);

        //DAO
        motherRepository = new MotherRepository(this);
        motherRepository.criarEstruturaBD();
        contaRepository = new ContaRepository(this);
        transacaoRepository = new TransacaoRepository(this);



        //RecyclerView e Adapter
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        //contasAdapter = new ContasAdapter(contas, this);


        contasAdapter = new ContasAdapter(contas, this);

        recyclerView.setAdapter(contasAdapter);


        updateUI();

        //Ajusta o evento de clique nos itens
        setupRecyclerView();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



////Ajusta o evento de clique nos itens
    private void setupRecyclerView() {

        //Toast.makeText(getApplicationContext(),"Entrou", Toast.LENGTH_LONG).show();


        // Ajusta o listener de clique na lista
        //recyclerView.setOnItemClickListener(clickListenerPessoas);



        /* old
        contasAdapter.setClickListener(new ContasAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast da linha clicada para teste
                //Toast.makeText(getApplicationContext(),"Clicou no item = " + position, Toast.LENGTH_LONG).show();


                Conta conta = contas.get(position);


                //Calcula o saldo atual
                if(contaRepository == null)
                    contaRepository = new ContaRepository(getApplicationContext());
                Double resultado = contaRepository.calcularSaldoAtualConta(getApplicationContext(), conta); //a conta é feita dentro do método
                if(resultado != null)
                    conta.setSaldo_atual(resultado); //caso o resultado atual seja nulo é porque o saldo atual não é afetado pelas transações (não há transações) e deverá ser exibido com o valor cadastrado




                Intent intent = new Intent(getApplicationContext(), ContaActivity.class);
                intent.putExtra(EXTRA_CONTA, conta);

                //Editar conta
                startActivityForResult(intent, REQUEST_CODE_EDITAR_CONTA);


            }
        });


        */


        contasAdapter.setClickListenerCustom(new ContasAdapter.ItemClickListenerCustom() {
            @Override
            public void onItemClickCard(int position) {

                Conta conta = contas.get(position);


                //Calcula o saldo atual
                if(contaRepository == null)
                    contaRepository = new ContaRepository(getApplicationContext());
                Double resultado = contaRepository.calcularSaldoAtualConta(getApplicationContext(), conta); //a conta é feita dentro do método
                if(resultado != null)
                    conta.setSaldo_atual(resultado); //caso o resultado atual seja nulo é porque o saldo atual não é afetado pelas transações (não há transações) e deverá ser exibido com o valor cadastrado




                Intent intent = new Intent(getApplicationContext(), ContaActivity.class);
                intent.putExtra(EXTRA_CONTA, conta);

                //Editar conta
                startActivityForResult(intent, REQUEST_CODE_EDITAR_CONTA);

            }

            @Override
            public void onItemClickBotaoGerarRelatorio(int position) {
                //Toast.makeText(getApplicationContext(),"Entrou Gerar Relatorio", Toast.LENGTH_LONG).show();


                Intent intent = new Intent(getApplicationContext(), ExtratoActivity.class);
                Conta conta = contas.get(position);

                intent.putExtra(EXTRA_EXTRATO_RELATORIO, conta);

                //Gerar Extrato - Relatório
                startActivityForResult(intent, REQUEST_CODE_GERAR_EXTRATO);
            }
        });












    }





    //Listener de clique nos itens da lista
    private AdapterView.OnItemClickListener clickListenerContas = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //pega a pessoa da posição clicada
            /*
            Pessoa pessoa = repository.consultarPessoaPorId(listaPessoas.get(position).getIdPessoa());

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            StringBuilder info = new StringBuilder();
            info.append("Nome: " + pessoa.getNome());
            info.append("\nEndereço: " + pessoa.getEndereco());
            info.append("\nCPF/CNPJ: " + pessoa.getCpfCpnj());
            info.append("\nSexo: " + pessoa.getSexo().getDescricao());
            info.append("\nProfissão: " + pessoa.getProfissao().getDescricao());
            info.append("\nDt. Nasc: " + dateFormat.format(pessoa.getDtNasc()));

            Util.mostrarMsgAlertOK(ListaPessoaActivity.this, "Info", info.toString(), TipoMsg.INFO);
            */


            //Toast da linha clicada para teste
            Toast.makeText(getApplicationContext(),"Clicou no item = " + position, Toast.LENGTH_LONG).show();


        }
    };


    private void showSnackBar(String msg) {
        CoordinatorLayout coordinatorlayout= (CoordinatorLayout)findViewById(R.id.coordlayout);
        Snackbar.make(coordinatorlayout, msg,
                Snackbar.LENGTH_LONG)
                .show();
    }




    private void updateUI() {

        contas.clear();
        contas.addAll(contaRepository.listarContas());


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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        //Nova conta
        if (requestCode == REQUEST_CODE_NOVA_CONTA) {
            if (resultCode == RESULT_OK) {
                showSnackBar(getResources().getString(R.string.conta_adicionada));

                updateUI();
            }
        }

        //Editar conta
        if (requestCode == REQUEST_CODE_EDITAR_CONTA) {
            if (resultCode == RESULT_OK) {
                showSnackBar(getResources().getString(R.string.conta_alterada));

                updateUI();
            }
        }


        //Nova transação
        if (requestCode == REQUEST_CODE_NOVA_TRANSACAO) {
            if (resultCode == RESULT_OK) {
                showSnackBar(getResources().getString(R.string.transacao_adicionada));

                updateUI();
            }
        }
    }







    private void showFABMenu(){
        isFABOpen=true;

        fab1.show();
        fab2.show();
        fab3.show();

        fab.animate().rotationBy(135);
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab.animate().rotationBy(-135);
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fab1.hide();
                    fab2.hide();
                    fab3.hide();

                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    @Override
    public void onBackPressed() { //Tratamento do voltar
        if(isFABOpen){
            closeFABMenu(); //fecha o agrupador de foat action button
        }else{
            super.onBackPressed(); //Faz a ação padrão do voltar
        }
    }



    private void ajustarOnClickListenerFOABS(){ //Ajusta o clicque para float buttons

        //fab agrupador dos demais float buttons
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */



                //Intent intent = new Intent(getApplicationContext(), ContaActivity.class);
                //startActivityForResult(intent, REQUEST_CODE_NOVA_CONTA);


                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }

            }
        });


        //Nova conta
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContaActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NOVA_CONTA);
            }
        });


        //Nova transação
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TransacaoActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NOVA_TRANSACAO);
            }
        });


        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do whatever you want when the fab1 is clicked
            }
        });



    }









}
