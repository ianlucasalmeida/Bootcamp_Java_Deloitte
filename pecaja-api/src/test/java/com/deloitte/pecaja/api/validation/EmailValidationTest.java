package com.deloitte.pecaja.api.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.deloitte.pecaja.api.model.Cliente;

class EmailValidationTest {

    private final EmailValidation validation = new EmailValidation();

    @Test
    void deveValidarEmailCorretoSemErro() {
        Cliente cliente = new Cliente();
        cliente.setEmail("ian@teste.com");
        
        assertDoesNotThrow(() -> validation.validar(cliente));
    }

    @Test
    void deveLancarExcecaoParaEmailSemArroba() {
        Cliente cliente = new Cliente();
        cliente.setEmail("ian.teste.com"); 
        
        assertThrows(RuntimeException.class, () -> validation.validar(cliente));
    }
}