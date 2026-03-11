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
import com.deloitte.pecaja.api.model.Peca;
import com.deloitte.pecaja.api.model.Venda;
import com.deloitte.pecaja.api.repository.ClienteRepository;
import com.deloitte.pecaja.api.repository.ProdutoRepository;
import com.deloitte.pecaja.api.repository.VendaRepository;

@ExtendWith(MockitoExtension.class)
class VendaControllerTest {

    
    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    
    @InjectMocks
    private VendaController vendaController;

    @Test
    void deveListarVendasComSucesso() {
        // Arrange
        Cliente cliente = new Cliente();
        Venda vendaFalsa = new Venda(cliente);
        when(vendaRepository.findAll()).thenReturn(List.of(vendaFalsa));

        // Act
        List<Venda> resultado = vendaController.listarVendas();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void deveRealizarVendaComSucesso() {
        
        Integer clienteId = 1;
        Integer produtoId = 99;

      
        VendaController.VendaRequestDTO request = new VendaController.VendaRequestDTO();
        request.clienteId = clienteId;
        request.itensIds = List.of(produtoId);

        
        Cliente clienteFalso = new Cliente();
        Peca pecaFalsa = new Peca(); 

        
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteFalso));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(pecaFalsa));

        
        Venda vendaSalva = new Venda(clienteFalso);
        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaSalva);

        
        Venda resultado = vendaController.realizarVenda(request);

        
        assertNotNull(resultado, "A venda não deveria ser nula");
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(produtoRepository, times(1)).findById(produtoId);
        verify(vendaRepository, times(1)).save(any(Venda.class));
    }

    @Test
    void deveCancelarVendaComSucesso() {
        // Act
        Integer idVenda = 1;
        vendaController.cancelarVenda(idVenda);

        // Assert
        verify(vendaRepository, times(1)).deleteById(idVenda);
    }
}