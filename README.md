# Un Pipeline Complet pour l’Analyse de Données MovieLens

## Description

Ce projet consiste à créer un pipeline complet pour l'analyse de données MovieLens. Il couvre toutes les étapes, depuis le nettoyage et la transformation des données jusqu'à l'analyse finale avec des visualisations dans Power BI.
--

## Introduction
Le projet vise à illustrer une approche complète pour l'analyse des données MovieLens. Il inclut des processus de nettoyage, de transformation, de chargement dans une base de données SQL Server et des visualisations interactives avec Power BI.

## Structure des Données
### Fichiers source :
1. `movies.csv` : Contient les informations sur les films.
2. `tags.csv` : Contient les tags associés aux films.
3. `ratings.csv` : Contient les évaluations des utilisateurs.

### Tables de Destination :
- **DimDate** : Dimension des dates.
- **DimUser** : Dimension des utilisateurs.
- **DimMovie** : Dimension des films.
- **FactRatings** : Table des faits avec les notations des films.

---

## Étapes du Pipeline
### 1. Nettoyage et Transformations des Données

#### `movies.csv`
- Validation des colonnes (`MovieID`, `Title`, `Genres`).
- Extraction de l'année de sortie (facultatif).
- Gestion des doublons et des champs vides.

#### `tags.csv`
- Validation des colonnes (`userId`, `movieId`, `tag`, `timestamp`).
- Conversion des timestamps en format lisible (si nécessaire).

#### `ratings.csv`
- Contrôle de la plage des valeurs de `Rating` (entre 0 et 5).
- Conversion des timestamps en date/heure.
- Gestion des doublons et des champs invalides.

### 2. Workflow dans Talend
Le pipeline Talend comprend 4 sous-jobs :
1. **DimDate** : Génération et chargement des dates.
2. **DimUser** : Extraction et chargement des utilisateurs.
3. **DimMovie** : Nettoyage et chargement des films.
4. **FactRatings** : Nettoyage, lookup et chargement des notations.

Un job principal orchestre ces sous-jobs, avec parallélisation des dimensions.

### 3. Optimisation des Index dans SQL Server
- **Index Cluster** sur les clés primaires des dimensions (e.g., `UserKey`, `MovieKey`).
- **Index Non Cluster** sur les clés étrangères de `FactRatings` pour les jointures.
- **Index Columnstore** pour accélérer les requêtes analytiques (si supporté).

---

## Visualisation des Données
### 1. Top N Films par Note Moyenne
- Type : Diagramme en barres.
- Contenu : Moyenne des notes par film.
- Filtre : Films avec au moins X évaluations.

### 2. Distribution des Notes
- Type : Histogramme.
- Contenu : Répartition des évaluations (1 à 5).
- Intérêt : Identifier les tendances générales dans les évaluations.

---

## Exigences Techniques
- **Talend Open Studio** : Pour construire le pipeline ETL.
- **SQL Server** : Pour le stockage des données.
- **Power BI** : Pour les visualisations interactives.
- **Dépendances** : Java, JDBC, et autres connecteurs nécessaires pour Talend.

---

## Installation et Exécution
1. Clonez ce dépôt :
   ```bash
   git clone https://github.com/votre-utilisateur/votre-projet.git
