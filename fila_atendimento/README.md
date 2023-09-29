# Desafio 2: Fila de Atendimento

Esta aplicação é uma REST API construída com Spring Boot que mantêm um pré-cadastro de clientes para possibilitar uma futura oferta de produtos e serviços a esses clientes.

Nesse desafio foi criada um estrutura de dados do tipo Queue (Fila) substituindo a `ArrayList` do desafio anterior.

## Estrutura de Classes

A aplicação possui as seguintes classes de modelo, localizadas na pasta `/model`:

### Cliente

A classe base que contém os seguintes atributos:
- `uuid`: UUID gerado automaticamente
- `mcc`: Código MCC (até 4 dígitos numéricos)
- `cpf`: CPF (11 dígitos numéricos)
- `nome`: Nome do cliente (até 50 caracteres)
- `email`: Endereço de e-mail válido

### PessoaFisica

Uma subclasse de `Cliente` que herda os mesmos atributos da classe `Cliente`.

### PessoaJuridica

Uma subclasse de `Cliente` que contém os seguintes atributos adicionais:
- `cnpj`: CNPJ (14 dígitos numéricos)
- `razaoSocial`: Razão Social (até 50 caracteres)

As validações são realizadas nos atributos de cada modelo, usando `Annotations`.

## Rotas da API

As rotas da API são definidas pelos arquivos `PessoaFisicaController` e `PessoaJuridicaController`, localizados na pasta `/controller`.

A busca por um objeto é realizada pelo `cpf` no caso de `PessoaFisica` e `cnpj` no caso de `PessoaJuridica`, ao invés de `id` ou `uuid`.

### Documentação da API by Swagger

A documentação da API está disponível através do Swagger. Acesse o Swagger UI em: [Swagger UI](http://localhost:8080/swagger-ui/index.html)

## Armazenamento

Os objetos criados são armazenados em memória, em uma estrutura de dados do tipo Queue(Fila), criado especialmente para este desafio, não usando as estruturas de dados disponíveis em `java.util`.

A Queue foi criada em `src/main/java/com/desafio/precadastro/service` no arquivo `GenericFila.java`

Cada modelo `PessoaFisica` e `PessoaJuridica` possui sua própria `GenericFila`.

O processo de interação entre o controller e a `GenericFila` é intermediada por `PessoaFisicaService` e `PessoaJuridicaService`, localizadas na pasta `/service`.
Cada método corresponde a uma rota disponível no arquivo `Controller`.

## Executando a Aplicação

1. Clone este repositório: `git clone https://github.com/edumats/bootcamp-cielo.git`
2. Navegue até o diretório do projeto: `cd  bootcamp-cielo/fila_atendimento`
3. Execute o aplicativo através do arquivo `PrecadastroClientesApplication.java`

As rotas API do aplicativo estarão acessíveis em: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Testes

Os testes da aplicação estão disponíveis na pasta `src/test/java/com/desafio/precadastro`, que segue a mesma estrutura de pastas e arquivos da aplicação principal.


