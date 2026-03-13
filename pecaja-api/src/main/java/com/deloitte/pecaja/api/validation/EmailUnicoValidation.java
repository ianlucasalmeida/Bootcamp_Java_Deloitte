package com.deloitte.pecaja.api.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deloitte.pecaja.api.model.Cliente;
import com.deloitte.pecaja.api.repository.ClienteRepository;

@Component
public class EmailUnicoValidation implements ClienteValidation {

    @Autowired
    private ClienteRepository repository;

    public void validar(Cliente cliente) {
        
        if (cliente.getEmail() != null && repository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Este e-mail já está em uso no Peça Já");
        }
    }
}