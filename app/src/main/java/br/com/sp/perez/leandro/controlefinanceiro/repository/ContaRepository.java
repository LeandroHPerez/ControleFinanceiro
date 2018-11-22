package br.com.sp.perez.leandro.controlefinanceiro.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import br.com.sp.perez.leandro.controlefinanceiro.util.Constantes;

public class ContaRepository extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = Constantes.BD_NOME;
    private static final int DATABASE_VERSION = Constantes.BD_VERSAO;
    //Colunas e tabela
    static final String DATABASE_TABLE = "TB_CONTAS";
    static final String KEY_ID = "ID";
    static final String KEY_DESCRICAO = "DESCRICAO";
    static final String KEY_SALDO_INICIAL = "SALDO_INICIAL";
    static final String KEY_SALDO_ATUAL = "SALDO_ATUAL";


    public ContaRepository(Context context) {
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

        if(oldVersion < 4){
            //código para atualizar da versão 3 para 4

        }

    }


    //Banco v1
    private static final String getDATABASE_CREATE_V1() {

        StringBuilder query = new StringBuilder();
            query.append("CREATE TABLE IF NOT EXISTS TB_CONTAS( ");
            query.append( KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
            query.append( KEY_DESCRICAO + " TEXT(30) NOT NULL,");
            query.append( KEY_SALDO_INICIAL + " DOUBLE NOT NULL DEFAULT 0,");
            query.append( KEY_SALDO_ATUAL + " DOUBLE NOT NULL DEFAULT 0);");

        return query.toString();
    }




    //OPERAÇÕES

}
