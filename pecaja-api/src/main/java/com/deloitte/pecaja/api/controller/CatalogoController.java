package com.deloitte.pecaja.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.pecaja.api.model.Peca;
import com.deloitte.pecaja.api.model.Produto;
import com.deloitte.pecaja.api.model.Servico;
import com.deloitte.pecaja.api.repository.ProdutoRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/catalogo") 
public class CatalogoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    
    public static class ProdutoUpdateDTO {
        private String descricao;
        private Double precoBase;

        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public Double getPrecoBase() { return precoBase; }
        public void setPrecoBase(Double precoBase) { this.precoBase = precoBase; }
    }

    
    @GetMapping
    public List<Produto> listarCatalogo() {
        return produtoRepository.findAll();
    }

    
    @PostMapping("/pecas")
    public Produto cadastrarPeca(@RequestBody Peca peca) {
        return produtoRepository.save(peca);
    }

    
    @PostMapping("/servicos")
    public Produto cadastrarServico(@RequestBody Servico servico) {
        return produtoRepository.save(servico);
    }

    
    @PutMapping("/{id}")
    public Produto editarProduto(@PathVariable Integer id, @RequestBody ProdutoUpdateDTO dadosAtualizados) {
        return produtoRepository.findById(id).map(produtoExistente -> {
            produtoExistente.setDescricao(dadosAtualizados.getDescricao());
            produtoExistente.setPrecoBase(dadosAtualizados.getPrecoBase());
            return produtoRepository.save(produtoExistente);
        }).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    
    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Integer id) {
        produtoRepository.deleteById(id);
    }
}