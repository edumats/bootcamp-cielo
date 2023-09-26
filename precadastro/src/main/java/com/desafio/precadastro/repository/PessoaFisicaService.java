package com.desafio.precadastro.repository;

import com.desafio.precadastro.model.PessoaFisica;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaFisicaService {
    private List<PessoaFisica> storage = new ArrayList<>();
    private Long counter = 1L;

    public List<PessoaFisica> getAllClientes() {
        return storage;
    }

    public PessoaFisica salvarPessoaFisica(PessoaFisica newPessoaFisica) {
        counter++;
        storage.add(newPessoaFisica);
        return newPessoaFisica;
    }
}
