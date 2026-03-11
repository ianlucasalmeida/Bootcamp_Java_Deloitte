package com.deloitte.cadastro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ServicoTest {

    @Test
    void deveCriarServicoComSucesso() {
        // Act: (id, descricao, precoBase, horasEstimadas) 
        Servico servico = new Servico(1, "Alinhamento", 80.00, 2.0);

        // Assert
        assertEquals("Alinhamento", servico.descricao);
        assertEquals(80.00, servico.precoBase);
    }
}