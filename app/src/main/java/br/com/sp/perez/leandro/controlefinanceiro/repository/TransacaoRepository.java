package br.com.sp.perez.leandro.controlefinanceiro.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.sp.perez.leandro.controlefinanceiro.model.Conta;
import br.com.sp.perez.leandro.controlefinanceiro.util.Constantes;

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

    private ContentValues getContentValuesConta(Conta conta) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DESCRICAO, conta.getDescricao());
        //contentValues.put(KEY_SALDO_INICIAL, conta.getSaldo_inicial());
        //contentValues.put(KEY_SALDO_ATUAL, conta.getSaldo_atual());

        return contentValues;
    }

    public void salvarAtualizarConta(Conta conta){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = getContentValuesConta(conta);

        if(conta.getId() != null) //Atualiza
           db.update(KEY_DATABASE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(conta.getId())});
        else                  //Salva
            db.insert(KEY_DATABASE_TABLE, null, contentValues);

        db.close();
    }


    public void removerConta(Conta conta) {
        SQLiteDatabase db = this.getWritableDatabase();

        conta.getId();

        db.delete(KEY_DATABASE_TABLE, KEY_ID + " = ?", new String[]{String.valueOf(conta.getId())});


        db.delete(KEY_DATABASE_TABLE, KEY_ID + "="
                + conta.getId(), null);

        db.close();
    }


    private void setContaFromCursor(Cursor cursor, Conta conta) {
        conta.setId             (cursor.getLong(cursor.getColumnIndex(KEY_ID)));
        conta.setDescricao      (cursor.getString(cursor.getColumnIndex(KEY_DESCRICAO)));
        //conta.setSaldo_inicial  (cursor.getDouble(cursor.getColumnIndex(KEY_SALDO_INICIAL)));
        //conta.setSaldo_atual    (cursor.getDouble(cursor.getColumnIndex(KEY_SALDO_ATUAL)));
    }


    public List<Conta> listarContas() {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Conta> contas =  new ArrayList<Conta>();

        Cursor cursor = db.query(KEY_DATABASE_TABLE, null, null, null, null, null, KEY_DESCRICAO);

        while (cursor.moveToNext()){
            Conta conta = new Conta();

            setContaFromCursor(cursor, conta);

            contas.add(conta);
        }

        cursor.close();

        db.close();

        return contas;

    }


    public Conta consultarContaPorID(int idConta){

        Conta conta = new Conta();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(KEY_DATABASE_TABLE, null, KEY_ID + " ?", new String[]{String.valueOf(idConta)}, null, null, KEY_DESCRICAO);

        if (cursor.moveToNext()){
            setContaFromCursor(cursor, conta);
        }

        cursor.close();

        db.close();

        return conta;
    }




}
