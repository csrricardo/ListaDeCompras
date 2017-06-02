package com.example.alunos.listadecompras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> lista = new ArrayList<>();
    private ArrayAdapter<Item> arrayAdapter;
    private int itemSendoAlterando = -1;
    private Dao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new Dao(this);
        lista = dao.listarItens();

        arrayAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_checked, lista);

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item itemTocado = lista.get(position);
                if (!itemTocado.isComprado()) {
                    itemTocado.setComprado(true);
                    dao.alterarItem(itemTocado);
                } else {
                    dao.excluirItem(itemTocado);
                }
                lista = dao.listarItens();
                arrayAdapter.clear();
                arrayAdapter.addAll(lista);
                arrayAdapter.notifyDataSetChanged();
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Item itemTocado = lista.get(position);
                EditText edtDesc = (EditText)findViewById(R.id.edtDesc);
                EditText edtQtd = (EditText)findViewById(R.id.edtQtd);
                edtDesc.setText(itemTocado.getDescricao());
                edtQtd.setText(String.valueOf(itemTocado.getQuantidade()));
                Button button = (Button)findViewById(R.id.button);
                button.setText("Alterar Item");
                itemSendoAlterando = position;
                return true;
            }
        });
    }

    public void adicionar(View view) {

        EditText edtDesc = (EditText) findViewById(R.id.edtDesc);
        EditText edtQtd = (EditText) findViewById(R.id.edtQtd);
        Item item = new Item();
        if (edtDesc.getText().toString().isEmpty()) {
            Toast.makeText(this, "INFORME UM VALOR PARA DESCRIÇÃO", Toast.LENGTH_SHORT).show();
            edtDesc.requestFocus();
            return;
        }
        item.setDescricao(edtDesc.getText().toString());

        if (edtQtd.getText().toString().isEmpty()){
            Toast.makeText(this, "INFORME UM VALOR PARA QUANTIDADE", Toast.LENGTH_SHORT).show();
            edtQtd.requestFocus();
            return;
        }
        item.setQuantidade(Integer.parseInt(edtQtd.getText().toString()));

        if (itemSendoAlterando >= 0){
            item.setComprado(lista.get(itemSendoAlterando).isComprado());
            item.setId(lista.get(itemSendoAlterando).getId());
            dao.alterarItem(item);
            itemSendoAlterando = -1;
            Button button = (Button)findViewById(R.id.button);
            button.setText("Adicionar");

        }else {
            item.setComprado(false);
            dao.incluirItem(item);
        }
        lista = dao.listarItens();
        arrayAdapter.clear();
        arrayAdapter.addAll(lista);
        arrayAdapter.notifyDataSetChanged();
        edtDesc.setText("");
        edtQtd.setText("");
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
        if (id == R.id.total_comprados) {
            int totalItens = lista.size();
            int totalComprados = 0;

            // 1a. FORMA DE PERCORRER OS ITENS DO ARRAYLIST
            //for (int i = 0; i < totalItens; i++){
              //  Item itenAtual = lista.get(i);
                //if (itenAtual.isComprado()){
                  //  totalComprados++;
                //}
            //}

            //2a. FORMA DE PERCORRER OS ITENS DO ARRAYLIST
            for (Item itemAtual : lista){
                if (itemAtual.isComprado()){
                    totalComprados++;
                }

            }
            Toast.makeText(this, "Comprados: " + totalComprados + "/" + totalItens, Toast.LENGTH_LONG ).show();

            return true;
        }else if (id == R.id.quantidade_total){
             int total = dao.obterQuantidadeTotal();
            Toast.makeText(this, "Quantidade Total: " + total, Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

