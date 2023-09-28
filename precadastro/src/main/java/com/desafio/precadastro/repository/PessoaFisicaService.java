package com.desafio.precadastro.repository;

import com.desafio.precadastro.model.PessoaFisica;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaFisicaService {
    // Armazena objetos PessoaFisica
    private final List<PessoaFisica> pessoasFisicas = new ArrayList<>();

    // Retorna todos PFs
    public List<PessoaFisica> getAllPessoasFisicas() {
        return pessoasFisicas;
    }

    // Retorna PF por CPF
    public PessoaFisica getPessoaFisicaByCpf(String cpf) {
        for (PessoaFisica cliente : pessoasFisicas) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    // Atualiza cadastro de PF
    public PessoaFisica updatePessoaFisica(String cpf, PessoaFisica updatedPessoaFisica) {
        // Pega index da PF por CPF
        int index = findIndexByAttribute(cpf);

        // Caso PF não seja encontrado, retorna null
        if (index == -1) {
            return null;
        }

        // Atualiza atributos
        pessoasFisicas.set(index, updatedPessoaFisica);

        return updatedPessoaFisica;
    }

    // Salva instância de PF
    public PessoaFisica savePessoaFisica(PessoaFisica newPessoaFisica) {
        pessoasFisicas.add(newPessoaFisica);
        return newPessoaFisica;
    }

    // Deleta instância de PF
    public Boolean deletePessoaFisica(String cpf) {
        int index = findIndexByAttribute(cpf);
        // Se não encontrado, retorna false
        if (index == -1) {
            return false;
        }

        // Deleta PessoaFisica
        pessoasFisicas.remove(index);

        // Retorna true se removido
        return true;
    }

    // Encontra index de PF pelo atributo CPF
    private int findIndexByAttribute(String cpf) {
        for (int i = 0; i < pessoasFisicas.size(); i++) {
            PessoaFisica pf = pessoasFisicas.get(i);
            if (pf.getCpf().equals(cpf)) {
                return i;
            }
        }
        return -1;
    }
}
