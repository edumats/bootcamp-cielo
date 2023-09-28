package com.desafio.precadastro.model;

public class PessoaFisica extends Cliente {
    public PessoaFisica(String mcc, String cpf, String nome, String email) {
        super(mcc, cpf, nome, email);
    }

    @Override
    public String toString() {
        return String.format(
                "Pessoa Física: mcc=%s, cpf=%s, nome:%s, email=%s",
                getMcc(), getCpf(), getNome(), getEmail());
    }
}
