package com.desafio.precadastro.repository;

import com.desafio.precadastro.model.PessoaFisica;
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
    public PessoaJuridica updatedPessoaJuridica(String cnpj, PessoaJuridica updatedPessoaJuridica) {
        // Pega index da PF por CPF
        int index = encontraIndexPorAtributo(cnpj);

        // Caso PJ não seja encontrado, retorna null
        if (index == -1) {
            return null;
        }

        // Atualiza atributos
        pessoasJuridicas.set(index, updatedPessoaJuridica);

        return updatedPessoaJuridica;
    }

    // Salva instância de PJ
    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica newPessoaJuridica) {
        pessoasJuridicas.add(newPessoaJuridica);
        return newPessoaJuridica;
    }

    // Deleta instância de PJ
    public Boolean deletarPessoaJuridica(String cnpj) {
        int index = encontraIndexPorAtributo(cnpj);
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
    private int encontraIndexPorAtributo(String cnpj) {
        for (int i = 0; i < pessoasJuridicas.size(); i++) {
            PessoaJuridica pf = pessoasJuridicas.get(i);
            if (pf.getCpf().equals(cnpj)) {
                return i;
            }
        }
        return -1;
    }
}
