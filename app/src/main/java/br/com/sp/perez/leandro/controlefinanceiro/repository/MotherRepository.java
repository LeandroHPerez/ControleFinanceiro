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

//Classe responsável pela primeira configuração de banco de dados do app - cria TODAS as tabelas
public class MotherRepository extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = Constantes.BD_NOME;
    private static final int DATABASE_VERSION = Constantes.BD_VERSAO;



    public MotherRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //As classes repository têm a responsabilidade e forncecer o script de criação
        sqLiteDatabase.execSQL(ContaRepository.getDATABASE_CREATE_V1());      //Cria o banco
        sqLiteDatabase.execSQL(TransacaoRepository.getDATABASE_CREATE_V1()); //Cria o banco

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

}
