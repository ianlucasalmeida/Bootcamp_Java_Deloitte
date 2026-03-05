package com.deloitte.cadastro;

public class Peca extends Produto {
    public String marca;
    public String codigoFabricante;

    public Peca(int id, String descricao, double precoBase, String marca, String codigoFabricante) {
        super(id, descricao, precoBase); 
        this.marca = marca;
        this.codigoFabricante = codigoFabricante;
    }

    @Override
    public String toString() {
        return "[PEÇA] ID: " + id + " | Descrição: " + descricao + 
               " | Marca: " + marca + " | Código: " + codigoFabricante + 
               " | Preço: R$" + String.format("%.2f", calcularPrecoFinal());
    }

    // Getters e Setters mantidos públicos para o acesso direto

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getCodigoFabricante() { return codigoFabricante; }
    public void setCodigoFabricante(String codigoFabricante) { this.codigoFabricante = codigoFabricante; }
}