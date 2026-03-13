package com.deloitte.pecaja.api.service;

import java.util.ArrayList;
import java.util.List;

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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.deloitte.pecaja.api.email.EmailService;
import com.deloitte.pecaja.api.model.Cliente;
import com.deloitte.pecaja.api.repository.ClienteRepository;
import com.deloitte.pecaja.api.validation.ClienteValidation;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ClienteValidation> validations = new ArrayList<>();

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void deveSalvarClienteEEnviarEmail() {
        
        Cliente cliente = new Cliente();
        cliente.setNome("Ian Almeida");
        cliente.setEmail("ian@teste.com");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        
        Cliente salvo = clienteService.criarCliente(cliente);

        
        assertNotNull(salvo);
        assertEquals("Ian Almeida", salvo.getNome());
        assertEquals("ian@teste.com", salvo.getEmail());
        
        verify(clienteRepository, times(1)).save(cliente);
        verify(emailService, times(1)).enviarEmailBoasVindas(cliente);
    }
}