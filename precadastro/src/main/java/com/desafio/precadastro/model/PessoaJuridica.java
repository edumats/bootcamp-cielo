package com.desafio.precadastro.model;

public class PessoaJuridica extends Cliente {
    String cnpj;
    String razaoSocial;

    public PessoaJuridica(Integer mcc, String cpf, String nome, String email, String cnpj, String razaoSocial) {
        super(mcc, cpf, nome, email);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String toString() {
        return "PessoaJuridica{" +
                "cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", uuid=" + uuid +
                ", mcc=" + mcc +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                "} ";
    }
}
