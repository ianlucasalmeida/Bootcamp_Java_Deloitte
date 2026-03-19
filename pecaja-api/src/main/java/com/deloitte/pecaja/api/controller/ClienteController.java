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

import com.deloitte.pecaja.api.model.Cliente;
import com.deloitte.pecaja.api.repository.ClienteRepository;
import com.deloitte.pecaja.api.service.ClienteService;

@CrossOrigin(origins = "*")
@RestController 
@RequestMapping("/clientes") 
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        return clienteService.criarCliente(cliente); 
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll(); 
    }

    @PutMapping("/{id}")
    public Cliente atualizarCliente(@PathVariable Integer id, @RequestBody Cliente clienteAtualizado) {
        
        Cliente clienteExistente = clienteRepository.findById(id).orElseThrow();
        
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        
        
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        
        return clienteRepository.save(clienteExistente);
    }

    @DeleteMapping("/{id}")
    public void deletarCliente(@PathVariable Integer id) {
        // Varrendo se o cliente existe, se não existir, lança uma exceção
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado para exclusão!");
        }
        
        // Se existir, aí sim nós deletado!
        clienteRepository.deleteById(id);
    }
}