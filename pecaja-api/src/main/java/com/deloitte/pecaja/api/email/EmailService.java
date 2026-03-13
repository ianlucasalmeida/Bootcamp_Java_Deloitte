package com.deloitte.pecaja.api.email;

import org.springframework.stereotype.Service;

import com.deloitte.pecaja.api.model.Cliente;

@Service
public class EmailService {
    public void enviarEmailBoasVindas(Cliente cliente) {
        System.out.println("Email de boas-vindas enviado para: " + cliente.getEmail());
    }
}