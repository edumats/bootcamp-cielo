package com.desafio.precadastro.service;

import com.desafio.precadastro.model.Cliente;
import com.desafio.precadastro.model.PessoaFisica;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaFisicaService {
    // AWS SQS
    private SqsTemplate sqsTemplate;

    // Nome da Queue
    public static final String QUEUE = "PessoaFisicaQueue.fifo";

    @Autowired
    public void setSqsTemplate(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    // Salva instância de PF e retorna a instância
    public PessoaFisica savePessoaFisica(PessoaFisica newPessoaFisica) {
        // Envia cliente para SQS
        sqsTemplate.send(to -> to.queue(QUEUE).payload(newPessoaFisica));
        return newPessoaFisica;
    }


    // Get e deleta primeira PF da fila, retorna primeira PF da fila
    public PessoaFisica getAndDeletePessoaFisica() {
        Optional<Message<PessoaFisica>> mensagem = sqsTemplate.receive(from -> from.queue(QUEUE), PessoaFisica.class);

        // Se o mensagem contém um cliente, retorna cliente
        if (mensagem.isPresent()) {
            return mensagem.get().getPayload();
        }
        // Caso mensagem esteja vazia, retorna null
        return null;
    }
}
