package com.desafio.precadastro.service;

import com.desafio.precadastro.model.Cliente;
import com.desafio.precadastro.model.PessoaJuridica;
import org.springframework.stereotype.Service;

@Service
public class PessoaJuridicaService {
    // Armazena objetos PessoaJuridica
    private final GenericFila<PessoaJuridica> pessoasJuridicas = new GenericFila<>();

    // Retorna todos PJs
    public Cliente[] getAllPessoasJuridicas() {
        return pessoasJuridicas.getAllClientes();
    }

    // Retorna PJ por CNPJ
    public PessoaJuridica getPessoaJuridicaByCnpj(String cnpj) {
        return pessoasJuridicas.find(cnpj);
    }

    // Atualiza cadastro de PJ
    public PessoaJuridica updatePessoaJuridica(String cnpj, PessoaJuridica updatedPessoaJuridica) {
        // Atualiza PJ
        if (!pessoasJuridicas.replace(cnpj, updatedPessoaJuridica)) {
            // Caso não encontre PJ, retorne null
            return null;
        }

        return updatedPessoaJuridica;
    }

    // Salva instância de PJ
    public PessoaJuridica savePessoaJuridica(PessoaJuridica newPessoaJuridica) {
        pessoasJuridicas.enqueue(newPessoaJuridica);
        return newPessoaJuridica;
    }

    // Deleta instância de PJ
    public Boolean deletePessoaJuridica(String cnpj) {
        return pessoasJuridicas.remove(cnpj);
    }

    // Get e deleta primeira PF da fila
    public PessoaJuridica getAndDeletePessoaJuridica() {
        return pessoasJuridicas.dequeue();
    }
}
