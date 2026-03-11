package com.deloitte.pecaja.api.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.deloitte.pecaja.api.model.Cliente;
import com.deloitte.pecaja.api.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class) 
class ClienteControllerTest {

    @Mock 
    private ClienteRepository clienteRepository;

    @InjectMocks 
    private ClienteController clienteController;

    @Test
    void deveListarClientesComSucesso() {
        
        Cliente clienteFalso = new Cliente();
        clienteFalso.setNome("Ian Almeida");
        
        
        when(clienteRepository.findAll()).thenReturn(List.of(clienteFalso));

        
        List<Cliente> resultado = clienteController.listarClientes();

        
        assertNotNull(resultado, "A lista não deveria ser nula");
        assertEquals(1, resultado.size(), "Deveria ter retornado exatamente 1 cliente");
        assertEquals("Ian Almeida", resultado.get(0).getNome());
    }

    @Test
    void deveCriarClienteComSucesso() {
        
        Cliente clienteEntrada = new Cliente();
        clienteEntrada.setNome("Novo Cliente Deloitte");

        
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEntrada);

        
        Cliente resultado = clienteController.criarCliente(clienteEntrada);

        
        assertNotNull(resultado);
        assertEquals("Novo Cliente Deloitte", resultado.getNome());
    }
}