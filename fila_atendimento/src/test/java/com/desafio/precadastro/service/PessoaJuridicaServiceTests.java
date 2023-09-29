package com.desafio.precadastro.service;

import com.desafio.precadastro.model.PessoaJuridica;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PessoaJuridicaServiceTests {

    private PessoaJuridicaService pessoaJuridicaService;

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
                "29089996300",
                "string",
                "empresa@teste.com",
                "71080025169541",
                "string"
        );
    }

    @BeforeEach
    void setService() {
        pessoaJuridicaService = new PessoaJuridicaService();
    }

    @Test
    void testGetAllPessoaJuridica() {
        // Set up do resultado esperado
        GenericFila<PessoaJuridica> resultadoEperado = new GenericFila<>();
        resultadoEperado.enqueue(pjTeste);
        resultadoEperado.enqueue(pjTeste1);

        // Adicionando PJs ao service
        pessoaJuridicaService.savePessoaJuridica(pjTeste);
        pessoaJuridicaService.savePessoaJuridica(pjTeste1);

        // Checa resultado
        assertArrayEquals(
                resultadoEperado.getAllClientes(),
                pessoaJuridicaService.getAllPessoasJuridicas()
        );
    }

    @Test
    void testGetPessoaJuridicaByCnpj_Exists() {
        // Setup
        pessoaJuridicaService.savePessoaJuridica(pjTeste);
        pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCnpj());

        // Chama método alvo
        PessoaJuridica result = pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCnpj());

        // Checa resultado
        assertNotNull(result);
    }

    @Test
    void testGetPessoaJuridicaByCnpj_DoesNotExist() {
        // Setup
        pessoaJuridicaService.savePessoaJuridica(pjTeste1);
        pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCpf());

        // Chama método alvo
        PessoaJuridica result = pessoaJuridicaService.getPessoaJuridicaByCnpj(pjTeste.getCnpj());

        // Checa resultado
        assertNull(result);
    }

    @Test
    void testUpdatePessoaJuridica_Exists() {
        // Setup
        pessoaJuridicaService.savePessoaJuridica(pjTeste);

        // Chama método alvo
        PessoaJuridica result = pessoaJuridicaService.updatePessoaJuridica(pjTeste.getCnpj(), pjTeste1);

        // Checa resultado
        assertEquals(pjTeste1, result);
    }

    @Test
    void testUpdatePessoaJuridica_DoesNotExist() {
        // Setup
        pessoaJuridicaService.savePessoaJuridica(pjTeste);

        // Chama método alvo para não encontrar instância armazenada
        PessoaJuridica result = pessoaJuridicaService.updatePessoaJuridica(pjTeste1.getCpf(), pjTeste1);

        // Checa resultado
        assertNull(result);
    }

    @Test
    void testSavePessoaJuridica() {
        // Chama método alvo
        PessoaJuridica result = pessoaJuridicaService.savePessoaJuridica(pjTeste);

        // Checa resultado
        assertEquals(pjTeste, result);
    }

    @Test
    void testDeletePessoaJuridica_Exists() {
        // Set up
        pessoaJuridicaService.savePessoaJuridica(pjTeste);

        // Chama método alvo
        Boolean result = pessoaJuridicaService.deletePessoaJuridica(pjTeste.getCnpj());

        // Checa resultado
        assertTrue(result);
    }

    @Test
    void testDeletePessoaJuridica_DoesNotExist() {
        // Set up
        pessoaJuridicaService.savePessoaJuridica(pjTeste);

        // Chama método alvo
        Boolean result = pessoaJuridicaService.deletePessoaJuridica(pjTeste1.getCpf());

        // Checa resultado
        assertFalse(result);
    }

    @Test
    void testGetAndDeletePessoaJuridica_Exists() {
        // Set up
        pessoaJuridicaService.savePessoaJuridica(pjTeste);

        // Chama método alvo
        PessoaJuridica result = pessoaJuridicaService.getAndDeletePessoaJuridica();

        // Checa resultado
        assertEquals(pjTeste, result);
    }

    @Test
    void testGetAndDeletePessoaJuridica_DoesNotExist() {
        // Chama método alvo
        PessoaJuridica result = pessoaJuridicaService.getAndDeletePessoaJuridica();

        // Checa resultado
        assertNull(result);
    }
}
