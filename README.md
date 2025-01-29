# Projet Android - [POKEMON]

Ce projet est une application Android développée avec **Kotlin** et utilisant les meilleures pratiques modernes telles que **Hilt**, **Retrofit**, **Room**, **Jetpack Compose**, et **Clean Architecture**.

## Technologies et Outils Utilisés

- **Kotlin** : Langage principal de développement.
- **Hilt** : Injection de dépendances pour faciliter la gestion des dépendances dans l'application.
- **Retrofit** : Librairie pour effectuer des appels API, avec support de la pagination.
- **Jetpack Compose** : UI déclarative pour construire des interfaces utilisateur modernes.
- **MVVM** : Architecture utilisée pour séparer les responsabilités et maintenir un code propre et testable.
- **Coroutines** : Pour gérer la programmation asynchrone de manière efficace et lisible.

## Fonctionnalités Principales

### 1. **Envoi de données à l'API**
   L'application permet à l'utilisateur d'envoyer des données à l'API

   Ces données sont envoyées via **Retrofit** à l'API pour obtenir des réponses pertinentes.

### 2. **Affichage des Informations reçues**
   Une fois les données récupérées depuis l'API, l'application affiche :
   - **Une liste de Pokémon** avec des informations de base (id,nom, etc...).
   - **Pagination** pour charger les Pokémon de manière optimale, en limitant le nombre de Pokémon affichés à la fois.
   - **Détails de chaque Pokémon** : Lorsqu'un utilisateur clique sur un Pokémon, les informations détaillées (name etc ..) sont affichées.

   L'interface utilisateur est construite avec **Jetpack Compose**, et la navigation entre les différents écrans est gérée avec **Navigation Compose**. Les utilisateurs peuvent faire défiler la liste de Pokémon, qui se charge de manière dynamique grâce à la pagination.

## Architecture du Projet

### 1. **Clean Architecture**
   L'architecture est basée sur le modèle **Clean Architecture** pour assurer une séparation claire des responsabilités entre les différentes couches de l'application :
   - **Domain** : Contient la logique métier.
   - **Data** : Contient les sources de données (API, base de données).
   - **Presentation** : Contient la couche UI avec **MVVM** (Model-View-ViewModel).

### 2. **MVVM (Model-View-ViewModel)**
   Ce modèle permet de séparer la logique métier de la logique d'interface utilisateur, rendant le code plus maintenable et testable.

### 3. **Coroutines**
   L'utilisation des **Coroutines** permet de gérer facilement les appels asynchrones, comme les appels API, sans bloquer le thread principal.

## Librairies Jetpack Utilisées

- **Navigation Compose** : Pour gérer la navigation entre les écrans.
- **Flow** : Pour observer les changements de données de manière réactive.
- **Room** : Pour la gestion de la base de données locale si nécessaire.

## Tests

Des tests sont écrits pour garantir la stabilité et la fiabilité du code

