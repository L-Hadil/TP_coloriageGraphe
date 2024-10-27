## Auteur
- **Nom et prénom** : LADJ Hadil -GL

# TP Coloriage de Graphe - Allocation de Registres

Ce projet implémente un algorithme de coloriage de graphe basé sur la méthode de Chaitin pour l'allocation de registres. L'objectif est de minimiser le spilling et d'optimiser l'assignation des registres grâce au coalescing.

## Fonctionnalités
- **Coloriage de Graphe avec Algorithme de Chaitin** : Simplification et coloriage pour assigner des registres avec un minimum de spilling.
- **Optimisation avec Coalescing** : Tentative de recoloration des nœuds spillés pour réduire l'utilisation de la pile et optimiser la coloration finale.

## Structure
- **Graph.java** : Gère la structure du graphe, les arêtes d'interférence et de préférence, ainsi que l'algorithme de coloriage.
- **Node.java** : Définit les nœuds du graphe, leurs interférences, préférences et couleurs.
- **RegisterAllocationMain.java** : Classe principale, interface console pour exécuter le programme avec différents graphes et paramètres.

## Utilisation
1. Clonez le dépôt et compilez les fichiers Java.
2. Exécutez le programme et choisissez le graphe à colorier et le nombre de registres.

