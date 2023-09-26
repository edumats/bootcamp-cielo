package com.desafio.precadastro;

import com.desafio.precadastro.model.PessoaFisica;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrecadastroClientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrecadastroClientesApplication.class, args);
		PessoaFisica eduardo = new PessoaFisica("1111", "36027579862", "Eduardo", "eduardo@email.com");
		String cpf = eduardo.getCpf();
		System.out.println(cpf);
	}


}
