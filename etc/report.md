# Rapport d'optimisation

## Choix de modélisation par méthodes

## Généralités
* Données de bases :
    * Nombre de processeurs à définir par l'utilisateur
    * Nombre de taches à définir par l'utilisateur
    * La durée de chaque tache est aléatoire de 0 jusqu'à 100

* Un etat est représenté par une hashmap de forme:
```
processeur: [tache0, tache1, tache2],
// ...
```

* Le critère d'arrêt commun est le fait de revoir n fois la même solution (n fixé par l'utilisateur)

* Un mouvement est calculé comme le passage d'une tache d'un processeur à un autre:
```
procS <- choix du processeur d'origine
procD <- choix du processeur destination
task  <- tache du processeur d'origine

solution[procS] <- solution[procS] - task
solution[procD] <- solution[procD] + task
```
 
* Affichage des n solutions finales avec n entré par l'utilisateur (exemple d'affichage:)
```
** --------------------------------------------- 
** Solution at iteration n°13 : 
**     0: [0, 6, 8, 11, 11, 15, 24, 38, 59, 63, 72, 83, 94]
**     1: [3, 10, 20, 20, 30, 34, 42, 42, 46, 74, 81, 82]
**     2: [5, 17, 36, 45, 56, 67, 81, 84, 93]
**     3: [25, 73, 77, 80, 95, 95]
**     4: [7, 21, 24, 45, 47, 54, 58, 63, 79, 85]
**
** Current c_max: 484 (solution seen 8 time.s)
** --------------------------------------------- 
```

## Recuit simulé
* Température initiale entrée par l'utilisateur
* Température minimale avant arrêt de 0.01, refroidissement de 1% par itération
* Choix de l'état voisin aléatoire:
    * Génération aléatoire de l'état plutot que choix aléatoire d'un état dans tous ceux générés
    * Choix aléatoire du processeur source, destination et de la tache

## Recherche taboue
* Taille du tableau de base à spécifier par l'utilisateur
* Choix du successeur:
    * Generation de successeurs: 
    tous les mouvements possibles avec la configuration courrante (permutations)
    * Comparaisons de tous les voisins et sauvegarde du meilleur d'entre tous

# Sources
## Books
* `None`

## Web
### Recherche taboue
* [University article](https://homepages.laas.fr/huguet/drupal/sites/homepages.laas.fr.huguet/files/u78/5IL_BOC_Recherche_tabou.pdf)

### Recuit simulé
* [Katrina Ellison Geltman](http://katrinaeg.com/simulated-annealing.html)

## Autres
* [Our lessons](https://arche.univ-lorraine.fr/pluginfile.php/978758/mod_resource/content/3/6-Metaheuristiques.pdf)
    
