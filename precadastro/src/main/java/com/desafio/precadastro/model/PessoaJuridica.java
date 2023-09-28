package com.desafio.precadastro.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PessoaJuridica extends Cliente {
    @NotBlank(message = "Campo CNPJ obrigatório")
    @Size(min = 14, max = 14, message = "CNPJ deve ter 14 dígitos")
    @Pattern(regexp = "^[0-9]+$", message = "CNPJ deve ter somente números")
    String cnpj;

    @Size(max = 50, message = "Razão Social deve ter até 50 caracteres")
    String razaoSocial;

    public PessoaJuridica(String mcc, String cpf, String nome, String email, String cnpj, String razaoSocial) {
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
        return String.format(
                "Pessoa Jurídica: cnpj=%s, razaoSocial=%s, mcc=%s, cpfContato=%s, nomeContato:%s, email=%s",
                getCnpj(), getRazaoSocial(), getMcc(), getCpf(), getNome(), getEmail());
    }
}
