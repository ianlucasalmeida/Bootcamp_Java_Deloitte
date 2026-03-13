package com.deloitte.pecaja.api.validation;

import org.springframework.stereotype.Component;

import com.deloitte.pecaja.api.model.Cliente;

@Component
public class NomeValidation implements ClienteValidation {
    public void validar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().length() < 3) {
            throw new RuntimeException("Nome inválido"); 
        }
    }
}