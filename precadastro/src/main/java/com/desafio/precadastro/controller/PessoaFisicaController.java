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
@RequestMapping("/api/pf")
public class PessoaFisicaController {
    @Autowired
    private PessoaFisicaService pessoaFisicaService;

    // Get todas PF
    @GetMapping("/")
    public List<PessoaFisica> getAllPessoaFisica() {
        return pessoaFisicaService.getAllPessoasFisicas();
    }

    // Get uma PF
    @GetMapping("/{cpf}")
    public ResponseEntity<PessoaFisica> getPessoaFisica(@PathVariable String cpf) {
        PessoaFisica pessoaFisica = pessoaFisicaService.getPessoaFisicaByCpf(cpf);
        // Se não retornar null, retornar PF encontrado
        if (pessoaFisica != null) {
            return new ResponseEntity<>(pessoaFisica, HttpStatus.OK);
        }
        // Caso retornar null, PF não encontrado, retornar Not Found
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

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
        // Retorna instância de PF ou null
        PessoaFisica pfEncontrada = pessoaFisicaService.getPessoaFisicaByCpf(newPessoaFisica.getCpf());

        // Se não retornar null, retornar PJ encontrado
        if (pfEncontrada == null) {
            pessoaFisicaService.salvarPessoaFisica(newPessoaFisica);
            return new ResponseEntity<>("Pessoa física criada com sucesso", HttpStatus.CREATED);
        }
        // Caso retorne uma PF, nova PF não pode ser criada
        return new ResponseEntity<>("Pessoa física já está cadastrado", HttpStatus.CONFLICT);
    }

    // Atualiza PF
    @PutMapping("/{cpf}")
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
        if (pessoaFisicaService.getPessoaFisicaByCpf(cpf) == null) {
            return new ResponseEntity<>(
                    "Pessoa física ainda não cadastrada. Não incluir novo registro",
                    HttpStatus.NOT_FOUND);
        }

        pessoaFisicaService.updatePessoaFisica(cpf, updatedPessoaFisica);
        return new ResponseEntity<>("Pessoa física atualizada", HttpStatus.OK);
    }

    // Deleta PF
    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deletePessoaFisica(@PathVariable String cpf) {
        // Se PessoaFisica é encontrado, é deletado
        if (pessoaFisicaService.deletarPessoaFisica(cpf)) {
            return new ResponseEntity<>("Pessoa Física deletada", HttpStatus.OK);
        }
        // Caso não seja encontrada, retorna erro
        return new ResponseEntity<>("Pessoa física não encontrada", HttpStatus.NOT_FOUND);
    }


}
