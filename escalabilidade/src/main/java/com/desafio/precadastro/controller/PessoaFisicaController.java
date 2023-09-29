package com.desafio.precadastro.controller;

import com.desafio.precadastro.model.Cliente;
import com.desafio.precadastro.model.PessoaFisica;
import com.desafio.precadastro.service.PessoaFisicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pf")
public class PessoaFisicaController {
    @Autowired
    private PessoaFisicaService pessoaFisicaService;

    // Cria PF
    @PostMapping("/")
    public ResponseEntity<String> criarPessoaFisica(@Valid @RequestBody PessoaFisica newPessoaFisica,
                                                    BindingResult bindingResult) {
        // Realiza validação de dados
        if (bindingResult.hasErrors()) {
            StringBuilder mensagemErro = new StringBuilder("Erro de validação: ");

            bindingResult.getFieldErrors().forEach(erro -> {
                mensagemErro.append(erro.getDefaultMessage()).append(" ");
            });
            return new ResponseEntity<>(mensagemErro.toString(), HttpStatus.BAD_REQUEST);
        }
        pessoaFisicaService.savePessoaFisica(newPessoaFisica);
        return new ResponseEntity<>("Pessoa física criada com sucesso", HttpStatus.CREATED);
    }

    // Pega primeira PF da fila e a retorna, deletando-a no processo
    @DeleteMapping("/primeira")
    public ResponseEntity<PessoaFisica> getAndDeleteFirstPessoaFisica() {
        PessoaFisica firstPessoaFisica = pessoaFisicaService.getAndDeletePessoaFisica();

        // Caso a fila esteja vazia, retorne Not Found
        if (firstPessoaFisica == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        // Retorne status 200 com a instância encontrada
        return new ResponseEntity<>(firstPessoaFisica, HttpStatus.OK);
    }

}
