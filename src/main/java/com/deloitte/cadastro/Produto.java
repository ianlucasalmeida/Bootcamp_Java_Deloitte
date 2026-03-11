package com.deloitte.cadastro;

public abstract class Produto {
    public int id;
    public String descricao;
    public double precoBase;

    public Produto(int id, String descricao, double precoBase) {
        this.id = id;
        this.descricao = descricao;
        this.precoBase = precoBase;
    }

    public double calcularPrecoFinal() {
        return precoBase;
    }

    // Getters e Setters mantidos públicos para o acesso direto
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public double getPrecoBase() { return precoBase; }
    public void setPrecoBase(double precoBase) { this.precoBase = precoBase; }
}