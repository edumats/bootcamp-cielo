package com.desafio.precadastro.repository;

import com.desafio.precadastro.model.PessoaFisica;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PessoaFisicaServiceTests {
    private PessoaFisicaService pessoaFisicaService;

    private static PessoaFisica pfTeste;
    private static PessoaFisica pfTeste1;

    @BeforeAll
    static void setUp() {
        pfTeste = new PessoaFisica("9682", "81408639753", "string", "test@test.com");
        pfTeste1 = new PessoaFisica("9682", "81408639754", "string", "test@test.com");
    }

    @BeforeEach
    void setService() {
        pessoaFisicaService = new PessoaFisicaService();
    }

    @Test
    void testGetAllPessoaFisica() {
        // Chama método alvo
        List<PessoaFisica> result = pessoaFisicaService.getAllPessoasFisicas();

        // Checa resultado
        assertEquals(List.of(), result);
    }

    @Test
    void testGetPessoaFisicaByCpf_Exists() {
        // Setup
        pessoaFisicaService.savePessoaFisica(pfTeste);
        pessoaFisicaService.getPessoaFisicaByCpf(pfTeste.getCpf());

        // Chama método alvo
        PessoaFisica result = pessoaFisicaService.getPessoaFisicaByCpf(pfTeste.getCpf());

        // Checa resultado
        assertNotNull(result);
    }

    @Test
    void testGetPessoaFisicaByCpf_DoesNotExist() {
        // Setup
        pessoaFisicaService.savePessoaFisica(pfTeste1);
        pessoaFisicaService.getPessoaFisicaByCpf(pfTeste.getCpf());

        // Chama método alvo
        PessoaFisica result = pessoaFisicaService.getPessoaFisicaByCpf(pfTeste.getCpf());

        // Checa resultado
        assertNull(result);
    }

    @Test
    void testUpdatePessoaFisica_Exists() {
        // Setup
        pessoaFisicaService.savePessoaFisica(pfTeste);

        // Chama método alvo
        PessoaFisica result = pessoaFisicaService.updatePessoaFisica(pfTeste.getCpf(), pfTeste1);

        // Checa resultado
        assertEquals(pfTeste1, result);
    }

    @Test
    void testUpdatePessoaFisica_DoesNotExist() {
        // Setup
        pessoaFisicaService.savePessoaFisica(pfTeste);

        // Chama método alvo para não encontrar instância armazenada
        PessoaFisica result = pessoaFisicaService.updatePessoaFisica(pfTeste1.getCpf(), pfTeste1);

        // Checa resultado
        assertNull(result);
    }

    @Test
    void testSalvaPessoaFisica() {
        // Chama método alvo
        PessoaFisica result = pessoaFisicaService.savePessoaFisica(pfTeste);

        // Checa resultado
        assertEquals(pfTeste, result);
    }

    @Test
    void testDeletePessoaFisica_Exists() {
        // Set up
        pessoaFisicaService.savePessoaFisica(pfTeste);

        // Chama método alvo
        Boolean result = pessoaFisicaService.deletePessoaFisica(pfTeste.getCpf());

        // Checa resultado
        assertTrue(result);
    }

    @Test
    void testDeletePessoaFisica_DoesNotExist() {
        // Set up
        pessoaFisicaService.savePessoaFisica(pfTeste);

        // Chama método alvo
        Boolean result = pessoaFisicaService.deletePessoaFisica(pfTeste1.getCpf());

        // Checa resultado
        assertFalse(result);
    }

}
