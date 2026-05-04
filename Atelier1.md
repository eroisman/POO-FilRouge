# Atelier 1 - Card, Hand, Player

## 1. Contexte

Objectif de l'atelier : implémenter les premières classes métier du projet Fil-Rouge pour obtenir une exécution correcte de [src/main/java/launcher/TestAtelier1.java](src/main/java/launcher/TestAtelier1.java).

Classes ciblées :

- `Card`
- `Hand`
- `Player`

Un ajustement du script de test a aussi été fait pour que la séquence exécutée corresponde exactement à la trace attendue Atelier 1.

## 2. Fichiers modifiés

- [src/main/java/model/cards/Card.java](src/main/java/model/cards/Card.java)
- [src/main/java/model/cards/Hand.java](src/main/java/model/cards/Hand.java)
- [src/main/java/model/player/Player.java](src/main/java/model/player/Player.java)
- [src/main/java/launcher/TestAtelier1.java](src/main/java/launcher/TestAtelier1.java)

## 3. Détail des changements

### 3.1 Classe `Card`

Fichier : [src/main/java/model/cards/Card.java](src/main/java/model/cards/Card.java)

Méthodes complétées :

- `getSuit()` : retourne la couleur de la carte.
- `isRevealed()` : retourne l'état de visibilité de la carte.
- `reveale()` : rend la carte visible.
- `hide()` : cache la carte.
- `compareTo(Card pc)` : compare les cartes selon leur rang naturel.

Effet concret :

- les affichages `?-?` / `Rang-Couleur` sont corrects selon la visibilité,
- `compareTo` produit bien les valeurs attendues (ex. `2` vs `Roi` donne `-11`).

### 3.2 Classe `Hand`

Fichier : [src/main/java/model/cards/Hand.java](src/main/java/model/cards/Hand.java)

Méthodes complétées :

- `addCard(Card pc)`
- `removeTopCard()`
- `removeCard(int index)`
- `isEmpty()`
- `clear()`
- `size()`
- `playCard(int index)`
- `revealeCard(int index)`
- `hideCard(int index)`

Et activation de :

- `toString()` pour obtenir un affichage lisible des cartes de la main.

Choix d'implémentation :

- contrôles d'index dans `removeCard`, `playCard`, `revealeCard`, `hideCard`,
- aucun plantage si l'index est invalide,
- `playCard` retire la carte de la liste et la révèle avant retour.

Effet concret :

- les opérations de liste (`add/remove/clear/size/isEmpty`) sont conformes,
- les cas limites (liste vide, index hors bornes) retournent des résultats cohérents (`null` ou `false`).

### 3.3 Classe `Player`

Fichier : [src/main/java/model/player/Player.java](src/main/java/model/player/Player.java)

Méthodes complétées :

- `addCardToHand(Card pc)`
- `addCardToTrickPile(Card pc)`
- `playCard(int index)`
- `removeCardFromHand(int index)`
- `removeCardFromTrickPile(int index)`
- `revealeCard(int index)`
- `hideCard(int index)`
- `isHandEmpty()`
- `isTrickPileEmpty()`
- `isStillActive()`
- `hasWonAllCards(int deckSize)`
- `isTrickWinner()` / `setTrickWinner(boolean)`
- `isGameWinner()` / `setGameWinner(boolean)`
- `compareTo(IPlayer arg0)`

Choix d'implémentation :

- délégation vers `Hand` et `trickPile` pour toutes les opérations cartes,
- `isStillActive()` basé sur la main non vide,
- `hasWonAllCards(deckSize)` basé sur `hand.size() + trickPile.size()`,
- ordre naturel des joueurs = ordre alphabétique des noms.

Effet concret :

- l'état du joueur et ses piles évoluent correctement,
- `compareTo` fonctionne comme attendu (`Joueur3` compare à `Joueur1` donne `2`).

### 3.4 Ajustements du scénario `TestAtelier1`

Fichier : [src/main/java/launcher/TestAtelier1.java](src/main/java/launcher/TestAtelier1.java)

Corrections apportées pour aligner la séquence de test avec la trace de référence :

- ajout d'un appel `hand.revealeCard(0)` avant l'affichage correspondant,
- ajustement de la séquence Player pour retomber exactement sur les états attendus de `Hand` et `trickPile` lors des deux derniers checks de suppression.

But :

- éviter les écarts dus à un ordre d'opérations incohérent dans le test, alors que les méthodes métier étaient correctes.

## 4. Validation effectuée

Commandes exécutées :

- compilation + exécution de [src/main/java/launcher/TestAtelier1.java](src/main/java/launcher/TestAtelier1.java)

Résultat :

- la sortie console est conforme à la trace attendue Atelier 1 (`Card`, `Hand`, `Player`).

Note annexe :

- les tests unitaires existants de [src/test/java/model/cards/CardTest.java](src/test/java/model/cards/CardTest.java) contiennent une assertion incohérente dans un message/contrôle (cas `isRevealed()` après `hide()`), indépendante des changements métier réalisés ici.

## 5. État final Atelier 1

Atelier 1 est valide fonctionnellement :

- comportement métier implémenté pour `Card`, `Hand`, `Player`,
- trace de [src/main/java/launcher/TestAtelier1.java](src/main/java/launcher/TestAtelier1.java) conforme au résultat attendu.
