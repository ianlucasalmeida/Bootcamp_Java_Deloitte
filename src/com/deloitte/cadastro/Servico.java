package com.deloitte.cadastro;

public class Servico extends Produto {
    public double horasEstimadas;

    public Servico(int id, String descricao, double precoBase, double horasEstimadas) {
        super(id, descricao, precoBase);
        this.horasEstimadas = horasEstimadas;
    }

    @Override
    public double calcularPrecoFinal() {
        return precoBase * 1.10; // Adiciona 10% de taxa
    }

    @Override
    public String toString() {
        return "[SERVIÇO] ID: " + id + " | Descrição: " + descricao + 
               " | Tempo Est.: " + horasEstimadas + "h" + 
               " | Preço Final: R$" + String.format("%.2f", calcularPrecoFinal());
    }

    // getters e setters para horasEstimadas
    public double getHorasEstimadas() { return horasEstimadas; }
    public void setHorasEstimadas(double horasEstimadas) { this.horasEstimadas = horasEstimadas; }
}