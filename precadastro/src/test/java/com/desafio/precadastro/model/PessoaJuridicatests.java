package com.desafio.precadastro.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PessoaJuridicatests {
    private static PessoaJuridica pj;

    @BeforeAll
    static void setUp() {
        pj = new PessoaJuridica(
                "1234",
                "12345678901",
                "string",
                "pj@pj.com",
                "70279535836406",
                "string"
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertNotNull(pj.getUuid());
        assertEquals("1234", pj.getMcc());
        assertEquals("12345678901", pj.getCpf());
        assertEquals("string", pj.getNome());
        assertEquals("pj@pj.com", pj.getEmail());
        assertEquals("70279535836406", pj.getCnpj());
        assertEquals("string", pj.getRazaoSocial());
    }

    @Test
    void testSetters() {
        pj.setMcc("4321");
        assertEquals("4321", pj.getMcc());

        pj.setCpf("98765432109");
        assertEquals("98765432109", pj.getCpf());

        pj.setNome("String");
        assertEquals("String", pj.getNome());

        pj.setEmail("pf1@pf1.com");
        assertEquals("pf1@pf1.com", pj.getEmail());

        pj.setCnpj("70279535836405");
        assertEquals("70279535836405", pj.getCnpj());

        pj.setRazaoSocial("String");
        assertEquals("String", pj.getRazaoSocial());
    }

    @Test
    void testToString() {
        String expectedString = "Pessoa Jurídica: cnpj=70279535836406, razaoSocial=string, mcc=1234, cpfContato=12345678901, nomeContato:string, email=pj@pj.com";
        assertEquals(expectedString, pj.toString());
    }
}
