
# Crud-Security 🚷👮

## Linguagem 📄

Este README também está disponível em outras línguas:
- 🇺🇸 [Inglês](README-en.md)

## O Projeto 📊

Este é um CRUD simples de gerenciamento de tarefas com SpringBoot.

## Tecnologias Utilizadas 🧭

- **SpringBoot** - Framework principal
- **Spring Data JPA** - Para interações com o banco de dados
- **H2 Database** - Banco de dados na memória para testes e desenvolvimento
- **Spring Security** - Para autenticação e autorização
- **Swagger** - Para documentação da API
- **Maven** - Gerenciador de dependências
- **JUnit** - Para testes unitários
- **MockMVC** - Para testes de integração

## Pré-requisitos

- Java 8 ou superior
- Maven
- IDE(recomendado IntelliJ ou VSCode)

## Como rodar o projeto

1. Copie o repositório
```bash
git clone https://github.com/guibarbian/Security-Crud
cd Security-Crud
```
2. Instale as dependências
```bash
mvn install
```
3. Rode a aplicação
```bash
mvn spring-boot:run
```
A aplicação vai ser executada em http://localhost:8080

Você pode usar algum cliente de API como Postman ou Insomnia para testar os endpoints manualmente

# Endpoints
## Segurança

Esta API conta com um padrão de segurança baseado em JWT (JSON Web Token) para autenticação e
autorização. Para utilizar os seus Endpoints, você deve registrar um usuário e logar com as credenciais 
nescessarias(email e senha).

Para criar um usuário, basta acessar o endpoint `/auth/registration` com um JSON body contendo as 
seguintes informações:

```json
{
  "name": "nomeDoUsuário",
  "email": "emailDoUsuário",
  "password": "senhaDoUsuário"
}
```

Ao criar o seu usuário(Endpoint retornou 201), você pode acessar o `auth/login` para se autenticar e utilizar os 
demais endpoints. A autenticação requer um JSON body com as seguintes informações:

```json
{
  "email": "emailDoUsuário",
  "password": "senhaDoUsuário"
}
```

Caso a API retorne 200, significa que você está autenticado, copie o token retornado e use-o para acessoar os 
endpoints de `tasks`

## Tarefas

Esta API tem os seguintes Endpoints para organização de tarefas

| Método | Endpoint      | Descrição              |
|--------|---------------|------------------------|
| GET    | `/tasks/{id}` | Acha uma tarefa por ID |
| POST   | `/tasks`      | Cria uma nova tarefa   |
| PUT    | `/tasks/{id}` | Atualiza uma tarefa    |
| DELETE | `/tasks/{id}` | Deleta uma task        |

## 

Para criar ou atualizar uma tarefa, você deve enviar um corpo JSON com os seguintes atributos:
```json
{
  "name": "nomeDaTarefa",
  "description": "descriçãoDaTarefa",
  "status": "statusDaTarefa"
}
```

# Desenvolvido com ⚙

- **IntelliJ IDEA**

# Autor ✏

- Guilherme A. Barbian 

