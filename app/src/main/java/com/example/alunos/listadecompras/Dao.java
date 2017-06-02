package com.example.alunos.listadecompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Dao {

    private BancoOpenHelper bancoOpenHelper;
    private SQLiteDatabase banco;

    public Dao(Context context){
        bancoOpenHelper = new BancoOpenHelper(context, "compras.db", null, 1);
    }
    public void abrir(){
        banco = bancoOpenHelper.getWritableDatabase();
    }
    public void fechar(){
        banco.close();
    }
    public ArrayList<Item> listarItens(){

        ArrayList<Item> itens = new ArrayList<>();
        abrir();
        Cursor cursor = banco.query("itens", null, null, null, null, null, "descricao");
        while (cursor.moveToNext()){
            Item item = new Item();
            item.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            item.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            item.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));
            boolean comprado = cursor.getInt(cursor.getColumnIndex("comprado")) > 0 ? true : false;
            item.setComprado(comprado);
            itens.add(item);
        }
        fechar();
        return itens;
    }
    public void incluirItem(Item item) {
        ContentValues values = new ContentValues();
        values.put("descricao", item.getDescricao());
        values.put("quantidade", item.getQuantidade());
        values.put("comprado", 0);
        abrir();
        banco.insert("itens", null, values);
        fechar();
    }
    public void alterarItem(Item item){
        ContentValues values = new ContentValues();
        values.put("descricao", item.getDescricao());
        values.put("quantidade", item.getQuantidade());
        values.put("comprado", item.isComprado() ? 1 : 0);
        abrir();
        banco.update("itens", values, "_id = " + item.getId(), null);
        fechar();
    }
    public void excluirItem(Item item){
        abrir();
        banco.delete("itens", "_id = " + item.getId(), null);
        fechar();

    }
    public int obterQuantidadeTotal(){
        abrir();
        Cursor cursor = banco.rawQuery("SELECT SUM(quantidade) FROM itens", null);
        cursor.moveToNext();
        int total = cursor.getInt(0);
        fechar();
        return total;


    }



}
