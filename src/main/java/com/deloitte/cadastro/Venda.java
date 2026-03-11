package com.deloitte.cadastro;

import java.util.ArrayList;
import java.util.List;

public class Venda {
    public int id;
    public Cliente cliente; 
    public List<Produto> itens; 

    public Venda(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>(); 
    }

    public void adicionarItem(Produto produto) {
        this.itens.add(produto);
    }

    public double calcularTotal() {
        double total = 0;
        for (Produto p : itens) {
            total += p.calcularPrecoFinal(); 
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder recibo = new StringBuilder();
        recibo.append("\n=================================");
        recibo.append("\nRECIBO DE VENDA #").append(id);
        recibo.append("\nCliente: ").append(cliente.nome);
        recibo.append("\n--- Itens Adquiridos ---");
        
        for (Produto p : itens) {
            recibo.append("\n * ").append(p.descricao)
                  .append(" | R$ ").append(String.format("%.2f", p.calcularPrecoFinal()));
        }
        
        recibo.append("\n---------------------------------");
        recibo.append("\nTOTAL A PAGAR: R$ ").append(String.format("%.2f", calcularTotal()));
        recibo.append("\n=================================");
        return recibo.toString();
    }
}