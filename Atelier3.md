# Atelier 3 - Game, Controller et lancement complet

## 1. Contexte

Objectif de l’atelier : terminer la hiérarchie des jeux et brancher le modèle sur le contrôleur et les vues pour exécuter le fil rouge depuis [src/main/java/launcher/GameLauncher.java](src/main/java/launcher/GameLauncher.java).

Les points clés de l’atelier sont :

- compléter la classe abstraite [src/main/java/model/games/AbstractGame.java](src/main/java/model/games/AbstractGame.java),
- compléter la classe abstraite [src/main/java/model/games/AbstractWarGame.java](src/main/java/model/games/AbstractWarGame.java),
- remettre la carte en mode face cachée par défaut pour tester le retournement pendant le jeu,
- valider le scénario complet via le lanceur de l’application,
- rédiger le carnet de bord de l’atelier.

## 2. Fichiers modifiés

- [src/main/java/model/cards/Card.java](src/main/java/model/cards/Card.java)
- [src/main/java/model/games/AbstractGame.java](src/main/java/model/games/AbstractGame.java)
- [src/main/java/model/games/AbstractWarGame.java](src/main/java/model/games/AbstractWarGame.java)

## 3. Détail des changements

### 3.1 Classe `Card`

Fichier : [src/main/java/model/cards/Card.java](src/main/java/model/cards/Card.java)

Changement appliqué :

- remise de `isFaceUp` à `false` dans le constructeur.

Effet concret :

- les cartes sont créées cachées par défaut,
- le retournement se fait au moment du jeu via les méthodes métier existantes.

### 3.2 Classe `AbstractGame`

Fichier : [src/main/java/model/games/AbstractGame.java](src/main/java/model/games/AbstractGame.java)

Changements appliqués :

- la partie n’utilise plus le stub de test à 4 cartes,
- la taille initiale du deck est désormais lue depuis le paquet réel,
- la distribution est lancée au démarrage de la partie,
- `PlayCards(...)` pose les cartes jouées sur le board et met à jour la carte visible de chaque joueur,
- `evaluateTrickWinner()` délègue à l’évaluateur du jeu et tague le vainqueur du pli,
- `getGamingMatRender()` fabrique les objets de rendu `PlayerRender` et `CardRender`,
- `theWinnerTakesItAll()` transfère les cartes du board vers la pile de pli du vainqueur,
- `theWinnerIs()` retourne le joueur gagnant encapsulé pour la vue.

### 3.3 Classe `AbstractWarGame`

Fichier : [src/main/java/model/games/AbstractWarGame.java](src/main/java/model/games/AbstractWarGame.java)

Changements appliqués :

- distribution 1 par 1 des cartes à tous les joueurs,
- détection de fin de partie quand un joueur possède toutes les cartes du jeu,
- tag du vainqueur de la partie via `isGameWinner`.

## 4. Validation effectuée

- compilation du projet après les modifications,
- vérification de l’intégration métier autour du lancement complet.

## 5. État final

Atelier 3 est branché sur le modèle de jeu complet :

- les cartes repartent cachées par défaut,
- la distribution du deck est faite par le modèle,
- le tour de jeu, l’évaluation des plis et la récupération des cartes gagnées sont gérés dans la hiérarchie `Game`,
- le scénario de lancement complet peut maintenant s’appuyer sur `GameLauncher`.
