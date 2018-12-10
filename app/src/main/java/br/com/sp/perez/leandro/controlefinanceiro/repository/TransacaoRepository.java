package br.com.sp.perez.leandro.controlefinanceiro.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.sp.perez.leandro.controlefinanceiro.model.Transacao;
import br.com.sp.perez.leandro.controlefinanceiro.util.Constantes;


/* SCRIPT CRIAÇÃO DA TABELA
CREATE TABLE [TB_TRANSACOES](
        [ID] INTEGER PRIMARY KEY AUTOINCREMENT,
        [DESCRICAO] TEXT(30) NOT NULL,
        [TIPO_TRANSACAO] INTEGER,
        [ID_CONTA] INTEGER REFERENCES [TB_CONTAS]([ID]),
        [NATUREZA_TRANSACAO] TEXT(1),
        [VALOR] DOUBLE NOT NULL DEFAULT 0,
        [IS_UNICA] BOOLEAN,
        [PERIODICIDADE] TEXT DEFAULT NULL,
        [QTD_REPETICOES] INTEGER DEFAULT NULL,
        [DATA_LANCAMENTO] TEXT);
 */


public class TransacaoRepository extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = Constantes.BD_NOME;
    private static final int DATABASE_VERSION = Constantes.BD_VERSAO;
    //Colunas e tabela
    static final String KEY_DATABASE_TABLE = "TB_TRANSACOES";
    static final String KEY_ID = "ID";
    static final String KEY_DESCRICAO = "DESCRICAO";
    static final String KEY_TIPO_TRANSACAO = "TIPO_TRANSACAO";
    static final String KEY_ID_CONTA = "ID_CONTA";
    static final String KEY_NATUREZA_TRANSACAO = "NATUREZA_TRANSACAO";
    static final String KEY_VALOR = "VALOR";
    static final String KEY_IS_UNICA = "IS_UNICA";
    static final String KEY_PERIODICIDADE = "PERIODICIDADE";
    static final String KEY_QTD_REPETICOES = "QTD_REPETICOES";
    static final String KEY_DATA_LANCAMENTO = "DATA_LANCAMENTO";






    public TransacaoRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(getDATABASE_CREATE_V1()); //Cria o banco

    }




    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        if(oldVersion < 2){
            //código para atualizar da versão 1 para 2


        }

        if(oldVersion < 3){
            //código para atualizar da versão 2 para 3


        }

    }


    //Banco v1
    public static final String getDATABASE_CREATE_V1() {


        StringBuilder query = new StringBuilder();
            query.append("CREATE TABLE IF NOT EXISTS " + KEY_DATABASE_TABLE + "( ");
            query.append( KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
            query.append( KEY_DESCRICAO + " TEXT(30) NOT NULL,");
            query.append( KEY_TIPO_TRANSACAO + " INTEGER,");
            query.append( KEY_ID_CONTA + " INTEGER REFERENCES TB_CONTAS(ID),"); //FK
            query.append( KEY_NATUREZA_TRANSACAO + " TEXT(1),");
            query.append( KEY_VALOR + " DOUBLE NOT NULL DEFAULT 0,");
            query.append( KEY_IS_UNICA+ " BOOLEAN,");
            query.append( KEY_PERIODICIDADE + " TEXT,");
            query.append( KEY_QTD_REPETICOES + " INTEGER,");
            query.append( KEY_DATA_LANCAMENTO + " TEXT);");

        return query.toString();
    }




    //OPERAÇÕES

    private ContentValues getContentValuesTransacao(Transacao transacao) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DESCRICAO, transacao.getDescricao());
        contentValues.put(KEY_TIPO_TRANSACAO, transacao.getTipoTransacao());
        contentValues.put(KEY_ID_CONTA, transacao.getIdConta()); //FK
        contentValues.put(KEY_NATUREZA_TRANSACAO, transacao.getNaturezaTransacao());
        contentValues.put(KEY_VALOR, transacao.getValor());
        contentValues.put(KEY_IS_UNICA, transacao.getUnica());
        contentValues.put(KEY_PERIODICIDADE, transacao.getPeriodicidade());
        contentValues.put(KEY_QTD_REPETICOES, transacao.getQtdRepeticoes());
        contentValues.put(KEY_DATA_LANCAMENTO, transacao.getDataLancamento());

        return contentValues;
    }

    public void salvarAtualizarConta(Transacao transacao){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = getContentValuesTransacao(transacao);

        if(transacao.getId() != null) //Atualiza
           db.update(KEY_DATABASE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(transacao.getId())});
        else                  //Salva
            db.insert(KEY_DATABASE_TABLE, null, contentValues);

        db.close();
    }


    public void removerTransacao(Transacao transacao) {
        SQLiteDatabase db = this.getWritableDatabase();

        transacao.getId();

        db.delete(KEY_DATABASE_TABLE, KEY_ID + " = ?", new String[]{String.valueOf(transacao.getId())});


        db.delete(KEY_DATABASE_TABLE, KEY_ID + "="
                + transacao.getId(), null);

        db.close();
    }


    private void setTransacaoFromCursor(Cursor cursor, Transacao transacao) {
        transacao.setId                 (cursor.getLong(cursor.getColumnIndex(KEY_ID)));
        transacao.setDescricao          (cursor.getString(cursor.getColumnIndex(KEY_DESCRICAO)));
        transacao.setTipoTransacao      (cursor.getString(cursor.getColumnIndex(KEY_TIPO_TRANSACAO)));
        transacao.setIdConta            (cursor.getLong(cursor.getColumnIndex(KEY_ID_CONTA)));
        transacao.setNaturezaTransacao  (cursor.getString(cursor.getColumnIndex(KEY_NATUREZA_TRANSACAO)));
        transacao.setValor              (cursor.getDouble(cursor.getColumnIndex(KEY_VALOR)));
        transacao.setUnica              (cursor.getInt(cursor.getColumnIndex(KEY_IS_UNICA)) == 1); //tratamento para boolean
        transacao.setPeriodicidade      (cursor.getString(cursor.getColumnIndex(KEY_PERIODICIDADE)));
        transacao.setQtdRepeticoes      (cursor.getLong(cursor.getColumnIndex(KEY_QTD_REPETICOES)));
        transacao.setDataLancamento     (cursor.getString(cursor.getColumnIndex(KEY_DATA_LANCAMENTO)));
    }


    public List<Transacao> listarTransacoes() {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Transacao> transacoes =  new ArrayList<Transacao>();

        Cursor cursor = db.query(KEY_DATABASE_TABLE, null, null, null, null, null, KEY_DESCRICAO);

        while (cursor.moveToNext()){
            Transacao transacao = new Transacao();

            setTransacaoFromCursor(cursor, transacao);

            transacoes.add(transacao);
        }

        cursor.close();

        db.close();

        return transacoes;

    }


    public Transacao consultarTransacaoPorIdTransacao(int idTransacao){

        Transacao transacao = new Transacao();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(KEY_DATABASE_TABLE, null, KEY_ID + " ?", new String[]{String.valueOf(idTransacao)}, null, null, KEY_DESCRICAO);

        if (cursor.moveToNext()){
            setTransacaoFromCursor(cursor, transacao);
        }

        cursor.close();

        db.close();

        return transacao;
    }




    public List<Transacao> listarTransacoesPorIdConta(Long idConta) {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Transacao> transacoes =  new ArrayList<Transacao>();

        Cursor cursor = db.query(KEY_DATABASE_TABLE, null, KEY_ID_CONTA + " ?", new String[]{String.valueOf(idConta)}, null, null, KEY_DESCRICAO);

        while (cursor.moveToNext()){
            Transacao transacao = new Transacao();

            setTransacaoFromCursor(cursor, transacao);

            transacoes.add(transacao);
        }

        cursor.close();

        db.close();

        return transacoes;

    }




    public Double obterValorTotalTransacoesPorIdConta(Long idConta) {
        SQLiteDatabase db = this.getReadableDatabase();
        Double resultado = null;
        String stringQuery = "SELECT SUM(" + KEY_VALOR + ") FROM " + KEY_DATABASE_TABLE + " WHERE " + KEY_ID_CONTA + " = " + Long.toString(idConta);
        Cursor cursor = db.rawQuery(stringQuery, null);
        if(cursor.moveToFirst())
            resultado = cursor.getDouble(0);
        else
            resultado = null;
        cursor.close();
        db.close();

        return  resultado;
    }


    public Double obterSaldoAtualTotalDeTodasAsContas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Double resultado = null;

        String stringQuery = "SELECT (SELECT SUM(saldo_inicial) FROM tb_contas) + (SELECT SUM(valor) FROM tb_transacoes) as total";
        Cursor cursor = db.rawQuery(stringQuery, null);
        if(cursor.moveToFirst())
            resultado = cursor.getDouble(0);
        else
            resultado = null;
        cursor.close();
        db.close();

        return  resultado;
    }



    public List<Transacao> listarTransacoesDaContaPorNatureza(Long idConta, String naturezaD_ou_C) {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Transacao> transacoes =  new ArrayList<Transacao>();

        Cursor cursor = db.query(KEY_DATABASE_TABLE, null, KEY_ID_CONTA + "=?" + " AND " + KEY_NATUREZA_TRANSACAO + "=?" , new String[]{String.valueOf(idConta), naturezaD_ou_C}, null, null, KEY_DESCRICAO);

        while (cursor.moveToNext()){
            Transacao transacao = new Transacao();

            setTransacaoFromCursor(cursor, transacao);

            transacoes.add(transacao);
        }

        cursor.close();

        db.close();

        return transacoes;

    }





    public List<Transacao> listarTransacoesDaContaPorTipo(Long idConta, String tipoTransacao) {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Transacao> transacoes =  new ArrayList<Transacao>();

        Cursor cursor = db.query(KEY_DATABASE_TABLE, null, KEY_ID_CONTA + "=?" + " AND " + KEY_TIPO_TRANSACAO + "=?" , new String[]{String.valueOf(idConta), tipoTransacao}, null, null, KEY_DESCRICAO);

        while (cursor.moveToNext()){
            Transacao transacao = new Transacao();

            setTransacaoFromCursor(cursor, transacao);

            transacoes.add(transacao);
        }

        cursor.close();

        db.close();

        return transacoes;

    }




    public List<Transacao> listarTransacoesDaContaPorPeriodo(Long idConta, String periodoInicial, String periodoFinal) {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Transacao> transacoes =  new ArrayList<Transacao>();

        Cursor cursor = db.query(KEY_DATABASE_TABLE, null, KEY_ID_CONTA + "=?" + " AND " + KEY_DATA_LANCAMENTO + ">=?"  + " AND " + KEY_DATA_LANCAMENTO + "<=?" , new String[]{String.valueOf(idConta), periodoInicial, periodoFinal}, null, null, KEY_DESCRICAO);

        while (cursor.moveToNext()){
            Transacao transacao = new Transacao();

            setTransacaoFromCursor(cursor, transacao);

            transacoes.add(transacao);
        }

        cursor.close();

        db.close();

        return transacoes;

    }









}
