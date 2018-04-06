# Rapport d'optimisation

## Choix de modélisation par méthodes

## Généralités
* Données de bases :
    * Nombre de processeurs à définir par l'utilisateur (si 0, aléatoire)
    * Nombre de taches à définir par l'utilisateur
    * La durée de chaque tache est aléatoire de 0 jusqu'à une limite fixée par l'utilisateur

* Un etat est représenté par un dictionnaire de forme:
```python
solution = {
    proc0: [task0, task1],
    proc1: [task2, task3],
    # ...
}
```

* Le critère d'arrêt commun est le dépassement nombre d'itération (d'un maximum de 1000)

* Un mouvement est calculé comme le passage d'une tache d'un processeur à un autre:
```
procS <- choix du processeur d'origine
procD <- choix du processeur destination
task  <- tache du processeur d'origine

solution[procS] <- solution[procS] - task
solution[procD] <- solution[procD] + task
```

* Affichage des n solutions finales avec n entré par l'utilisateur (exemple d'affichage:)
```python
** ---------------------
** [Solution n°6] CMax: 21
**
**  0: [8, 10]
**      total: 18
**  1: [9, 10, 2]
**      total: 21
**  2: [7, 5, 6, 3]
**      total: 21
**  3: [9, 10]
**      total: 19
**  4: [7, 4, 5, 5]
**      total: 21
**
```

## Recuit simulé
* Température initiale de __ (modifiable par l'utilisateur)
* Température minimale avant arrêt de __ (modifiable par l'utilisateur)
* Choix de l'état voisin aléatoire:
    * Génération aléatoire de l'état plutot que choix aléatoire d'un état dans tous ceux générés

## Recherche taboue
* Taille du tableau de base à spécifier par l'utilisateur

# Sources
## Books
    `None`

## Web
### Recherche taboue
    * [University article] https://homepages.laas.fr/huguet/drupal/sites/homepages.laas.fr.huguet/files/u78/5IL_BOC_Recherche_tabou.pdf

### Recuit simulé
    * [Katrina Ellison Geltman] http://katrinaeg.com/simulated-annealing.html

## Autres
    * [Our lessons] (https://arche.univ-lorraine.fr/pluginfile.php/978758/mod_resource/content/3/6-Metaheuristiques.pdf)
    