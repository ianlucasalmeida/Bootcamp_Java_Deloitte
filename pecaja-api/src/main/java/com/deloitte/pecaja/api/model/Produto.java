package com.deloitte.pecaja.api.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Diz ao banco para usar uma tabela só
@DiscriminatorColumn(name = "tipo_produto", discriminatorType = DiscriminatorType.STRING) // A coluna que difere peça de serviço
public abstract class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String descricao;
    private Double precoBase;

    public Produto() {
    }

    public Produto(String descricao, Double precoBase) {
        this.descricao = descricao;
        this.precoBase = precoBase;
    }

    
    public Double calcularPrecoFinal() {
        return precoBase;
    }

    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getPrecoBase() { return precoBase; }
    public void setPrecoBase(Double precoBase) { this.precoBase = precoBase; }
}