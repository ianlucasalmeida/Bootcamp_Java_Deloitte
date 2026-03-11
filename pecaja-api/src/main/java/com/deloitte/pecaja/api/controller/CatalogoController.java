package com.deloitte.pecaja.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.pecaja.api.model.Peca;
import com.deloitte.pecaja.api.model.Produto;
import com.deloitte.pecaja.api.model.Servico;
import com.deloitte.pecaja.api.repository.ProdutoRepository;

@RestController
@RequestMapping("/catalogo") 
public class CatalogoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    // Lista todo o catálogo (Peças e Serviços misturados - Polimorfismo)
    @GetMapping
    public List<Produto> listarCatalogo() {
        return produtoRepository.findAll();
    }

    // Rota específica para cadastrar uma Peça
    @PostMapping("/pecas")
    public Produto cadastrarPeca(@RequestBody Peca peca) {
        return produtoRepository.save(peca);
    }

    // Rota específica para cadastrar um Serviço
    @PostMapping("/servicos")
    public Produto cadastrarServico(@RequestBody Servico servico) {
        return produtoRepository.save(servico);
    }

    // Deleta um Produto (Peça ou Serviço) através do ID passado na URL
    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Integer id) {
        produtoRepository.deleteById(id);
    }
}