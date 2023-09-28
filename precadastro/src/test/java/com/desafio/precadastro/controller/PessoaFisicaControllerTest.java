package com.desafio.precadastro.controller;

import com.desafio.precadastro.model.PessoaFisica;
import com.desafio.precadastro.repository.PessoaFisicaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class PessoaFisicaControllerTest {

    @Mock
    private PessoaFisicaService pessoaFisicaService;

    @InjectMocks
    private PessoaFisicaController pessoaFisicaController;

    @Mock
    private BindingResult bindingResult;

    private static PessoaFisica pfTeste;

    @BeforeAll
    static void setUp() {
        pfTeste = new PessoaFisica("9682", "81408639753", "string", "test@test.com");
    }

    @Test
    void testGetAllPessoaFisica() {
        // Preparar dados para o mock
        List<PessoaFisica> pessoaFisicaList = new ArrayList<>();
        pessoaFisicaList.add(pfTeste);

        // Preparar o mock do pessoaFisicaService para retornar dados mockados
        Mockito.when(pessoaFisicaService.getAllPessoasFisicas()).thenReturn(pessoaFisicaList);

        // Chamada ao método alvo do teste
        List<PessoaFisica> result = pessoaFisicaController.getAllPessoaFisica();

        // Checar resultado
        assertEquals(pessoaFisicaList, result);
    }

    @Test
    void testGetPessoaFisica_Success() {
        // Preparar mock para retonar PessoaFisica
        Mockito.when(pessoaFisicaService.getPessoaFisicaByCpf(pfTeste.getCpf())).thenReturn(pfTeste);

        // Chamada ao método alvo do teste
        ResponseEntity<PessoaFisica> result = pessoaFisicaController.getPessoaFisica(pfTeste.getCpf());

        // Checar resultado
        assertEquals(pfTeste, result.getBody());
        assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
    }

    @Test
    void testGetPessoaFisica_ValidationError() {
        // Preparar mock para retornar null
        Mockito.when(pessoaFisicaService.getPessoaFisicaByCpf("81408639753")).thenReturn(null);

        // Chamada ao método alvo do teste
        ResponseEntity<PessoaFisica> result = pessoaFisicaController.getPessoaFisica("81408639753");

        // Checar resultado
        assertNull(result.getBody());
        assertEquals(HttpStatusCode.valueOf(404), result.getStatusCode());
    }

    @Test
    void testCriarPessoaFisica_Success() {
        // Mock para bindingResult
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        // Mock para o pessoaFisicaService
        Mockito.when(pessoaFisicaService.getPessoaFisicaByCpf(pfTeste.getCpf())).thenReturn(null);


        // Chamar o método para criação da instância
        ResponseEntity<String> result = pessoaFisicaController.criarPessoaFisica(pfTeste, bindingResult);

        // Checar resultados
        assertEquals("Pessoa física criada com sucesso", result.getBody());
        assertEquals(HttpStatusCode.valueOf(201), result.getStatusCode());
    }

    @Test
    void testCriarPessoaFisica_ValidationError() {
        // Mock para o pessoaFisicaService
        Mockito.when(pessoaFisicaService.getPessoaFisicaByCpf(pfTeste.getCpf())).thenReturn(pfTeste);

        // Mock para bindingResult não acusar erros
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        // Chamar o método alvo do teste
        ResponseEntity<String> result = pessoaFisicaController.criarPessoaFisica(pfTeste, bindingResult);

        // Checar resultados
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }

    @Test
    void testPutPessoaFisica_Success() {
        // Mock para bindingResult não acusar erros
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        // Mock para pessoaFisicaService
        Mockito.when(pessoaFisicaService.getPessoaFisicaByCpf(pfTeste.getCpf())).thenReturn(pfTeste);

        // Chamar o método alvo do teste
        ResponseEntity<String> result = pessoaFisicaController.putPessoaFisica(pfTeste, pfTeste.getCpf(), bindingResult);

        // Checar resultados
        assertEquals("Pessoa física atualizada", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testPutPessoaFisica_ValidationError() {
        // Mock para bindingResult acusar erros
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);

        // Chamar o método alvo do teste
        ResponseEntity<String> result = pessoaFisicaController.putPessoaFisica(pfTeste, pfTeste.getCpf(), bindingResult);

        // Checar resultados
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void testPutPessoaFisica_NotFound() {
        // Mock para bindingResult não acusar erros
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        // Mock para pessoaFisicaService
        Mockito.when(pessoaFisicaService.getPessoaFisicaByCpf(pfTeste.getCpf())).thenReturn(null);

        // Chamar o método alvo do teste
        ResponseEntity<String> result = pessoaFisicaController.putPessoaFisica(pfTeste, pfTeste.getCpf(), bindingResult);

        // Checar resultados
        assertEquals("Pessoa física ainda não cadastrada. Não incluir novo registro", result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testDeletePessoaFisica_NotFound() {
        // Mock para pessoaFisicaService
        Mockito.when(pessoaFisicaService.deletePessoaFisica(pfTeste.getCpf())).thenReturn(false);

        ResponseEntity<String> result = pessoaFisicaController.deletePessoaFisica(pfTeste.getCpf());

        // Checar resultados
        assertEquals("Pessoa física não encontrada", result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testDeletePessoaFisica_Success() {
        // Mock para pessoaFisicaService
        Mockito.when(pessoaFisicaService.deletePessoaFisica(pfTeste.getCpf())).thenReturn(true);

        ResponseEntity<String> result = pessoaFisicaController.deletePessoaFisica(pfTeste.getCpf());

        // Checar resultados
        assertEquals("Pessoa Física deletada", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}