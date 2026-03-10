package com.deloitte.pecaja.api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   
    @ManyToOne
    @JoinColumn(name = "cliente_id") 
    private Cliente cliente;

    
    @ManyToMany
    @JoinTable(
        name = "venda_itens", // Nome da tabela de junção
        joinColumns = @JoinColumn(name = "venda_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> itens = new ArrayList<>();

    
    public Venda() {
    }

    public Venda(Cliente cliente) {
        this.cliente = cliente;
    }

    
    public void adicionarItem(Produto produto) {
        this.itens.add(produto);
    }

    public Double calcularTotal() {
        double total = 0;
        for (Produto p : itens) {
            total += p.calcularPrecoFinal(); 
        }
        return total;
    }

    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public List<Produto> getItens() { return itens; }
    public void setItens(List<Produto> itens) { this.itens = itens; }
}