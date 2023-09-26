package com.desafio.precadastro.repository;

import com.desafio.precadastro.model.PessoaFisica;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PessoaFisicaService {
    // Armazena objetos PessoaFisica
    private List<PessoaFisica> pessoasFisicas = new ArrayList<>();
    private Long counter = 1L;

    public List<PessoaFisica> getAllClientes() {
        return pessoasFisicas;
    }

    // Busca PessoaFisica por UUID
    public PessoaFisica getClienteByUuid(UUID pessoaFisicaId) {
        for (PessoaFisica pessoaFisica : pessoasFisicas) {
            if (pessoaFisica.getUuid().equals(pessoaFisicaId)) {
                return pessoaFisica;
            }
        }
        return null;
    }

    // Procura PessoaFisica por CPF
    public PessoaFisica getClienteByCpf(String cpf) {
        for (PessoaFisica cliente : pessoasFisicas) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    // Atualiza cadastro de PessoaFisica
    public PessoaFisica updatePessoaFisica(String cpf, PessoaFisica updatedPessoaFisica) {
        PessoaFisica atualPessoaFisica = getClienteByCpf(cpf);
        if (atualPessoaFisica == null) {
            return null;
        }

        // Pega index da atual PessoaFisica
        int index = pessoasFisicas.indexOf(atualPessoaFisica);

        // Atualiza atributos
        pessoasFisicas.set(index, updatedPessoaFisica);

        return updatedPessoaFisica;
    }

    // Salva instância de PessoaFisica
    public PessoaFisica salvarPessoaFisica(PessoaFisica newPessoaFisica) {
        counter++;
        pessoasFisicas.add(newPessoaFisica);
        return newPessoaFisica;
    }
}
