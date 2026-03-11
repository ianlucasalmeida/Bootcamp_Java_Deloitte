package com.deloitte.cadastro;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class VendaTest {

    @Test
    void deveAdicionarItemNaVenda() {
        // Arrange
        Cliente cliente = new Cliente(1, "Ian Almeida", "ian@teste.com");
        Venda venda = new Venda(1, cliente); // Construtor exige ID e o Cliente
        Peca peca = new Peca(1, "Amortecedor", 200.0, "Cofap", "Y-200");

        // Act
        venda.adicionarItem(peca);

        // Assert: Verifica se o toString() da Venda incluiu a palavra "Amortecedor"
        String cupomFiscal = venda.toString();
        assertTrue(cupomFiscal.contains("Amortecedor"), "A venda deveria registrar a peça adicionada!");
    }
}