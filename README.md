# Projet Android - [POKEMON]

Ce projet est une application Android développée avec **Kotlin** et utilisant les meilleures pratiques modernes telles que **Hilt**, **Retrofit**, **Jetpack Compose**, et **Clean Architecture**.

## Technologies et Outils Utilisés

- **Kotlin** : Langage principal de développement.
- **Hilt** : Injection de dépendances pour faciliter la gestion des dépendances dans l'application.
- **Retrofit** : Librairie pour effectuer des appels API, avec support de la pagination.
- **Jetpack Compose** : UI déclarative pour construire des interfaces utilisateur modernes.
- **MVVM** : Architecture utilisée pour séparer les responsabilités et maintenir un code propre et testable.
- **Coroutines** : Pour gérer la programmation asynchrone de manière efficace et lisible.

## Fonctionnalités Principales

### 1. **Envoi de données à l'API**
   L'application permet à l'utilisateur d'envoyer des données à l'API, telles que :
   - **Texte d'un champ de recherche** (par exemple, recherche d'articles ou d'images).
   - **Données de géolocalisation** pour récupérer des informations spécifiques à un emplacement.

   Ces données sont envoyées via **Retrofit** à l'API pour obtenir des réponses pertinentes.

### 2. **Affichage des Informations reçues**
   Une fois les données récupérées depuis l'API, l'application affiche :
   - **Texte** provenant de l'API.

   L'interface utilisateur est construite avec **Jetpack Compose**, qui offre une expérience fluide et réactive.

### 3. **Pagination**
   L'application implémente la **pagination** pour les appels API afin d'optimiser l'affichage et réduire la consommation de ressources lors de la récupération de données volumineuses.

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

Des tests sont écrits pour garantir la stabilité et la fiabilité du code :
- Tests unitaires pour les **ViewModels** et la logique métier.
- Tests d'interface utilisateur (UI) pour tester la navigation et l'affichage des données.

