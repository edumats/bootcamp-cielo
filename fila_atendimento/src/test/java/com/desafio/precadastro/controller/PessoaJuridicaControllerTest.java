package com.desafio.precadastro.controller;

import com.desafio.precadastro.model.Cliente;
import com.desafio.precadastro.model.PessoaJuridica;
import com.desafio.precadastro.service.GenericFila;
import com.desafio.precadastro.service.PessoaJuridicaService;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PessoaJuridicaControllerTest {

    @Mock
    private PessoaJuridicaService pessoaJuridicaService;

    @InjectMocks
    private PessoaJuridicaController pessoaJuridicaController;

    @Mock
    private BindingResult bindingResult;

    private static PessoaJuridica pjTeste;
    private static PessoaJuridica pjTeste1;

    @BeforeAll
    static void setUp() {
        pjTeste = new PessoaJuridica(
                "3075",
                "29089996309",
                "string",
                "empresa@teste.com",
                "71080025169542",
                "string"
        );
        pjTeste1 = new PessoaJuridica(
                "3075",
                "29089996404",
                "string1",
                "empresa1@teste1.com",
                "71080025169542",
                "string1"
        );
    }

    @Test
    void testGetAllPessoaJuridica() {
        // Set up do resultado esperado
        GenericFila<Cliente> resultadoEsperado = new GenericFila<>();
        resultadoEsperado.enqueue(pjTeste);
        resultadoEsperado.enqueue(pjTeste1);

        // Preparar o mock do pessoaJuridicaService para retornar dados mockados
        Mockito.when(pessoaJuridicaService.getAllPessoasJuridicas()).thenReturn(resultadoEsperado.getAllClientes());

        // Chamada ao método alvo do teste
        Cliente[] result = pessoaJuridicaController.getAllPessoaJuridica();

        // Compara resultado
        assertArrayEquals(
                resultadoEsperado.getAllClientes(),
                result
        );
    }

    @Test
    void testGetPessoaJuridica_Success() {
        // Preparar mock para retonar PessoaJuridica
        Mockito.when(pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCnpj())).thenReturn(pjTeste);

        // Chamada ao método alvo do teste
        ResponseEntity<PessoaJuridica> result = pessoaJuridicaController.getPessoaJuridica(pjTeste.getCnpj());

        // Checar resultado
        assertEquals(pjTeste, result.getBody());
        assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
    }

    @Test
    void testGetPessoaJuridica_ValidationError() {
        // Preparar mock para retornar null
        Mockito.when(pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCnpj())).thenReturn(null);

        // Chamada ao método alvo do teste
        ResponseEntity<PessoaJuridica> result = pessoaJuridicaController.getPessoaJuridica(pjTeste.getCnpj());

        // Checar resultado
        assertNull(result.getBody());
        assertEquals(HttpStatusCode.valueOf(404), result.getStatusCode());
    }

    @Test
    void testCriarPessoaJuridica_Success() {
        // Mock para bindingResult
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        // Mock para o pessoaJuridicaService
        Mockito.when(pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCnpj())).thenReturn(null);


        // Chamar o método para criação da instância
        ResponseEntity<String> result = pessoaJuridicaController.criaPessoaJuridica(pjTeste, bindingResult);

        // Checar resultados
        assertEquals("Pessoa jurídica criada com sucesso", result.getBody());
        assertEquals(HttpStatusCode.valueOf(201), result.getStatusCode());
    }

    @Test
    void testCriarPessoaJuridica_ValidationError() {
        // Mock para o pessoaJuridicaService
        Mockito.when(pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCnpj())).thenReturn(pjTeste);

        // Mock para bindingResult não acusar erros
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        // Chamar o método alvo do teste
        ResponseEntity<String> result = pessoaJuridicaController.criaPessoaJuridica(pjTeste, bindingResult);

        // Checar resultados
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }

    @Test
    void testPutPessoaJuridica_Success() {
        // Mock para bindingResult não acusar erros
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        // Mock para pessoaJuridicaService
        Mockito.when(pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCnpj())).thenReturn(pjTeste);

        // Chamar o método alvo do teste
        ResponseEntity<String> result = pessoaJuridicaController.putPessoaJuridica(pjTeste, pjTeste.getCnpj(), bindingResult);

        // Checar resultados
        assertEquals("Pessoa física atualizada", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testPutPessoaJuridica_ValidationError() {
        // Mock para bindingResult acusar erros
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);

        // Chamar o método alvo do teste
        ResponseEntity<String> result = pessoaJuridicaController.putPessoaJuridica(pjTeste, pjTeste.getCnpj(), bindingResult);

        // Checar resultados
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void testPutPessoaJuridica_NotFound() {
        // Mock para bindingResult não acusar erros
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        // Mock para pessoaJuridicaService
        Mockito.when(pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCnpj())).thenReturn(null);

        // Chamar o método alvo do teste
        ResponseEntity<String> result = pessoaJuridicaController.putPessoaJuridica(pjTeste, pjTeste.getCnpj(), bindingResult);

        // Checar resultados
        assertEquals("Pessoa jurídica ainda não cadastrada. Não incluir novo registro", result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testDeletePessoaJuridica_NotFound() {
        // Mock para pessoaJuridicaService
        Mockito.when(pessoaJuridicaService.deletePessoaJuridica(pjTeste.getCnpj())).thenReturn(false);

        ResponseEntity<String> result = pessoaJuridicaController.deletePessoaJuridica(pjTeste.getCnpj());

        // Checar resultados
        assertEquals("Pessoa jurídica não encontrada", result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testDeletePessoaJuridica_Success() {
        // Mock para pessoaJuridicaService
        Mockito.when(pessoaJuridicaService.deletePessoaJuridica(pjTeste.getCnpj())).thenReturn(true);

        ResponseEntity<String> result = pessoaJuridicaController.deletePessoaJuridica(pjTeste.getCnpj());

        // Checar resultados
        assertEquals("Pessoa Jurídica deletada", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetAndDeletePessoaJuridica_Success() {
        // Mock para pessoaJuridicaService
        Mockito.when(pessoaJuridicaService.getAndDeletePessoaJuridica()).thenReturn(pjTeste);

        // Chama método alvo
        ResponseEntity<PessoaJuridica> result = pessoaJuridicaController.getAndDeleteFirstPessoaJuridica();

        // Checar resultados
        assertEquals(pjTeste, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    @Test
    void testGetAndDeletePessoaJuridica_Empty_Queue() {
        // Mock para pessoaJuridicaService
        Mockito.when(pessoaJuridicaService.getAndDeletePessoaJuridica()).thenReturn(null);

        // Chama método alvo
        ResponseEntity<PessoaJuridica> result = pessoaJuridicaController.getAndDeleteFirstPessoaJuridica();

        // Checar resultados
        assertNull(result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
