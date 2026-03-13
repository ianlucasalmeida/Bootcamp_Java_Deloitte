package com.deloitte.pecaja.api.validation;

import org.springframework.stereotype.Component;

import com.deloitte.pecaja.api.model.Cliente;

@Component
public class EmailValidation implements ClienteValidation {
    public void validar(Cliente cliente) {
        if(cliente.getEmail() == null || !cliente.getEmail().contains("@")) {
            throw new RuntimeException("Email inválido");
        }
    }
}