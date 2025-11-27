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
