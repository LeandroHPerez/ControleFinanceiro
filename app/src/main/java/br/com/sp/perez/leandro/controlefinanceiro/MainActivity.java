package br.com.sp.perez.leandro.controlefinanceiro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.sp.perez.leandro.controlefinanceiro.adapter.ContasAdapter;
import br.com.sp.perez.leandro.controlefinanceiro.model.Conta;
import br.com.sp.perez.leandro.controlefinanceiro.repository.ContaRepository;

public class MainActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    private ContasAdapter contasAdapter;
    private List<Conta> contas = new ArrayList<>();
    private ContaRepository contaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //DAO
        contaRepository = new ContaRepository(this);

        Conta c = new Conta();
        //.setId(2L);
        c.setDescricao("Descrição 1");
        c.setSaldo_inicial(1000.02);
        c.setSaldo_atual(10001.03);
        //contaRepository.salvarAtualizarConta(c);

        //Conta c2 = contaRepository.listarContas().get(0);

        //contaRepository.removerConta(contaRepository.listarContas().get(0));

        //contaRepository.removerConta(c);

        contas = contaRepository.listarContas();


        //RecyclerView e Adapter
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        //contasAdapter = new ContasAdapter(contas, this);


        contasAdapter = new ContasAdapter(contas, this);

        recyclerView.setAdapter(contasAdapter);

        //Ajuda o evento de clique
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




    private void setupRecyclerView() {

        Toast.makeText(getApplicationContext(),"Entrou", Toast.LENGTH_LONG).show();


        // Ajusta o listener de clique na lista
        //recyclerView.setOnItemClickListener(clickListenerPessoas);


        contasAdapter.setClickListener(new ContasAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast da linha clicada para teste
                Toast.makeText(getApplicationContext(),"Clicou no item = " + position, Toast.LENGTH_LONG).show();

                /*
                final Contato contato = contatos.get(position);
                Intent i = new Intent(getApplicationContext(), DetalheActivity.class);
                i.putExtra("contato", contato);
                startActivityForResult(i, 2); */
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
}
