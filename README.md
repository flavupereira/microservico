## Sistema de Microserviços - E-commerce
## 📋 Visão Geral

Este projeto é um sistema de e-commerce baseado em microserviços, implementado com Spring Boot e Spring Cloud.
O sistema é composto por vários serviços independentes que se comunicam entre si para fornecer funcionalidades 
completas de compra, autenticação, gerenciamento de pedidos e transporte.

## 🏗️ Arquitetura do Sistema

### Serviços Principais

**1. API Gateway** (api-gateway) - Porta 8082

**2. Serviço de Autenticação** (auth) - Porta 8088

**3. Servidor Eureka** (eureka-server) - Porta 8761

**4. Serviço de Loja** (loja) - Porta 8080

**5. Serviço de Fornecedor** (fornecedor)

**6. Serviço de Transportador** (transportador) - Porta 8083


## 🔧 Tecnologias Utilizadas

- **Java** 8 & 17

- **Spring Boot** 2.1.5 & 3.2.5

- **Spring Cloud** (Gateway, Eureka, Feign, Hystrix, Config)

- **Spring Security** + OAuth2

- **Spring Data JPA**

- **MySQL & H2 Database**

- **Maven**

  ## 🚀 Como Executar

**Pré-requisitos**

- Java 8 e 17

- Maven

- MySQL (opcional, H2 para desenvolvimento)

### Ordem de Inicialização

**1.Eureka Server**
```
bash
cd eureka-server
mvn spring-boot:run
```
**2.Authentication Service**

```
bash
cd auth-service
mvn spring-boot:run
```
**3.Fornecedor Service**
```
bash
cd fornecedor-service
mvn spring-boot:run
```
**4.Transportador Service**
```
bash
cd transportador-service
mvn spring-boot:run
```
**5.Loja Service**
```
bash
cd loja-service
mvn spring-boot:run
```
**6.API Gateway**
```
bash
cd api-gateway
mvn spring-boot:run
```

## 🔐 Autenticação e Segurança

### Configuração OAuth2

- Servidor de autorização: auth-service:8088

- Client: loja / lojapwd

- Usuário: joao / joaopwd

- Escopos: web, mobile

## Endpoints Protegidos

- POST /compra - Requer role USER

## 📡 Comunicação entre Serviços

## Service Discovery

- Eureka Server: Gerencia registro e descoberta de serviços

- Clientes: Todos os serviços se registram no Eureka

## API Gateway

- Roteamento dinâmico baseado em service discovery

- Headers sensíveis: Cookie, Authorization

- Actuator endpoints habilitados

## Comunicação via Feign Client

- LojaClient ↔ FornecedorClient

- LojaClient ↔ TransportadorClient

- Circuit breaker com Hystrix
