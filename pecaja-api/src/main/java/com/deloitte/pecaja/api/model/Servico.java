package com.deloitte.pecaja.api.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SERVICO") // Apelido no banco de dados
public class Servico extends Produto {

    private Double horasEstimadas;

    public Servico() {
    }

    public Servico(String descricao, Double precoBase, Double horasEstimadas) {
        super(descricao, precoBase);
        this.horasEstimadas = horasEstimadas;
    }

    @Override
    public Double calcularPrecoFinal() {
        return getPrecoBase() * 1.10; // Adiciona 10% de taxa
    }

    
    public Double getHorasEstimadas() { return horasEstimadas; }
    public void setHorasEstimadas(Double horasEstimadas) { this.horasEstimadas = horasEstimadas; }
}