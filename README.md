# API Bancária

API REST desenvolvida com Java e Spring Boot para praticar operações bancárias, persistência de dados e organização de aplicações em camadas.

O projeto está em desenvolvimento e tem finalidade educacional.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Maven
- H2 Database

## Funcionalidades implementadas

- Cadastro e consulta de clientes.
- Abertura de conta vinculada a um cliente.
- Consulta de conta e saldo.
- Depósitos e saques.
- Transferências entre contas.
- Registro das movimentações.
- Consulta de extrato com ordenação das movimentações mais recentes para as mais antigas.
- DTOs para entrada de dados das operações e resposta do extrato.
- Tratamento centralizado de exceções.

A implementação das operações ainda está em processo de validação.

## Regras de negócio

- Um cliente pode possuir uma conta nesta versão do projeto.
- Não é permitido cadastrar clientes com CPF duplicado.
- Contas são abertas com saldo inicial zero.
- Depósitos, saques e transferências devem ter valor positivo.
- Saques e transferências exigem saldo suficiente.
- Não é permitido transferir para a própria conta.
- Cada depósito ou saque gera uma movimentação.
- Cada transferência gera duas movimentações: uma de saída e outra de entrada.
- As alterações de saldo e o registro das movimentações participam da mesma transação.

Valores monetários são representados com `BigDecimal`.

## Organização do projeto

| Pacote | Responsabilidade |
|---|---|
| `controller` | Receber requisições HTTP e devolver respostas |
| `services` | Aplicar regras de negócio e coordenar as operações |
| `repository` | Acessar e persistir dados |
| `entities` | Representar as entidades persistidas |
| `dto` | Definir os dados recebidos e enviados pela API |
| `exceptions` | Representar e tratar erros da aplicação |

### Entidades principais

- **Cliente:** representa o titular da conta.
- **Conta:** mantém o saldo e o vínculo com o cliente.
- **Movimentacao:** registra o valor, a data, o tipo e a conta de uma operação.

Uma conta pode possuir várias movimentações. Cada movimentação pertence a uma conta.

## Endpoints

### Clientes

| Método | Rota | Descrição |
|---|---|---|
| POST | `/clientes` | Cadastrar cliente |
| GET | `/clientes` | Listar clientes |
| GET | `/clientes/{id}` | Buscar cliente pelo ID |

### Contas

| Método | Rota | Descrição |
|---|---|---|
| POST | `/contas` | Abrir conta |
| GET | `/contas/{id}` | Consultar conta |
| POST | `/contas/{id}/depositos` | Realizar depósito |
| POST | `/contas/{id}/saques` | Realizar saque |
| POST | `/contas/{id}/transferencias` | Realizar transferência |
| GET | `/contas/{id}/movimentacoes` | Consultar extrato |

## Exemplos de requisições

### Abrir uma conta

O cliente informado deve estar cadastrado.

```http
POST /contas
Content-Type: application/json
```

```json
{
  "clienteId": 1
}
```

### Depositar

```http
POST /contas/1/depositos
Content-Type: application/json
```

```json
{
  "valor": 100.00
}
```

### Sacar

```http
POST /contas/1/saques
Content-Type: application/json
```

```json
{
  "valor": 20.00
}
```

### Transferir

O ID da conta de origem é informado na URL. A conta de destino e o valor são enviados no corpo.

```http
POST /contas/1/transferencias
Content-Type: application/json
```

```json
{
  "idDestino": 2,
  "valor": 30.00
}
```

### Consultar extrato

```http
GET /contas/1/movimentacoes
```

Exemplo do formato de resposta:

```json
[
  {
    "id": 3,
    "valor": 30.00,
    "dataHora": "2026-10-16T15:00:00Z",
    "tipoMov": "TRANSFERENCIA_ENVIADA"
  },
  {
    "id": 2,
    "valor": 20.00,
    "dataHora": "2026-10-16T14:00:00Z",
    "tipoMov": "SAQUE"
  },
  {
    "id": 1,
    "valor": 100.00,
    "dataHora": "2026-10-16T13:00:00Z",
    "tipoMov": "DEPOSITO"
  }
]
```

## Como executar

### Pré-requisitos

- JDK 21.
- Maven instalado ou Maven Wrapper disponível no projeto.

### Pela IDE

1. Abra o projeto como projeto Maven.
2. Aguarde a resolução das dependências.
3. Execute a classe `ApibancariaApplication`.

### Pelo terminal

Na pasta que contém o arquivo `pom.xml`, execute:

```bash
mvn spring-boot:run
```

Com a porta padrão do Spring Boot, a API estará disponível em:

```text
http://localhost:8080
```

As configurações do banco e da aplicação ficam em:

```text
src/main/resources/application.properties
```

## Tratamento de erros

A aplicação possui exceções para recursos não encontrados e operações inválidas.

Exemplos:

- Cliente ou conta inexistente.
- CPF já cadastrado.
- Cliente que já possui conta.
- Valor de operação inválido.
- Saldo insuficiente.
- Transferência para a mesma conta.

## Próximas etapas

- Validar os fluxos e respostas dos endpoints.
- Implementar testes automatizados.
- Aprimorar as validações de entrada.
- Adicionar paginação e filtros ao extrato.
- Ampliar o uso de DTOs para clientes e contas.
- Migrar para PostgreSQL e adicionar migrações.
- Implementar autenticação e autorização.
- Tratar operações simultâneas sobre o saldo.
- Documentar a API com OpenAPI.
- Adicionar Docker e integração contínua.

## Limitações atuais

A aplicação ainda não possui autenticação, autorização ou proteção específica contra alterações simultâneas de saldo. Não está preparada para uso financeiro real.

## Autor

Pedro Augusto Miranda Ceppo

Projeto desenvolvido durante os estudos de Java e Spring Boot.
