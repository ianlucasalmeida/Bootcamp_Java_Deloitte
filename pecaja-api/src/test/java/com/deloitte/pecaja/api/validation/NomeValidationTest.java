package com.deloitte.pecaja.api.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.deloitte.pecaja.api.model.Cliente;

class NomeValidationTest {

    private final NomeValidation validation = new NomeValidation();

    @Test
    void deveValidarNomeCorretoSemErro() {
        Cliente cliente = new Cliente();
        cliente.setNome("Ian Almeida");
        
        
        assertDoesNotThrow(() -> validation.validar(cliente));
    }

    @Test
    void deveLancarExcecaoParaNomeMuitoCurto() {
        Cliente cliente = new Cliente();
        cliente.setNome("Ia"); 
        
        
        assertThrows(RuntimeException.class, () -> validation.validar(cliente));
    }
}