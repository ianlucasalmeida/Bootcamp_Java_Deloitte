package com.deloitte.cadastro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PecaTest {

    @Test
    void deveCriarPecaComSucesso() {
        // Act: (id, descricao, precoBase, marca, codigo)
        Peca peca = new Peca(1, "Pastilha de Freio", 120.50, "Bosch", "X-100");

        // Assert: Acessando diretamente as propriedades como no Main.java
        assertEquals("Pastilha de Freio", peca.descricao);
        assertEquals(120.50, peca.precoBase);
    }
}