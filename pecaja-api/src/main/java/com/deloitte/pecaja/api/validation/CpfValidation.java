package com.deloitte.pecaja.api.validation;

import org.springframework.stereotype.Component;

import com.deloitte.pecaja.api.model.Cliente;

@Component
public class CpfValidation implements ClienteValidation {
    public void validar(Cliente cliente) {
        System.out.println("Validando CPF...");
        
    }
}