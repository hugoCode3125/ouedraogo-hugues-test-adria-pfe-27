# Évaluation technique — Architecture Micro-services, IA et développement Full-stack

**ADRIA BT | ENSET**

**Candidat :** Hugues Fawzi OUEDRAOGO  
**Formation :** Génie Logiciel et Systèmes Informatiques Distribués (GLSID)  
**Technologies principales :** Java 21, Spring Boot, Spring Cloud, Spring AI, Angular, Kafka, Docker

---

# Sommaire

1. Présentation du projet
2. Architecture de la solution
3. Environnement technique
4. Développement des microservices
5. Exposition et documentation des API
6. Tests et validation
7. Interface utilisateur
8. Sécurité
9. Choix techniques et difficultés rencontrées
10. Structure du projet

---

# 1. Présentation du projet

## 1.1 Contexte

Dans le cadre de l’évaluation technique organisée par ADRIA BT en collaboration avec l’ENSET, l’objectif est de concevoir une application bancaire reposant sur une architecture microservices.

La solution permet de gérer des comptes bancaires, d'enregistrer des transactions et de consulter les informations associées. Elle intègre également un assistant conversationnel capable d'interagir avec les services métier afin de répondre aux demandes des utilisateurs.

L'application s'appuie sur l'écosystème Spring pour la construction des services distribués et sur plusieurs composants complémentaires pour assurer leur communication, leur découverte et leur configuration.

## 1.2 Objectifs

Les principaux objectifs du projet sont :

- Concevoir une architecture basée sur des microservices indépendants.
- Développer les services de gestion des comptes et des transactions.
- Exposer les fonctionnalités métier à travers plusieurs interfaces.
- Assurer la communication interservices.
- Mettre en place la découverte des services et le routage centralisé.
- Intégrer un assistant conversationnel utilisant l'intelligence artificielle et les outils MCP.
- Mettre en place une communication événementielle avec Kafka.
- Conteneuriser les différents composants de l'application.

## 1.3 Fonctionnalités principales

La solution couvre les fonctionnalités suivantes :

| Domaine | Fonctionnalités |
|---|---|
| Gestion des comptes | Création, consultation et récupération des comptes |
| Gestion des transactions | Enregistrement et consultation des transactions |
| Validation métier | Vérification du montant, du compte, du statut et de la devise |
| Communication | Appels interservices avec OpenFeign |
| Résilience | Gestion des erreurs de communication avec Resilience4j |
| Assistant IA | Interaction conversationnelle avec les services métier |
| Événements | Publication et consommation d'événements avec Kafka |
| Infrastructure | Découverte, configuration et routage des services |
| Déploiement | Conteneurisation avec Docker et Docker Compose |

# 2. Architecture de la solution

## 2.1 Vue d'ensemble

L'application adopte une architecture microservices. Chaque service possède une responsabilité fonctionnelle précise et expose ses fonctionnalités à travers des interfaces dédiées.

Les services métier principaux sont :

- **Account Service :** responsable de la gestion des comptes bancaires.
- **Transaction Service :** responsable de l'enregistrement et de la gestion des transactions.

L'infrastructure est complétée par Eureka Server, Config Server et API Gateway. Un service supplémentaire est consacré à l'assistant conversationnel.

La communication entre les services repose principalement sur les API REST et OpenFeign. Kafka intervient pour la transmission des événements métier.

![alt text](<Screenshot 2026-10-30 113707.png>)

*Figure 1 — Architecture globale de la solution.*

## 2.2 Composants de l'architecture

| Composant | Responsabilité |
|---|---|
| API Gateway | Point d'entrée centralisé pour les requêtes vers les services |
| Eureka Server | Enregistrement et découverte des services |
| Config Server | Centralisation de la configuration |
| Account Service | Gestion des comptes bancaires |
| Transaction Service | Gestion et validation des transactions |
| Assistant Service | Gestion des interactions avec le modèle d'IA et les outils MCP |
| Kafka | Transmission des événements liés aux transactions |

## 2.3 Diagramme de classes métier

Le modèle métier repose principalement sur deux entités : `Account` et `Transaction`.

L'entité `Account` représente un compte bancaire et contient notamment son identifiant, la référence du client, son statut, sa devise et son solde.

L'entité `Transaction` représente une opération effectuée sur un compte. Elle contient son montant, sa devise, sa catégorie marchande, son pays et sa date de création.

![alt text](<Screenshot 2026-10-30 110431.png>)

*Figure 2 — Modèle de données métier.*

## 2.4 Organisation des services

Chaque microservice est organisé en plusieurs couches afin de séparer les responsabilités :

| Couche | Rôle |
|---|---|
| `domain` | Contient les entités et les éléments du domaine métier |
| `repository` | Assure l'accès aux données |
| `service` | Regroupe les règles métier et les traitements |
| `web/rest` | Expose les fonctionnalités à travers REST |
| `web/graphql` | Expose les fonctionnalités à travers GraphQL |
| `mcp` | Expose les outils destinés aux interactions MCP |

Cette organisation permet de centraliser les traitements métier dans la couche service. Les différentes interfaces d'accès utilisent ainsi les mêmes règles métier, sans dupliquer leur implémentation.

# 3. Environnement technique

## 3.1 Technologies utilisées

| Technologie | Utilisation |
|---|---|
| Java 25 | Langage de développement |
| Spring Boot | Développement des microservices |
| Spring Cloud | Infrastructure distribuée |
| Spring Cloud Gateway | Routage des requêtes |
| Netflix Eureka | Découverte des services |
| Spring Cloud Config | Configuration centralisée |
| Spring Data JPA | Persistance des données |
| H2 Database | Base de données utilisée pour les tests et le développement |
| OpenFeign | Communication entre microservices |
| Resilience4j | Gestion de la résilience |
| Spring AI | Intégration du modèle d'intelligence artificielle |
| MCP | Exposition et utilisation des outils métier |
| Apache Kafka | Communication événementielle |
| Docker | Conteneurisation |
| Docker Compose | Orchestration locale des conteneurs |
| Maven | Gestion des dépendances et construction du projet |

## 3.2 Environnement de développement

Le développement a été réalisé dans un environnement Java avec Maven pour la gestion des dépendances et la construction des applications.

Les services peuvent être exécutés indépendamment ou dans un environnement conteneurisé à l'aide de Docker Compose.

![alt text](<Screenshot 2026-10-30 114042.png>)

*Figure 3 — Organisation des modules dans l'environnement de développement.*

# 4. Développement des microservices

## 4.1 Account Service

Le service `account-service` est responsable de la gestion des comptes bancaires.

Il permet notamment de créer un compte et de récupérer les informations d'un compte à partir de son identifiant.

### Modèle Account

L'entité `Account` contient les attributs suivants :

| Attribut | Type | Description |
|---|---|---|
| id | UUID | Identifiant unique du compte |
| customerReference | String | Référence du client |
| status | AccountStatus | Statut du compte |
| currency | String | Devise du compte |
| balance | double | Solde disponible |

Le statut du compte est représenté par une énumération `AccountStatus`, qui distingue deux états :

- `ACTIVE` : compte actif.
- `BLOCKED` : compte bloqué.

### Règles métier

Lors de la création d'un compte, son statut est initialisé à `ACTIVE`. Le solde initial fourni est conservé.

Le service propose également la récupération d'un compte par son identifiant. Lorsqu'un compte n'existe pas, une réponse HTTP `404 NOT FOUND` est retournée.

![alt text](image.png)
![alt text](image-1.png)

*Figure 4 — Implémentation de la gestion des comptes.*

## 4.2 Transaction Service

Le service `transaction-service` prend en charge l'enregistrement des transactions bancaires.

Il communique avec `account-service` pour récupérer les informations du compte concerné et vérifier les conditions nécessaires avant d'enregistrer une opération.

### Modèle Transaction

L'entité `Transaction` contient les attributs suivants :

| Attribut | Type | Description |
|---|---|---|
| id | UUID | Identifiant unique de la transaction |
| accountId | UUID | Identifiant du compte concerné |
| amount | BigDecimal | Montant de la transaction |
| currency | String | Devise de la transaction |
| merchantCategory | String | Catégorie du commerçant |
| country | String | Pays de l'opération |
| createdAt | Instant | Date de création |

### Règles de validation

Avant l'enregistrement d'une transaction, plusieurs contrôles sont effectués :

1. Le montant doit être strictement positif.
2. Le montant ne doit pas dépasser le plafond configuré.
3. Le compte associé doit exister.
4. Le compte doit être actif.
5. La devise de la transaction doit correspondre à celle du compte.
6. Le solde doit être suffisant pour effectuer l'opération.

Lorsque les conditions sont satisfaites, la transaction est enregistrée et le solde du compte est mis à jour.

Les montants sont représentés avec `double` afin de limiter les problèmes de précision liés aux calculs financiers.

![alt text](image-2.png)

*Figure 5 — Vérifications effectuées lors de la création d'une transaction.*

## 4.3 Communication interservices avec OpenFeign

Le service Transaction utilise OpenFeign pour communiquer avec le service Account.

Cette approche permet d'appeler les endpoints REST du service distant à travers une interface Java déclarative.

Lors de la création d'une transaction, le service récupère les informations du compte concerné avant d'effectuer les validations métier.

Cette séparation permet à chaque microservice de conserver sa responsabilité tout en collaborant avec les autres services.

![alt text](image-3.png)

*Figure 6 — Client OpenFeign utilisé pour communiquer avec Account Service.*

### 4.4. Discovery Service

Le `discovery-service` assure l'enregistrement des instances et permet aux autres composants de découvrir les services disponibles.

**Responsabilités principales :**
- Enregistrement des microservices.
- Découverte des instances disponibles.

### 4.5. Gateway Service

Le `gateway-service` constitue le point d'entrée des requêtes HTTP. Il applique les règles de routage et redirige les requêtes vers les microservices fonctionnels.

**Responsabilités principales :**
- Centralisation des accès aux API.
- Routage vers les services cibles.

### 4.6. Config Service

Le `config-service` centralise la configuration des microservices afin d'éviter la duplication des paramètres et de faciliter leur gestion.

**Responsabilités principales :**
- Distribution de la configuration applicative.
- Gestion des paramètres propres à chaque service.


# 5. Exposition et documentation des API

## 5.1 API REST

Les services métier exposent leurs fonctionnalités à travers des endpoints REST.

Cette interface permet aux applications clientes et aux autres services de communiquer avec les fonctionnalités métier à l'aide de requêtes HTTP.

Les opérations suivent les conventions REST, notamment :

| Méthode HTTP | Utilisation |
|---|---|
| GET | Récupérer des informations |
| POST | Créer une ressource |
| PUT | Remplacer ou mettre à jour une ressource |
| PATCH | Modifier partiellement une ressource |
| DELETE | Supprimer une ressource |

Les réponses HTTP permettent d'indiquer le résultat des opérations, notamment `200 OK`, `201 CREATED`, `400 BAD REQUEST` et `404 NOT FOUND`.


# 6. Tests et validation


## 6. Tests et résultats

Cette section présente les vérifications réalisées sur les services techniques, les services métier et les fonctionnalités principales de l'application.

### 6.1. Tests des services techniques

#### Discovery Service

Le démarrage du serveur de découverte et l'enregistrement des microservices ont été vérifiés.

**Vérifications :** disponibilité du serveur et visibilité des instances enregistrées.

![alt text](<Screenshot 2026-10-30 113357.png>)

*Cette capture montre que les services se sont bien  enregistrés dans le Discovery Service.*

#### Gateway Service

Le routage des requêtes HTTP vers les services fonctionnels a été vérifié à travers les routes configurées.

**Vérifications :** accessibilité des routes et réception des réponses des services cibles.

![alt text](image-4.png)

*Cette capture montre le fonctionneemnt du routage via l'API Gateway qui renvoit donc bers Account service.*

#### Config Service

La récupération de la configuration centralisée a été vérifiée pour les services concernés.

**Vérifications :** disponibilité de la configuration et récupération des propriétés attendues.

![alt text](image-5.png)
Cette capture montre la réponse de l'endpoint de configuration ou journal de démarrage montrant le chargement de la configuration.

*Figure 5 — Vérification du Config Service.*

## 6.2. Tests Account Service
Les opérations de gestion des comptes bancaires ont été vérifiées à travers les API REST.

Scénarios vérifiés :
- Consultation d'un compte à partir de son identifiant.

![alt text](image-6.png)
-  Consultation des accounts existants

## 6.3. Tests Transaction Service
Les opérations de gestion des transactions ont été vérifiées afin de contrôler leur création et le respect des règles métier.

Scénarios vérifiés :
- Consultation des transactions effectuees

![alt text](image-7.png)


# 7. Interface utilisateur

L'interface utilisateur constitue le point d'accès graphique à la plateforme bancaire.

Elle a pour objectif de faciliter l'accès aux fonctionnalités principales, notamment la consultation des comptes, l'affichage des informations bancaires et les opérations liées aux transactions.

**À compléter **

# 8. Sécurité

La sécurité constitue un aspect important d'une application bancaire.

Elle doit notamment couvrir l'authentification des utilisateurs, la gestion des autorisations et la protection des échanges entre les composants.

Dans une architecture basée sur Spring, Spring Security et Keycloak peuvent être utilisés pour mettre en place une gestion centralisée des identités et des accès.

Les contrôles métier réalisés au niveau des services permettent également de limiter les opérations non autorisées par les règles fonctionnelles, comme les transactions sur des comptes bloqués ou les opérations dépassant le solde disponible.

**À compléter**

# 9. Choix techniques et difficultés rencontrées


| Décision | Alternative écartée | Justifications |
|---|---|---|
| Eureka | Consul Discovery | Intégration native avec Spring Cloud donc mise en place plus rapide |
| Spring Cloud Config | Consul Config | Cohérence avec le reste de l'écosystème Spring |
| Gateway Server WebFlux (réactif) | Gateway Server Web MVC | Non bloquant, adapté au routage.|
| OpenFeign | RestTemplate / WebClient | Client déclaratif, plus lisible, intégré à Eureka |
| Angular | React | Maîtrise du framework, donc livraison plus rapide dans un temps limité. Framework complet (routeur, formulaires...) 
| Architecture micro-services | Monolithe | Séparation claire des responsabilités 
| `BigDecimal` pour les montants | `double` | Évite les erreurs d'arrondi sur les valeurs monétaires |
| H2 | PostgreSQL | Démarrage rapide  |




## 9.5 Difficultés rencontrées

Juste le temps sinon comprehension exacte et capacite a implementer le travail demande.





# Conclusion

Ce projet a permis de mettre en pratique plusieurs concepts liés à la conception d'applications distribuées : séparation des responsabilités métier, communication interservices, découverte dynamique, configuration centralisée et gestion de la résilience.

L'intégration de Kafka apporte une approche événementielle complémentaire, tandis que Spring AI et MCP ouvrent la possibilité d'interagir avec les fonctionnalités métier à travers un assistant conversationnel.

L'ensemble constitue une base technique permettant de comprendre les principaux mécanismes nécessaires à la conception et à l'évolution d'une application bancaire construite autour d'une architecture microservices.

---

