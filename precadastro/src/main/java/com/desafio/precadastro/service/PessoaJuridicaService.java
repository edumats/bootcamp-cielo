package com.desafio.precadastro.service;

import com.desafio.precadastro.model.PessoaJuridica;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaJuridicaService {
    // Armazena objetos PessoaJuridica
    private final List<PessoaJuridica> pessoasJuridicas = new ArrayList<>();

    // Retorna todos PJs
    public List<PessoaJuridica> getAllPessoasJuridicas() {
        return pessoasJuridicas;
    }

    // Retorna PJ por CNPJ
    public PessoaJuridica getPessoaJuridicaByCnpj(String cnpj) {
        for (PessoaJuridica pj : pessoasJuridicas) {
            if (pj.getCnpj().equals(cnpj)) {
                return pj;
            }
        }
        return null;
    }

    // Atualiza cadastro de PJ
    public PessoaJuridica updatePessoaJuridica(String cnpj, PessoaJuridica updatedPessoaJuridica) {
        // Pega index da PF por CPF
        int index = findIndexByAttribute(cnpj);

        // Caso PJ não seja encontrado, retorna null
        if (index == -1) {
            return null;
        }

        // Atualiza atributos
        pessoasJuridicas.set(index, updatedPessoaJuridica);

        return updatedPessoaJuridica;
    }

    // Salva instância de PJ
    public PessoaJuridica savePessoaJuridica(PessoaJuridica newPessoaJuridica) {
        pessoasJuridicas.add(newPessoaJuridica);
        return newPessoaJuridica;
    }

    // Deleta instância de PJ
    public Boolean deletePessoaJuridica(String cnpj) {
        int index = findIndexByAttribute(cnpj);
        // Se não encontrado, retorna null
        if (index == -1) {
            return false;
        }

        // Deleta PJ
        pessoasJuridicas.remove(index);

        // Retorna true se removido
        return true;
    }

    // Encontra index de PJ pelo atributo CNPJ
    private int findIndexByAttribute(String cnpj) {
        for (int i = 0; i < pessoasJuridicas.size(); i++) {
            PessoaJuridica pj = pessoasJuridicas.get(i);
            if (pj.getCnpj().equals(cnpj)) {
                return i;
            }
        }
        return -1;
    }
}
