package com.desafio.precadastro.controller;

import com.desafio.precadastro.model.Cliente;
import com.desafio.precadastro.model.PessoaJuridica;
import com.desafio.precadastro.service.PessoaJuridicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pj")
public class PessoaJuridicaController {
    private PessoaJuridicaService pessoaJuridicaService;

    @Autowired
    public void setPessoaJuridicaService(PessoaJuridicaService pessoaJuridicaService) {
        this.pessoaJuridicaService = pessoaJuridicaService;
    }

    // Cria PJ
    @PostMapping("/")
    public ResponseEntity<String> criaPessoaJuridica(@Valid @RequestBody PessoaJuridica newPessoaJuridica,
                                                     BindingResult bindingResult) {
        // Realiza validação de dados
        if (bindingResult.hasErrors()) {
            StringBuilder mensagemErro = new StringBuilder("Erro de validação: ");

            bindingResult.getFieldErrors().forEach(erro -> mensagemErro.append(erro.getDefaultMessage()).append(", "));
            return new ResponseEntity<>(mensagemErro.toString(), HttpStatus.BAD_REQUEST);
        }
        pessoaJuridicaService.savePessoaJuridica(newPessoaJuridica);
        return new ResponseEntity<>("Pessoa jurídica criada com sucesso", HttpStatus.CREATED);
    }

    // Pega primeira PJ da fila e a retorna, deletando-a no processo
    @DeleteMapping("/primeira")
    public ResponseEntity<PessoaJuridica> getAndDeleteFirstPessoaJuridica() {
        PessoaJuridica firstPessoaJuridica = pessoaJuridicaService.getAndDeletePessoaJuridica();

        // Caso a fila esteja vazia, retorne Not Found
        if (firstPessoaJuridica == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        // Retorne status 200 com a instância encontrada
        return new ResponseEntity<>(firstPessoaJuridica, HttpStatus.OK);
    }
}
