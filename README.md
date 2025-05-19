#  Application de Banque Digitale

##  Présentation

Cette application permet de gérer un système bancaire digital complet à l'aide des technologies **Spring Boot** et **Java EE**. Elle inclut la gestion des clients, des comptes bancaires (courant et épargne), l’historique des opérations, les virements entre comptes, et des statistiques de tableau de bord.

---

## 🎯 Fonctionnalités principales

- 🔹 Gestion des clients (CRUD)
- 🔹 Création et administration des comptes bancaires
- 🔹 Opérations : Débit, Crédit, Virement
- 🔹 Consultation de l’historique des opérations (filtrage, pagination)
- 🔹 Statistiques & dashboard (avec `DashboardDTO`)
- 🔹 Documentation de l’API avec Swagger

---

## 🛠️ Technologies utilisées

- **Backend** :
  - Java 21
  - Spring Boot 3.4.5
  - Spring Data JPA + Hibernate
  - REST API
  - Lombok
  - Maven

- **Base de données** :
  - MySQL (ou H2 en mode test)

- **Documentation** :
  - SpringDoc OpenAPI (Swagger)

- **Outils de test** :
  - JUnit, Spring Test

---

## 📁 Structure du projet

```
digital-banking/
├── eBankFrentEnd/                  # (future intégration Angular)
├── src/
│   └── main/
│       ├── java/tech/mobl3lm/digitalbanking/
│       │   ├── dtos/               # Objets de transfert (DTO)
│       │   ├── entities/           # Entités JPA
│       │   ├── enums/              # Types énumérés
│       │   ├── exceptions/         # Exceptions personnalisées
│       │   ├── mappers/            # Conversion entités <-> DTOs
│       │   ├── repositories/       # Repositories Spring Data
│       │   ├── services/           # Logique métier
│       │   └── web/                # Contrôleurs REST
│       └── resources/
│           └── application.properties
```

---

##  Détails des packages

###  `dtos`
Transfert de données entre couches : `CustomerDTO`, `BankAccountDTO`, `DashboardStatsDTO`, `TransferRequestDTO`, etc.

###  `entities`
Modélisation des données avec JPA :
- `Customer`
- `BankAccount` (abstraite)
- `CurrentAccount`
- `SavingAccount`
- `Operation`

###  `enums`
Types énumérés utilisés :
- `AccountStatus` : CREATED, ACTIVE, SUSPENDED...
- `OperationType` : DEBIT, CREDIT

###  `exceptions`
Gestion centralisée des erreurs avec :
- `BankAccountNotFoundException`
- `CustomerNotFoundException`
- `InsufficientBalanceException`
- `GlobalExceptionHandler`

###  `mappers`
Transforme les entités en DTOs via `BankAccountMapper`.

###  `repositories`
Interface avec la base de données :
- `CustomerRepository`
- `BankAccountRepository`
- `AccountOperationRepository`

###  `services`
Contient la logique métier :
- `CustomerService`, `CustomerServiceInterface`
- `BankService`
- `DashboardService`, `DashboardServiceImpl`

###  `web`
Contrôleurs REST exposant les endpoints :
- `CustomerController`
- `CompteController`
- `OperationController`
- `DashboardController`

---

##  Endpoints REST principaux

###  Clients
- `GET /customers`
- `GET /customers/{id}`
- `POST /customers`
- `PUT /customers/{id}`
- `DELETE /customers/{id}`

###  Comptes
- `GET /accounts`
- `GET /accounts/{id}`
- `POST /accounts` (compte courant ou épargne)
- `DELETE /accounts/{id}`

###  Opérations
- `POST /accounts/debit`
- `POST /accounts/credit`
- `POST /accounts/transfer`
- `GET /accounts/{id}/operations`
- `GET /accounts/{id}/pageOperations`

###  Dashboard
- `GET /dashboard/stats`

---


##  Lancer le projet

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/ton-utilisateur/nom-du-repo.git
   cd digital-banking
   ```

2. Configurer la base de données MySQL dans `application.properties`.

3. Compiler le projet :
   ```bash
   mvn clean install
   ```

4. Démarrer le projet :
   ```bash
   mvn spring-boot:run
   ```

5. Accéder à l’API :
   - API : `http://localhost:8085`
   - Swagger UI : `http://localhost:8085/swagger-ui.html`

---

##  Configuration MySQL

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/eBank?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

---
## 👨‍🎓 Auteur
**Mohamed BOULAALAM**  
Étudiant en ingénierie informatique, Big Data and Cloud Computing – ENSET Mohammedia
