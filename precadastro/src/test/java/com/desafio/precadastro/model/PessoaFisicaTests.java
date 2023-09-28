package com.desafio.precadastro.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PessoaFisicaTests {

    private static PessoaFisica pf;

    @BeforeAll
    static void setUp() {
        pf = new PessoaFisica("1234", "12345678901", "string", "pf@pf.com");
    }

    @Test
    void testConstructorAndGetters() {
        assertNotNull(pf.getUuid());
        assertEquals("1234", pf.getMcc());
        assertEquals("12345678901", pf.getCpf());
        assertEquals("string", pf.getNome());
        assertEquals("pf@pf.com", pf.getEmail());
    }

    @Test
    void testSetters() {
        pf.setMcc("4321");
        assertEquals("4321", pf.getMcc());

        pf.setCpf("98765432109");
        assertEquals("98765432109", pf.getCpf());

        pf.setNome("String");
        assertEquals("String", pf.getNome());

        pf.setEmail("pf1@pf1.com");
        assertEquals("pf1@pf1.com", pf.getEmail());
    }

    @Test
    void testToString() {
        String expectedString = "Pessoa Física: mcc=1234, cpf=12345678901, nome:string, email=pf@pf.com";
        assertEquals(expectedString, pf.toString());
    }
}
