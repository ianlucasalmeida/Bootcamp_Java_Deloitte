package com.deloitte.pecaja.api.controller;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.deloitte.pecaja.api.model.Cliente;
import com.deloitte.pecaja.api.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class) 
class ClienteControllerTest {

    // Na branch legada, o Controller fala direto com o Repository!
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
        clienteEntrada.setNome("Novo Cliente Peça Já");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEntrada);

        Cliente resultado = clienteController.criarCliente(clienteEntrada);
        
        assertNotNull(resultado);
        assertEquals("Novo Cliente Peça Já", resultado.getNome());
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        Integer id = 1;
        Cliente clienteAntigo = new Cliente();
        clienteAntigo.setId(id);
        clienteAntigo.setNome("Nome Antigo");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNome("Nome Novo");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteAntigo));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAntigo);

        Cliente resultado = clienteController.atualizarCliente(id, clienteAtualizado);

        assertNotNull(resultado);
        assertEquals("Nome Novo", resultado.getNome());
        
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).save(clienteAntigo);
    }

    @Test
    void deveDeletarClienteComSucesso() {
        Integer id = 1;

        clienteController.deletarCliente(id);

        verify(clienteRepository, times(1)).deleteById(id);
    }
}