package com.desafio.precadastro.controller;

import com.desafio.precadastro.model.PessoaFisica;
import com.desafio.precadastro.repository.PessoaFisicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private PessoaFisicaService pessoaFisicaService;

    @GetMapping("/pf")
    public List<PessoaFisica> getAllPessoaFisica() {
        return pessoaFisicaService.getAllClientes();
    }

    @PostMapping("/pf")
    public ResponseEntity<String> criarPessoaFisica(@Valid @RequestBody PessoaFisica newPessoaFisica, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder mensagemErro = new StringBuilder("Erro de validação: ");

            bindingResult.getFieldErrors().forEach(erro -> {
                mensagemErro.append(erro.getDefaultMessage()).append(", ");
            });
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro.toString());
        }
        pessoaFisicaService.salvarPessoaFisica(newPessoaFisica);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa física criada com sucesso");
    }
}
