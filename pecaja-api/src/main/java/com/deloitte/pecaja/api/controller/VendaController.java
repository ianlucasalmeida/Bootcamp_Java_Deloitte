package com.deloitte.pecaja.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.pecaja.api.model.Cliente;
import com.deloitte.pecaja.api.model.Produto;
import com.deloitte.pecaja.api.model.Venda;
import com.deloitte.pecaja.api.repository.ClienteRepository;
import com.deloitte.pecaja.api.repository.ProdutoRepository;
import com.deloitte.pecaja.api.repository.VendaRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // Padrão DTO aplicado para simplificar o payload e contornar a abstração da classe Produto
    public static class VendaRequestDTO {
        public Integer clienteId;
        public List<Integer> itensIds;
    }

    @GetMapping
    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    @PostMapping
    public Venda realizarVenda(@RequestBody VendaRequestDTO request) {
        // Hidrata a venda buscando as entidades reais no banco a partir dos IDs informados
        Cliente clienteReal = clienteRepository.findById(request.clienteId).orElseThrow();
        Venda novaVenda = new Venda(clienteReal);

        for (Integer idProduto : request.itensIds) {
            Produto produtoReal = produtoRepository.findById(idProduto).orElseThrow();
            novaVenda.adicionarItem(produtoReal);
        }

        return vendaRepository.save(novaVenda);
    }

    @DeleteMapping("/{id}")
    public void cancelarVenda(@PathVariable Integer id) {
        vendaRepository.deleteById(id);
    }
}