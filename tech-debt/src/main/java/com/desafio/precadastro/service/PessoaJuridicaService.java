package com.desafio.precadastro.service;

import com.desafio.precadastro.model.Cliente;
import com.desafio.precadastro.model.PessoaJuridica;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaJuridicaService {
    // Template do AWS SQS
    private SqsTemplate sqsTemplate;

    // Nome da Queue
    public static final String QUEUE = "PessoaJuridicaQueue.fifo";

    @Autowired
    public void setSqsTemplate(SqsTemplate sqsTemplate) { this.sqsTemplate = sqsTemplate; }

    // Salva instância de PJ
    public PessoaJuridica savePessoaJuridica(PessoaJuridica newPessoaJuridica) {
        // Envia cliente para SQS
        sqsTemplate.send(to -> to.queue(QUEUE).payload(newPessoaJuridica));
        return newPessoaJuridica;
    }

    // Get e deleta primeira PF da fila
    public PessoaJuridica getAndDeletePessoaJuridica() {
        Optional<Message<PessoaJuridica>> cliente = sqsTemplate.receive(from -> from.queue(QUEUE), PessoaJuridica.class);

        // Se o mensagem contém um cliente, retorna cliente
        if (cliente.isPresent()) {
            return cliente.get().getPayload();
        }
        // Caso mensagem esteja vazia, retorna null
        return null;
    }
}
