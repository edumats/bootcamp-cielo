# Desafio 4: Technical Debt

### Identifique um débito técnico de Segurança da Informação na aplicação
Não é exigido autenticação para utilizar os métodos REST da API.

### Detalhe o débito técnico identificado, informando a criticidade e possíveis consequências
Grave, pois: 
- expõe dados dos clientes
- expõe as rotas disponíveis na API
- permite que os dados armazenados nas filas sejam modificados por qualquer usuário

### Planeje as atividades técnicas para o desenvolvimento da solução
Inclusão do Sprint Security nas dependências do projeto para implementar autenticação na API.

### Implemente a solução
Neste projeto todas as rotas da API necessitam de autenticação, inclusive na página de documentação do [Swagger](http://localhost:8080/swagger-ui/index.html).

