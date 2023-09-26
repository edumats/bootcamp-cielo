package com.desafio.precadastro.model;

import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

public abstract class Cliente {
    UUID uuid = UUID.randomUUID();
    @NotNull(message = "Campo MCC é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Max(value = 9999, message = "Campo MCC deve ser entre 0 e 9999")
    Integer mcc;
    @NotBlank(message = "Campo CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O valor de CPF deve ser de 11 dígitos")
    String cpf;
    @NotBlank(message = "Campo Nome é obrigatório")
    @Size(max = 50, message = "O valor de Nome precisa ter no máximo 50 caracteres ")
    String nome;
    @NotBlank(message = "Campo E-mail é obrigatório")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$", message = "Insira um e-mail válido")
    String email;

    public Cliente(Integer mcc, String cpf, String nome, String email) {
        this.mcc = mcc;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Integer getMcc() {
        return mcc;
    }

    public void setMcc(Integer mcc) {
        this.mcc = mcc;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
