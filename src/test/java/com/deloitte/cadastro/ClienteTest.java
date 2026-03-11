package com.deloitte.cadastro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void deveCriarClienteComNomeEEmail() {
        // Arrange
        int idEsperado = 1;
        String nomeEsperado = "Ian Almeida";
        String emailEsperado = "ian@exemplo.pt";

        // Act
        Cliente cliente = new Cliente(idEsperado, nomeEsperado, emailEsperado);

        // Assert
        assertEquals(nomeEsperado, cliente.getNome(), "O nome do cliente não bate certo!");
        assertEquals(emailEsperado, cliente.getEmail(), "O email do cliente não bate certo!");
    }
}