package com.deloitte.pecaja.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.pecaja.api.model.Cliente;
import com.deloitte.pecaja.api.repository.ClienteRepository;

@RestController 
@RequestMapping("/clientes") 
public class ClienteController {

    @Autowired // O Spring injeta o banco de dados aqui automaticamente
    private ClienteRepository clienteRepository;

    // Substitui o antigo "Cadastrar Cliente"
    @PostMapping
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        
        return clienteRepository.save(cliente); 
    }

    // Substitui o antigo "Listar Clientes"
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll(); 
    }
}