package com.example.alunos.listadecompras;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoOpenHelper extends SQLiteOpenHelper {

    public BancoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE itens (" + "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "descricao TEXT, " +
                "quantidade INTEGER, " +
                "comprado INTEGER" + ")";
        db.execSQL(sql);

        sql = "INSERT INTO itens (descricao, quantidade, comprado) " + "VALUES ('Batata', 12, 0)";
        db.execSQL(sql);

        sql = "INSERT INTO itens (descricao, quantidade, comprado) " + "VALUES ('Cebola', 5, 0)";
        db.execSQL(sql);

        sql = "INSERT INTO itens (descricao, quantidade, comprado) " + "VALUES ('Detergente', 2, 0)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

