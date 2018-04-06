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

# Critique des méthodes
## Recherche taboue
On utilisera ces données pour ce qui est de la recherche taboue:
```
== Config
** Current algorithm: Tabu search
** Tasks durations  : [40, 0, 84, 12, 64, 3, 0, 12, 26, 22, 17, 
                        87, 38, 9, 3, 31, 12, 14, 15, 79, 84, 65, 
                        73, 73, 15, 14, 72, 25, 32, 94, 50, 73, 80, 
                        7, 32, 79, 48, 26, 9, 60, 81, 96, 59, 9, 
                        29, 13, 74, 81, 47, 0]
** Processors number: 5
```

On différencie deux cas pour la recherche taboue. 
Le premier est en utilisant une liste taboue petite (ici de 5):
```
** --------------------------------------------- 
** Solution at iteration n°61 : 
**     0: [0, 0, 0, 3, 3, 7, 9, 9, 12, 12, 12, 13, 14, 14, 15, 15, 22, 25, 26, 29, 31, 32, 32, 65]
**     1: [38, 40, 73, 81, 87, 96]
**     2: [9, 17, 50, 81, 84, 84, 94]
**     3: [47, 48, 59, 60, 64, 72, 73]
**     4: [26, 73, 74, 79, 79, 80]
**
** Current c_max: 423 (solution seen 11 time.s)
** --------------------------------------------- 
** Found in 71ms
```
Le second est pour une configuration identique mais une liste taboue beaucoup plus grande (ici de 500)
```
** --------------------------------------------- 
** Solution at iteration n°65 : 
**     0: [0, 0, 0, 3, 3, 7, 9, 9, 9, 12, 12, 12, 13, 14, 14, 15, 22, 26, 26, 29, 31, 32, 32, 38, 50]
**     1: [17, 40, 47, 48, 80, 87, 96]
**     2: [59, 81, 81, 84, 94]
**     3: [60, 64, 65, 72, 73, 84]
**     4: [15, 25, 73, 73, 74, 79, 79]
**
** Current c_max: 418 (solution seen 11 time.s)
** --------------------------------------------- 
** Found in 69ms
```

On peut donc noter que la recherche taboue est plus efficace lorsque sa liste est grande. 
On peut expliquer ces résultats par le fait qu'une liste taboue petite entraine la suite des minimas locaux et 
des cycles plutot que de continuer l'exploration de l'espace de recherche.

## Recuit simulé
On utilisera ces données pour ce qui est du recuit simulé
```
== Config
** Current algorithm: Simulated annealing
** Tasks durations  : [40, 58, 62, 19, 96, 42, 51, 41, 34, 19, 
                        66, 72, 99, 40, 90, 81, 23, 82, 35, 61, 
                        13, 38, 73, 2, 3, 43, 30, 98, 78, 7, 49, 
                        21, 87, 95, 70, 64, 64, 12, 76, 63, 52, 
                        55, 81, 6, 57, 17, 70, 81, 7, 0]
** Processors number: 5
```
Les cas que l'on différencie ici sont lors d'une température de base élevée et lors d'une température de base faible
Prenons par exemple le cas d'une température initiale à 0.5
```
** --------------------------------------------- 
** Solution at iteration n°390 : 
**     0: [87, 0, 72, 81, 55, 66, 73, 30, 21, 90, 43, 62, 78]
**     1: [61, 35, 76, 7, 2, 63, 70]
**     2: [7, 12, 64, 42, 6, 40, 52, 19, 17, 23, 13, 57, 51, 98, 96, 19, 70, 38, 41]
**     3: [58, 81, 64, 95, 34]
**     4: [81, 82, 40, 3, 49, 99]
**
** Current c_max: 765 (solution seen 0 time.s)
** --------------------------------------------- 
** Found in 56ms
```
Relançons ce test avec maintenant une température initiale de 10:
```
** --------------------------------------------- 
** Solution at iteration n°688 : 
**     0: [6, 81, 62, 64, 38, 99, 19, 96, 42, 0, 78, 13, 40]
**     1: [34, 7, 87, 61, 66, 41, 81, 49]
**     2: [63, 95, 35, 17, 81, 90, 7, 43, 2, 70, 70, 73, 51]
**     3: [55, 40, 58, 12, 98, 82, 23, 21, 19, 64, 76, 52]
**     4: [72, 57, 3, 30]
**
** Current c_max: 697 (solution seen 0 time.s)
** --------------------------------------------- 
** Found in 37ms
```

On peut noter que la recherche est plus précise lorsque la température est plus élevée.
Ces résultats s'expliquent par le fait qu'une température haute à le temps d'explorer aussi ce qui 
est des mauvais voire des tres mauvais résultats, pour échapper aux minima locaux.
Le problème d'une température faible est alors son rejet quasi systématique des autres voisins ce
qui lui fait épouser le minima local.

# Comparaison
Comparons maintenant la recherche tabou avec une petite puis une grande liste et ensuite le 
recuit simulé avec une petite puis une grande température initiale pour un jeu de données comprenant:
- 15 processeurs
- 75 taches
## Tabou à petite liste (10)
Resultats:
```
** FINAL SOLUTION
** Solution at iteration n°34 : 
**     0: [19, 38, 45, 52, 54, 64]
**     1: [20, 23, 37, 50, 63, 75]
**     2: [37, 95]
**     3: [13, 26, 78, 87]
**     4: [10, 32, 47, 64, 82, 95]
**     5: [57, 60, 62, 66, 90]
**     6: [22, 48, 85, 96, 99]
**     7: [16, 29, 62, 67, 79, 93]
**     8: [0, 21, 22, 52, 68, 92, 93]
**     9: [15, 70, 79, 91, 95]
**     10: [9, 14, 19, 31, 33, 38, 59, 69, 71]
**     11: [16, 39, 48, 63, 77, 94]
**     12: [1, 8, 8, 9, 21, 57, 77]
**     13: []
**     14: [82]
**
** Current c_max: 350 (solution seen 26 time.s)
** Found in 423ms
```
## Tabou à grande liste (1000)
Resultats:
```
** FINAL SOLUTION
** Solution at iteration n°40 : 
**     0: [32, 48, 77, 79]
**     1: [15, 22, 23, 33, 59, 93]
**     2: [9, 21, 78, 82, 85]
**     3: [14, 16, 21, 50, 62, 93]
**     4: [13, 19, 37, 38, 64, 67]
**     5: [1, 10, 47, 48, 52, 52, 77]
**     6: [8, 90, 91, 99]
**     7: [39, 66, 75, 79]
**     8: [0, 62, 63, 64, 92]
**     9: [45, 63, 68, 71]
**     10: [22, 26, 38, 82, 95]
**     11: [8, 16, 19, 29, 31, 87, 95]
**     12: [37, 60, 95, 96]
**     13: [9, 20, 54, 69]
**     14: [57, 57, 70, 94]
**
** Current c_max: 288 (solution seen 26 time.s)
** Found in 500ms
```
## Recuit à faible température initiale (1)
Resultats:
```
** FINAL SOLUTION
** Solution at iteration n°465 : 
**     0: []
**     1: [19, 21, 70, 78]
**     2: [96, 9, 68, 95, 26, 48]
**     3: [71, 52, 14, 93, 82, 20, 31, 77, 95, 1, 64, 37]
**     4: [0, 94]
**     5: [87, 50, 16]
**     6: [66, 45, 9, 22]
**     7: [60, 47, 57, 79, 38, 16]
**     8: [48, 85, 79, 23, 99, 69, 21]
**     9: [10, 29, 64, 95, 32]
**     10: [8, 52, 39, 77, 62, 8, 90, 63]
**     11: [59, 93, 63, 57, 13, 75]
**     12: []
**     13: [67]
**     14: [38, 33, 22, 92, 37, 82, 54, 15, 19, 91, 62]
**
** Current c_max: 637 (solution seen 4 time.s)
** Found in 7ms
```
## Recuit à grande température initiale (100)
Resultats:
```
** FINAL SOLUTION
** Solution at iteration n°928 : 
**     0: [21, 69, 71]
**     1: [79, 57, 99, 1]
**     2: [39, 37, 13, 85, 92, 20, 45, 63, 70, 10]
**     3: []
**     4: [48, 87, 94, 38, 48, 93, 14, 23]
**     5: [60, 82, 77, 15, 29, 52, 77]
**     6: [9, 16, 78, 68, 16, 52, 22]
**     7: [93, 64, 22, 32, 95, 62, 95, 59]
**     8: [57, 54, 0, 95, 8]
**     9: [47, 79, 91, 75, 31, 67, 38, 9, 66, 62]
**     10: []
**     11: [19, 33, 50]
**     12: [21, 37, 90, 19, 63, 8]
**     13: []
**     14: [64, 82, 96, 26]
**
** Current c_max: 565 (solution seen 0 time.s)
** Found in 7ms
```
## Conclusion
On voit donc que l'heuristique de la recherche taboue est plus efficace sur de grandes données (toutes
proportions gardées pour cet exemple) comparé au recuit simulé. Les deux méthodes sont moins performantes
avec leurs paramètres faibles.


Cela peut s'expliquer par une trop grande variation des résultat et donc un blocage dans une zone non 
optimale pour le recuit par rapport au tabou, ou alors à cause de l'implémentation de ces deux algorithmes 
dans notre code.

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
    
