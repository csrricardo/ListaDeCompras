package com.example.alunos.listadecompras;

public class Item {

   private int id;
   private String descricao;
   private int quantidade;
   private boolean comprado;

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public void setQuantidade(int quantidade){

        if (quantidade > 0){
            this.quantidade = quantidade;

        }
    }

    @Override
    public String toString(){
        return descricao + "\nQuantidade: " + quantidade + (isComprado() ? " COMPRADO" : "");
    }

}
