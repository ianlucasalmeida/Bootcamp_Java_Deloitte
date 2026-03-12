package com.deloitte.pecaja.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class VendaTest {

    @Test
    void deveAdicionarItemNaVendaDaApi() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setNome("Ian Almeida");
        
        Venda venda = new Venda(cliente);
        
        Peca peca = new Peca();
        peca.setDescricao("Amortecedor");
        peca.setPrecoBase(200.0);

        // Act
        venda.adicionarItem(peca);

        // Assert
        assertNotNull(venda.getItens());
        assertEquals(1, venda.getItens().size(), "A venda do Spring deveria ter 1 item");
        assertEquals("Amortecedor", venda.getItens().get(0).getDescricao());
    }
}