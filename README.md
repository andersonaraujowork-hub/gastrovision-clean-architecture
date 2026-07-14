# GastroVision - Sistema de Gestão de Restaurantes                                                                                                                                
   
  ## Sobre o projeto                                                                                                                                                                
                  
  Sistema de gestão compartilhado para restaurantes, desenvolvido como Tech Challenge - Fase 2 da Pós Tech FIAP (Arquitetura e Desenvolvimento Java).

  O sistema permite gerenciar tipos de usuários, restaurantes e itens do cardápio por meio de uma API REST, seguindo os princípios da Clean Architecture.

  ## Arquitetura

  O projeto é organizado em camadas seguindo Clean Architecture:

  - **Domain** — entidades de negócio e interfaces de repositório, sem dependência de frameworks
  - **Application** — use cases e DTOs de entrada/saída
  - **Infrastructure** — implementações JPA, controllers REST e tratamento global de exceções

  ## Tecnologias

  - Java 21
  - Spring Boot 4.0.7
  - Spring Data JPA / Hibernate
  - MySQL 8.0
  - H2 (banco em memória para testes)
  - Docker / Docker Compose
  - JUnit 5 + Mockito

  ## Como executar

  ### Com Docker (recomendado)

  ```bash
  docker-compose up --build

  A aplicação estará disponível em: http://localhost:8080

  Sem Docker

  1. Ter MySQL rodando localmente na porta 3306
  2. Ajustar src/main/resources/application.properties com suas credenciais
  3. Executar:

  mvn spring-boot:run

  Endpoints da API

  Tipo de Usuário — /api/user-types

  ┌────────┬──────────────────────┬───────────────┐
  │ Método │         Rota         │   Descrição   │
  ├────────┼──────────────────────┼───────────────┤
  │ POST   │ /api/user-types      │ Criar tipo    │
  ├────────┼──────────────────────┼───────────────┤
  │ GET    │ /api/user-types      │ Listar todos  │
  ├────────┼──────────────────────┼───────────────┤
  │ GET    │ /api/user-types/{id} │ Buscar por ID │
  ├────────┼──────────────────────┼───────────────┤
  │ PUT    │ /api/user-types/{id} │ Atualizar     │
  ├────────┼──────────────────────┼───────────────┤
  │ DELETE │ /api/user-types/{id} │ Remover       │
  └────────┴──────────────────────┴───────────────┘

  Usuário — /api/users

  ┌────────┬─────────────────┬───────────────┐
  │ Método │      Rota       │   Descrição   │
  ├────────┼─────────────────┼───────────────┤
  │ POST   │ /api/users      │ Criar usuário │
  ├────────┼─────────────────┼───────────────┤
  │ GET    │ /api/users      │ Listar todos  │
  ├────────┼─────────────────┼───────────────┤
  │ GET    │ /api/users/{id} │ Buscar por ID │
  ├────────┼─────────────────┼───────────────┤
  │ PUT    │ /api/users/{id} │ Atualizar     │
  ├────────┼─────────────────┼───────────────┤
  │ DELETE │ /api/users/{id} │ Remover       │
  └────────┴─────────────────┴───────────────┘

  Restaurante — /api/restaurants

  ┌────────┬───────────────────────┬───────────────────┐
  │ Método │         Rota          │     Descrição     │
  ├────────┼───────────────────────┼───────────────────┤
  │ POST   │ /api/restaurants      │ Criar restaurante │
  ├────────┼───────────────────────┼───────────────────┤
  │ GET    │ /api/restaurants      │ Listar todos      │
  ├────────┼───────────────────────┼───────────────────┤
  │ GET    │ /api/restaurants/{id} │ Buscar por ID     │
  ├────────┼───────────────────────┼───────────────────┤
  │ PUT    │ /api/restaurants/{id} │ Atualizar         │
  ├────────┼───────────────────────┼───────────────────┤
  │ DELETE │ /api/restaurants/{id} │ Remover           │
  └────────┴───────────────────────┴───────────────────┘

  Itens do Cardápio — /api/restaurants/{restaurantId}/menu-items

  ┌────────┬─────────────────────────────────────────────────┬───────────────┐
  │ Método │                      Rota                       │   Descrição   │
  ├────────┼─────────────────────────────────────────────────┼───────────────┤
  │ POST   │ /api/restaurants/{restaurantId}/menu-items      │ Criar item    │
  ├────────┼─────────────────────────────────────────────────┼───────────────┤
  │ GET    │ /api/restaurants/{restaurantId}/menu-items      │ Listar itens  │
  ├────────┼─────────────────────────────────────────────────┼───────────────┤
  │ GET    │ /api/restaurants/{restaurantId}/menu-items/{id} │ Buscar por ID │
  ├────────┼─────────────────────────────────────────────────┼───────────────┤
  │ PUT    │ /api/restaurants/{restaurantId}/menu-items/{id} │ Atualizar     │
  ├────────┼─────────────────────────────────────────────────┼───────────────┤
  │ DELETE │ /api/restaurants/{restaurantId}/menu-items/{id} │ Remover       │
  └────────┴─────────────────────────────────────────────────┴───────────────┘

  Exemplos de requisição

  Criar tipo de usuário
  POST /api/user-types
  { "name": "Dono de Restaurante" }

  Criar usuário
  POST /api/users
  { "name": "João Silva", "email": "joao@email.com", "userTypeId": 1 }

  Criar restaurante
  POST /api/restaurants
  {
    "name": "Cantina Italiana",
    "address": "Rua das Flores, 123",
    "cuisineType": "Italiana",
    "openingHours": "11:00 - 23:00",
    "ownerId": 1
  }

  Criar item do cardápio
  POST /api/restaurants/1/menu-items
  {
    "name": "Pizza Margherita",
    "description": "Molho de tomate, mussarela e manjericão",
    "price": 45.90,
    "onSiteOnly": false,
    "photoPath": "/images/pizza-margherita.jpg"
  }

  Testes

  mvn test

  Cobertura mínima de 80% com testes unitários (use cases) e testes de integração (controllers).

  Collection Postman

  O arquivo de collection está disponível em /postman/gastrovision.postman_collection.json.
  Importe no Postman para testar todos os endpoints da API.