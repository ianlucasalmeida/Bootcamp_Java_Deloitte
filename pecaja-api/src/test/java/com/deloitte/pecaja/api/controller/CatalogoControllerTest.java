package com.deloitte.pecaja.api.controller;

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
import org.mockito.junit.jupiter.MockitoExtension;

import com.deloitte.pecaja.api.model.Peca;
import com.deloitte.pecaja.api.model.Produto;
import com.deloitte.pecaja.api.model.Servico;
import com.deloitte.pecaja.api.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class CatalogoControllerTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private CatalogoController catalogoController;

    @Test
    void deveListarCatalogoComSucesso() {
        
        Peca pecaFalsa = new Peca();
        Servico servicoFalso = new Servico();
        
        
        when(produtoRepository.findAll()).thenReturn(List.of(pecaFalsa, servicoFalso));

         
        List<Produto> resultado = catalogoController.listarCatalogo();

        
        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "O catálogo deveria retornar 2 itens mistos");
    }

    @Test
    void deveCadastrarPecaComSucesso() {
        
        Peca pecaEntrada = new Peca();
        when(produtoRepository.save(any(Peca.class))).thenReturn(pecaEntrada);

        
        Produto resultado = catalogoController.cadastrarPeca(pecaEntrada);

        
        assertNotNull(resultado, "A peça salva não deveria ser nula");
    }

    @Test
    void deveCadastrarServicoComSucesso() {
        
        Servico servicoEntrada = new Servico();
        when(produtoRepository.save(any(Servico.class))).thenReturn(servicoEntrada);

        
        Produto resultado = catalogoController.cadastrarServico(servicoEntrada);

        
        assertNotNull(resultado, "O serviço salvo não deveria ser nulo");
    }

    @Test
    void deveDeletarProdutoComSucesso() {
        
        Integer idParaDeletar = 1;
        catalogoController.deletarProduto(idParaDeletar);

      
        verify(produtoRepository, times(1)).deleteById(idParaDeletar);
    }
}