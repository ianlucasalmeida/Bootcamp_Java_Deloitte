package com.deloitte.pecaja.api.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PECA") // Apelido no banco de dados
public class Peca extends Produto {

    private String marca;
    private String codigoFabricante;

    public Peca() {
    }

    public Peca(String descricao, Double precoBase, String marca, String codigoFabricante) {
        super(descricao, precoBase);
        this.marca = marca;
        this.codigoFabricante = codigoFabricante;
    }

    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getCodigoFabricante() { return codigoFabricante; }
    public void setCodigoFabricante(String codigoFabricante) { this.codigoFabricante = codigoFabricante; }
}