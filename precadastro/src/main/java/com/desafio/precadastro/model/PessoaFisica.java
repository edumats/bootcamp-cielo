package com.desafio.precadastro.model;

public class PessoaFisica extends Cliente {
    public PessoaFisica(String mcc, String cpf, String nome, String email) {
        super(mcc, cpf, nome, email);
    }

    @Override
    public String toString() {
        return "PessoaFisica{" +
                "uuid=" + uuid +
                ", mcc='" + mcc + '\'' +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                "} " + super.toString();
    }
}
