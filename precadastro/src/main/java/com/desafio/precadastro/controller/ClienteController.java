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

    @GetMapping("/pf/{cpf}")
    public ResponseEntity<PessoaFisica> getPessoaFisica(@PathVariable String cpf) {
        PessoaFisica pessoaFisica = pessoaFisicaService.getClienteByCpf(cpf);
        if (pessoaFisica != null) {
            return new ResponseEntity<>(pessoaFisica, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/pf")
    public ResponseEntity<String> criarPessoaFisica(@Valid @RequestBody PessoaFisica newPessoaFisica, BindingResult bindingResult) {
        // Realiza validação de dados
        if (bindingResult.hasErrors()) {
            StringBuilder mensagemErro = new StringBuilder("Erro de validação: ");

            bindingResult.getFieldErrors().forEach(erro -> {
                mensagemErro.append(erro.getDefaultMessage()).append(", ");
            });
            return new ResponseEntity<>(mensagemErro.toString(), HttpStatus.BAD_REQUEST);
        }

        PessoaFisica pessoaFisica = pessoaFisicaService.getClienteByCpf(newPessoaFisica.getCpf());
        if (pessoaFisica == null) {
            pessoaFisicaService.salvarPessoaFisica(newPessoaFisica);
            return new ResponseEntity<>("Pessoa física criada com sucesso", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Pessoa física já está cadastrado", HttpStatus.CONFLICT);
    }

    // Atualiza Pessoa Física
    @PutMapping("/pf/{cpf}")
    public ResponseEntity<String> putPessoaFisica(
            @Valid @RequestBody PessoaFisica updatedPessoaFisica,
            @PathVariable String cpf,
            BindingResult bindingResult) {

        // Realiza validação de dados
        if (bindingResult.hasErrors()) {
            StringBuilder mensagemErro = new StringBuilder("Erro de validação: ");

            bindingResult.getFieldErrors().forEach(erro -> {
                mensagemErro.append(erro.getDefaultMessage()).append(", ");
            });
            return new ResponseEntity<>(mensagemErro.toString(), HttpStatus.BAD_REQUEST);
        }

        // Checa se cadastro já existe
        if (pessoaFisicaService.getClienteByCpf(cpf) == null) {
            return new ResponseEntity<>(
                    "Pessoa física ainda não cadastrada. Não incluir novo registro",
                    HttpStatus.NOT_FOUND);
        }

        pessoaFisicaService.updatePessoaFisica(cpf, updatedPessoaFisica);
        return new ResponseEntity<>("Pessoa física atualizada", HttpStatus.OK);
    }
}
