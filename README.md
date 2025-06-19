# GRPC ORDER SERVER

Projeto desenvolvido para estudos utilizando a comunicação gRPC e gerencimento de filas com RabbitMQ. 

## 🚀 Começando

Essas instruções permitirão que você execute o projeto em sua máquina local, para fins de desenvolvimento e testes. 


### 📋 Pré-requisitos

- Java 21
- Docker e Docker Compose
- Maven (opicional)

### 🔧 Configurando o projeto

1. Execute o git clone do projeto no diretório que desejar:

```
git clone https://github.com/joaolucasbarboza/grpc-order-server.git
```

2. Execute o Maven Clean e Maven Install para criar as classes Java com base no arquivo .proto usando o plugin do Maven:

```
mvn clean install
```

> ⚠️ Observação importante (usando a IDE):
> Se você estiver utilizando uma IDE como IntelliJ IDEA, não é necessário ter o Maven instalado manualmente na sua máquina. A própria IDE já gerencia o Maven internamente através do wrapper ou da configuração do projeto.

Utilizando a IDE Intellij IDEA:
- Vá até o icone "Maven" na lateral direita da IDE
- Expanda o projeto > __Lifecycle__ > __clean__ e depois __install__

Após isso, as classes geradas a partir do .proto estarão disponíveis automaticamente dentro do diretório target/generated-sources.

3. Execute o __docker-compose__ para criar os containers necessários:

```
docker-compose up
```

Após todos os passos serem executados com exitô, inicie o projeto.


## 🛠️ Tecnologias utilizadas

* [Spring Boot](https://docs.spring.io/spring-boot/index.html) - Framework
* [Maven](https://maven.apache.org/) - Gerente de Dependência
* [gRPC](https://docs.spring.io/spring-grpc/reference/server.html) - Protocolo
* [PostgreSQL](https://www.postgresql.org/) - Banco de dados relacional
* [RabbitMQ](https://www.rabbitmq.com/) - Gerenciamento de fila

## 📁 Estrutura do projeto
> A estrutura do projeto foi simplificada para fins de estudos, não utilizando a Clean Arch a fundo, mas com alguns principios de boas práticas.
```
📦 main
 ┣ 📂 java
 ┃ ┗ 📂 br.edu.fema.order_server
 ┃   ┣ 📂 configuration
 ┃   ┣ 📂 dto
 ┃   ┣ 📂 entity
 ┃   ┣ 📂 enums
 ┃   ┣ 📂 message
 ┃   ┣ 📂 repository
 ┃   ┣ 📂 service
 ┃   ┃ ┣ 📂 stepsProcess
 ┃   ┣ 📂 proto
 ┃   ┗ 📂 resources
 ┗ 📂 target
```
