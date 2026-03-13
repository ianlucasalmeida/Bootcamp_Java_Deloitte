package com.deloitte.pecaja.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deloitte.pecaja.api.email.EmailService;
import com.deloitte.pecaja.api.model.Cliente;
import com.deloitte.pecaja.api.repository.ClienteRepository;
import com.deloitte.pecaja.api.validation.ClienteValidation;

@Service
public class ClienteService {

    @Autowired
    private List<ClienteValidation> validations; 

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    public Cliente criarCliente(Cliente cliente) {
        
        for (ClienteValidation validation : validations) {
            validation.validar(cliente);
        }
        
        Cliente clienteSalvo = clienteRepository.save(cliente);
        emailService.enviarEmailBoasVindas(clienteSalvo); 
        
        return clienteSalvo;
    }
}