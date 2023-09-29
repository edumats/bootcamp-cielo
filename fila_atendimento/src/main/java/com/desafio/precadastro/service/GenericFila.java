package com.desafio.precadastro.service;

import com.desafio.precadastro.model.Cliente;

public class GenericFila<T extends Cliente>{
    // Aponta para começo da fila
    private Node<T> head;

    // Aponta para final da fila
    private Node<T> tail;

    // Definição de Node
    private static class Node<T> {
        // Aponta para objeto Cliente
        T data;

        // Aponta para próximo Node da Fila
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Adiciona Cliente à Fila
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    // Remove primeiro Cliente da Fila
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return data;
    }

    // Encontra Cliente através do cpf ou cnpj, retornando Cliente. Retorna null caso não encontre
    public T find(String cpfOrCnpj) {
        // Aponta para o Node que está sendo analizado
        Node<T> current = head;

        // Continua até o final da fila
        while (current != null) {
            // Cliente em análise
            T clienteAtual = current.data;

            // Se cliente tem o CPF ou CNPJ requerido, ele é substituído pelo novo Cliente no Node
            if (clienteAtual.getIdentity().equals(cpfOrCnpj)) {
                return clienteAtual;
            }
            // Se não for encontrado, move ponteiro para o próximo Node
            current = current.next;
        }
        return null;
    }

    // Substitui Cliente através do cpf ou cnpj, retornando true. Retorna false caso não encontre o Cliente
    public boolean replace(String cpfOrCnpj, T novoCliente) {
        // Aponta para o Node que está sendo analizado
        Node<T> current = head;

        // Continua até o final da fila
        while (current != null) {
            // Cliente em análise
            T clienteAtual = current.data;

            // Se cliente tem o CPF ou CNPJ requerido, ele é substituído pelo novo Cliente no Node
            if (clienteAtual.getIdentity().equals(cpfOrCnpj)) {
                current.data = novoCliente;
                return true;
            }
            // Se não for encontrado, move ponteiro para o próximo Node
            current = current.next;
        }
        return false;
    }

    // Busca Cliente por CPF ou CNPJ e remove Cliente da Fila
    public boolean remove(String cpfOrCnpj) {
        Node<T> current = head;
        Node<T> previous = null;

        while (current != null) {
            // Cliente em análise
            T clienteAtual = current.data;

            // Se CPF ou CNPJ bater com o Cliente atual
            if (clienteAtual.getIdentity().equals(cpfOrCnpj)) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                return true;
            }

            previous = current;
            current = current.next;
        }
        return false;
    }

    public boolean isEmpty() {
        return head == null;
    }

    // Method to get all Cliente objects from the queue
    public Cliente[] getAllClientes() {
        int size = 0;
        Node<T> current = head;

        // Count the number of Cliente objects in the queue
        while (current != null) {
            size++;
            current = current.next;
        }

        // Create an array to hold Cliente objects
        Cliente[] clientesArray = new Cliente[size];

        current = head;
        int index = 0;

        // Fill the array with Cliente objects
        while (current != null) {
                clientesArray[index++] = current.data;
            current = current.next;
        }

        return clientesArray;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            result.append(current.data);
            if (current.next != null) {
                result.append(", ");
            }
            current = current.next;
        }
        result.append("]");
        return result.toString();
    }

}

