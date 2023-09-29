package com.desafio.precadastro.service;

import com.desafio.precadastro.model.Cliente;
import com.desafio.precadastro.model.PessoaFisica;
import org.springframework.stereotype.Service;

@Service
public class PessoaFisicaService {
    // Armazena objetos PessoaFisica
    private final GenericFila<PessoaFisica> pessoasFisicas = new GenericFila<>();

    // Retorna todos PFs
    public Cliente[] getAllPessoasFisicas() {
        return pessoasFisicas.getAllClientes();
    }

    // Retorna PF por CPF
    public PessoaFisica getPessoaFisicaByCpf(String cpf) {
        return pessoasFisicas.find(cpf);
    }

    // Atualiza cadastro de PF
    public PessoaFisica updatePessoaFisica(String cpf, PessoaFisica updatedPessoaFisica) {
        // Atualiza PF
        if (!pessoasFisicas.replace(cpf, updatedPessoaFisica)) {
            // Caso não encontre PJ, retorne null
            return null;
        }

        return updatedPessoaFisica;
    }

    // Salva instância de PF
    public PessoaFisica savePessoaFisica(PessoaFisica newPessoaFisica) {
        pessoasFisicas.enqueue(newPessoaFisica);
        return newPessoaFisica;
    }

    // Deleta instância de PF
    public Boolean deletePessoaFisica(String cpf) {
        return pessoasFisicas.remove(cpf);
    }

    // Get e deleta primeira PF da fila
    public PessoaFisica getAndDeletePessoaFisica() {
        return pessoasFisicas.dequeue();
    }
}
