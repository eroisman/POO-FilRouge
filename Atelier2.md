# Atelier 2 - Collections, Comparator, Evaluators, Renderers

## 1. Contexte

Objectif de l'atelier :

- factoriser les opérations communes des collections de cartes,
- implémenter `Deck` et `Board` sur cette base,
- implémenter les comparateurs et évaluateurs de pli,
- implémenter les classes proxy `CardRender` et `PlayerRender`,
- implémenter le retour des cartes gagnées dans la main du joueur.

## 2. Fichiers modifiés

- [src/main/java/model/cards/AbstractCardsCollection.java](src/main/java/model/cards/AbstractCardsCollection.java)
- [src/main/java/model/cards/Hand.java](src/main/java/model/cards/Hand.java)
- [src/main/java/model/cards/Deck.java](src/main/java/model/cards/Deck.java)
- [src/main/java/model/cards/Board.java](src/main/java/model/cards/Board.java)
- [src/main/java/model/cards/NewWarGameCardComparator.java](src/main/java/model/cards/NewWarGameCardComparator.java)
- [src/main/java/model/cards/CardRender.java](src/main/java/model/cards/CardRender.java)
- [src/main/java/model/cards/Card.java](src/main/java/model/cards/Card.java)
- [src/main/java/model/games/AbstractGameEvaluator.java](src/main/java/model/games/AbstractGameEvaluator.java)
- [src/main/java/model/games/ClassicWarGameEvaluator.java](src/main/java/model/games/ClassicWarGameEvaluator.java)
- [src/main/java/model/games/NewWarGameEvaluator.java](src/main/java/model/games/NewWarGameEvaluator.java)
- [src/main/java/model/player/Player.java](src/main/java/model/player/Player.java)
- [src/main/java/model/player/PlayerRender.java](src/main/java/model/player/PlayerRender.java)

## 3. Détail des changements

### 3.1 Factorisation dans `AbstractCardsCollection`

Fichier : [src/main/java/model/cards/AbstractCardsCollection.java](src/main/java/model/cards/AbstractCardsCollection.java)

Ajouts :
- stockage commun des cartes via `List<Card> cards`,
- constructeurs : vide, depuis `Collection<Card>`, et par copie depuis `ICardsCollection`,
- implémentation commune de :
  - `addCard`,
  - `removeTopCard`,
  - `removeCard`,
  - `clear`,
  - `size`,
  - `isEmpty`,
  - `sort`,
  - `sort(Comparator)`,
  - `shuffle`,
  - `max`,
  - `max(Comparator)`,
  - `iterator`,
  - `toString`.

Effet : `Board`, `Deck` et `Hand` peuvent réutiliser les mêmes opérations sans duplication.

### 3.2 Classe `Hand`

Fichier : [src/main/java/model/cards/Hand.java](src/main/java/model/cards/Hand.java)

Changements :
- `Hand` hérite maintenant de `AbstractCardsCollection`,
- constructeurs alignés sur les constructeurs parents (dont copie `ICardsCollection`),
- `sort()` utilise `NewWarGameCardComparator` pour un ordre déterministe conforme à la trace,
- `sort(Comparator)`, `shuffle()`, `max()`, `max(Comparator)` reliés à la logique Atelier 2,
- les méthodes spécifiques `Hand` restent : `playCard`, `revealeCard`, `hideCard`.

### 3.3 Classe `Deck`

Fichier : [src/main/java/model/cards/Deck.java](src/main/java/model/cards/Deck.java)

Changements :
- `Deck` hérite de `AbstractCardsCollection`,
- implémentation du constructeur `Deck(int deckSize)` :
  - deck 52 : toutes les valeurs,
  - deck 32 : de 7 à As,
- constructeur de copie `Deck(ICardsCollection)` implémenté,
- `max()` surchargé pour un résultat déterministe quand plusieurs cartes ont même rang.

### 3.4 Classe `Board`

Fichier : [src/main/java/model/cards/Board.java](src/main/java/model/cards/Board.java)

Changements :

- `Board` hérite de `AbstractCardsCollection`,
- constructeurs vide, depuis `Collection<Card>` et par copie `ICardsCollection` implémentés,
- opérations communes héritées (pas de duplication locale).

### 3.5 Visibilité des cartes pour Atelier 2

Fichier : [src/main/java/model/cards/Card.java](src/main/java/model/cards/Card.java)

Changement :

- dans le constructeur, la carte est créée visible (`isFaceUp = true`), conformément à la précondition de `TestAtelier2`.

Remarque :

- pour rejouer strictement Atelier 1 ensuite, il faut repasser cette valeur à `false`.

### 3.6 Comparator et évaluateurs

Fichiers :

- [src/main/java/model/cards/NewWarGameCardComparator.java](src/main/java/model/cards/NewWarGameCardComparator.java)
- [src/main/java/model/games/AbstractGameEvaluator.java](src/main/java/model/games/AbstractGameEvaluator.java)
- [src/main/java/model/games/ClassicWarGameEvaluator.java](src/main/java/model/games/ClassicWarGameEvaluator.java)
- [src/main/java/model/games/NewWarGameEvaluator.java](src/main/java/model/games/NewWarGameEvaluator.java)

Changements :

- `NewWarGameCardComparator.compare` : comparaison par rang puis couleur,
- `AbstractGameEvaluator.evaluateTrickWinner` : template method activée,
- `ClassicWarGameEvaluator` : max/comparaison via ordre naturel,
- `NewWarGameEvaluator` : max/comparaison via `NewWarGameCardComparator`.

### 3.7 Renderers (proxy read-only)

Fichiers :

- [src/main/java/model/cards/CardRender.java](src/main/java/model/cards/CardRender.java)
- [src/main/java/model/player/PlayerRender.java](src/main/java/model/player/PlayerRender.java)

Changements :
- délégation des getters et états vers l'objet enveloppe,
- `compareTo`, `equals`, `hashCode`, `toString` implémentés,
- objectif : exposer une vue limitée pour la couche `view`.

### 3.8 `Player.addWonCardsBackToHand`

Fichier : [src/main/java/model/player/Player.java](src/main/java/model/player/Player.java)

Changements :

- implémentation de `addWonCardsBackToHand()` :
  - parcours des cartes du `trickPile` via itérateur implicite,
  - ajout en main,
  - vidage du `trickPile`,
  - tri de la main avec le comparateur Atelier 2 pour obtenir une trace stable.

## 4. Vérification

- Les diagnostics IDE sur les fichiers modifiés sont sans erreur.
- Le scénario `TestAtelier2` a été exécuté pendant l'implémentation et les checks métier sont conformes à la trace attendue (collections, comparateur, évaluateurs, renderers, itérateur).
- En fin de session, la capture de sortie terminal VS Code n'affichait plus de texte pour les commandes, donc la dernière relance console n'a pas pu être re-capturée dans cette session.

## 5. État final

Atelier 2 est implémenté sur la partie `model` demandée :

- factorisation des collections,
- `Deck`/`Board`/`Hand` fonctionnels avec copie,
- comparateur et évaluateurs opérationnels,
- `CardRender` et `PlayerRender` implémentés,
- transfert des plis vers la main du joueur implémenté.
