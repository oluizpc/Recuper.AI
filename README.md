# Recuper.AI

Recuper.AI é um **SaaS de recuperação de carrinhos abandonados para e-commerce**, focado em automatizar o contato com clientes após o abandono do checkout, utilizando regras de negócio, automação e inteligência artificial como apoio à decisão.

## Objetivo do Projeto
Desenvolver um sistema capaz de:
- Identificar carrinhos abandonados em tempo real
- Registrar eventos e dados do e-commerce
- Automatizar o contato com o cliente (ex: WhatsApp)
- Acompanhar métricas de conversão e recuperação
- Servir como base para um produto SaaS escalável

Este projeto também será utilizado como **Trabalho de Conclusão de Curso (TCC)**, abordando conceitos de engenharia de software, arquitetura em camadas e sistemas orientados a eventos.

---

## Funcionalidades (MVP)
- Integração com plataformas de e-commerce (ex: Shopify)
- Registro de carrinhos, clientes e pedidos
- Detecção automática de carrinho abandonado
- Envio de mensagens de recuperação
- Painel com métricas básicas (abandonados, convertidos, perdidos)
- Estrutura preparada para uso de Inteligência Artificial

---

## Arquitetura
O projeto segue uma **arquitetura em camadas**, separando responsabilidades:

- **Domain**: entidades, enums e regras de negócio
- **Application**: serviços e DTOs
- **API**: controllers REST
- **Infrastructure**: integrações, schedulers e configurações
- **Database**: versionamento de banco com Flyway

---

## Tecnologias Utilizadas
- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway**
- **Maven**
- **Lombok**

---

## Como executar o projeto
### Pré-requisitos
- Java 21
- Maven
- PostgreSQL

### Passos
1. Clone o repositório:
```bash
git clone https://github.com/oluizpc/Recuper.AI.git
```
2. Configure o banco de dados no application.properties ou application.yml
3. Execute a aplicação:
```bash
mvn spring-boot:run
```
O Flyway será responsável por criar a estrutura inicial do banco de dados.

## Status do Projeto
### Em desenvolvimento (MVP)

## Autor
### Luiz Paullo
Projeto desenvolvido para fins acadêmicos e profissionais.
