package com.desafio.precadastro.controller;

import com.desafio.precadastro.model.PessoaJuridica;
import com.desafio.precadastro.repository.PessoaJuridicaService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class PessoaJuridicaControllerTest {

    @Mock
    private PessoaJuridicaService pessoaJuridicaService;

    @InjectMocks
    private PessoaJuridicaController pessoaJuridicaController;

    @Mock
    private BindingResult bindingResult;

    private static PessoaJuridica pjTeste;

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
    }

    @Test
    void testGetAllPessoaJuridica() {
        // Preparar dados para o mock
        List<PessoaJuridica> pessoaFisicaList = new ArrayList<>();
        pessoaFisicaList.add(pjTeste);

        // Preparar o mock do pessoaFisicaService para retornar dados mockados
        Mockito.when(pessoaJuridicaService.getAllPessoasJuridicas()).thenReturn(pessoaFisicaList);

        // Chamada ao método alvo do teste
        List<PessoaJuridica> result = pessoaJuridicaController.getAllPessoaJuridica();

        // Checar resultado
        assertEquals(pessoaFisicaList, result);
    }

    @Test
    void testGetPessoaJuridica_Success() {
        // Preparar mock para retonar PessoaFisica
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

        // Mock para o pessoaFisicaService
        Mockito.when(pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCnpj())).thenReturn(null);


        // Chamar o método para criação da instância
        ResponseEntity<String> result = pessoaJuridicaController.criaPessoaJuridica(pjTeste, bindingResult);

        // Checar resultados
        assertEquals("Pessoa jurídica criada com sucesso", result.getBody());
        assertEquals(HttpStatusCode.valueOf(201), result.getStatusCode());
    }

    @Test
    void testCriarPessoaJuridica_ValidationError() {
        // Mock para o pessoaFisicaService
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

        // Mock para pessoaFisicaService
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

        // Mock para pessoaFisicaService
        Mockito.when(pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCnpj())).thenReturn(null);

        // Chamar o método alvo do teste
        ResponseEntity<String> result = pessoaJuridicaController.putPessoaJuridica(pjTeste, pjTeste.getCnpj(), bindingResult);

        // Checar resultados
        assertEquals("Pessoa jurídica ainda não cadastrada. Não incluir novo registro", result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testDeletePessoaFisica_NotFound() {
        // Mock para pessoaFisicaService
        Mockito.when(pessoaJuridicaService.deletarPessoaJuridica(pjTeste.getCnpj())).thenReturn(false);

        ResponseEntity<String> result = pessoaJuridicaController.deletePessoaFisica(pjTeste.getCnpj());

        // Checar resultados
        assertEquals("Pessoa jurídica não encontrada", result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testDeletePessoaFisica_Success() {
        // Mock para pessoaFisicaService
        Mockito.when(pessoaJuridicaService.deletarPessoaJuridica(pjTeste.getCnpj())).thenReturn(true);

        ResponseEntity<String> result = pessoaJuridicaController.deletePessoaFisica(pjTeste.getCnpj());

        // Checar resultados
        assertEquals("Pessoa Jurídica deletada", result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
