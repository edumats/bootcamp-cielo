package com.desafio.precadastro.model;

public class PessoaFisica extends Cliente {
    public PessoaFisica(String mcc, String cpf, String nome, String email) {
        super(mcc, cpf, nome, email);
    }

    // Como decidi usar o CPF ou CNPJ para determinar a identidade de uma PF ou PJ
    // Criei esse método para retornar CPF em PFs e CNPJ em PJs
    @Override
    public String getIdentity() {
        return cpf;
    }

    @Override
    public String toString() {
        return String.format(
                "Pessoa Física: mcc=%s, cpf=%s, nome:%s, email=%s",
                getMcc(), getCpf(), getNome(), getEmail());
    }
}
