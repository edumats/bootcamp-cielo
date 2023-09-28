package com.desafio.precadastro.controller;

import com.desafio.precadastro.model.PessoaJuridica;
import com.desafio.precadastro.repository.PessoaJuridicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pj")
public class PessoaJuridicaController {
    @Autowired
    private PessoaJuridicaService pessoaJuridicaService;

    // GET todas PJs
    @GetMapping("/")
    public List<PessoaJuridica> getAllPessoaJuridica() {
        return pessoaJuridicaService.getAllPessoasJuridicas();
    }

    // Get uma PJ
    @GetMapping("/{cnpj}")
    public ResponseEntity<PessoaJuridica> getPessoaJuridica(@PathVariable String cnpj) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaService.getPessoaJuridicaByCnpj(cnpj);
        // Se não retornar null, retornar PJ encontrado
        if (pessoaJuridica != null) {
            return new ResponseEntity<>(pessoaJuridica, HttpStatus.OK);
        }

        // Caso retornar null, PJ não encontrado, retornar Not Found
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Cria PJ
    @PostMapping("/")
    public ResponseEntity<String> criaPessoaJuridica(@Valid @RequestBody PessoaJuridica newPessoaJuridica,
                                                     BindingResult bindingResult) {
        // Realiza validação de dados
        if (bindingResult.hasErrors()) {
            StringBuilder mensagemErro = new StringBuilder("Erro de validação: ");

            bindingResult.getFieldErrors().forEach(erro -> {
                mensagemErro.append(erro.getDefaultMessage()).append(", ");
            });
            return new ResponseEntity<>(mensagemErro.toString(), HttpStatus.BAD_REQUEST);
        }
        // Retorna null, caso PJ não seja encontrado
        PessoaJuridica pjEncontrada = pessoaJuridicaService.getPessoaJuridicaByCnpj(newPessoaJuridica.getCnpj());

        // Se retornar null, podemos criar nova PJ
        if (pjEncontrada == null) {
            pessoaJuridicaService.savePessoaJuridica(newPessoaJuridica);
            return new ResponseEntity<>("Pessoa jurídica criada com sucesso", HttpStatus.CREATED);
        }
        // Caso retorne uma PJ, nova PJ não pode ser criada
        return new ResponseEntity<>("Pessoa jurídica já está cadastrado", HttpStatus.CONFLICT);
    }

    // Atualiza PJ
    @PutMapping("/{cnpj}")
    public ResponseEntity<String> putPessoaJuridica(
            @Valid @RequestBody PessoaJuridica updatedPessoaJuridica,
            @PathVariable String cnpj,
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
        if (pessoaJuridicaService.getPessoaJuridicaByCnpj(cnpj) == null) {
            return new ResponseEntity<>(
                    "Pessoa jurídica ainda não cadastrada. Não incluir novo registro",
                    HttpStatus.NOT_FOUND);
        }

        pessoaJuridicaService.updatePessoaJuridica(cnpj, updatedPessoaJuridica);
        return new ResponseEntity<>("Pessoa física atualizada", HttpStatus.OK);
    }

    // Deleta PJ
    @DeleteMapping("/{cnpj}")
    public ResponseEntity<String> deletePessoaFisica(@PathVariable String cnpj) {
        // Se PJ é encontrado, é deletado
        if (pessoaJuridicaService.deletePessoaJuridica(cnpj)) {
            return new ResponseEntity<>("Pessoa Jurídica deletada", HttpStatus.OK);
        }
        // Caso não seja encontrada, retorna erro
        return new ResponseEntity<>("Pessoa jurídica não encontrada", HttpStatus.NOT_FOUND);
    }
}
