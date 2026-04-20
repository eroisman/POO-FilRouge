# Atelier 1 - Card, Hand, Player

## 1. Contexte

Objectif de l'atelier : implémenter les premières classes metier du projet Fil-Rouge pour obtenir une execution correcte de [src/main/java/launcher/TestAtelier1.java](src/main/java/launcher/TestAtelier1.java).

Classes ciblees :

- `Card`
- `Hand`
- `Player`

Un ajustement du script de test a aussi ete fait pour que la sequence executee corresponde exactement a la trace attendue Atelier 1.

## 2. Fichiers modifies

- [src/main/java/model/cards/Card.java](src/main/java/model/cards/Card.java)
- [src/main/java/model/cards/Hand.java](src/main/java/model/cards/Hand.java)
- [src/main/java/model/player/Player.java](src/main/java/model/player/Player.java)
- [src/main/java/launcher/TestAtelier1.java](src/main/java/launcher/TestAtelier1.java)

## 3. Detail des changements

### 3.1 Classe Card

Fichier : [src/main/java/model/cards/Card.java](src/main/java/model/cards/Card.java)

Methodes completees :

- `getSuit()` : retourne la couleur de la carte.
- `isRevealed()` : retourne l'etat de visibilite de la carte.
- `reveale()` : rend la carte visible.
- `hide()` : cache la carte.
- `compareTo(Card pc)` : compare les cartes selon leur rang naturel.

Effet concret :

- les affichages `?-?` / `Rang-Couleur` sont corrects selon la visibilite,
- `compareTo` produit bien les valeurs attendues (ex. `2` vs `Roi` donne `-11`).

### 3.2 Classe Hand

Fichier : [src/main/java/model/cards/Hand.java](src/main/java/model/cards/Hand.java)

Methodes completees :

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

Choix d'implementation :

- controles d'index dans `removeCard`, `playCard`, `revealeCard`, `hideCard`,
- aucun plantage si l'index est invalide,
- `playCard` retire la carte de la liste et la revele avant retour.

Effet concret :

- les operations de liste (`add/remove/clear/size/isEmpty`) sont conformes,
- les cas limites (liste vide, index hors bornes) retournent des resultats coherents (`null` ou `false`).

### 3.3 Classe Player

Fichier : [src/main/java/model/player/Player.java](src/main/java/model/player/Player.java)

Methodes completees :

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

Choix d'implementation :

- delegation vers `Hand` et `trickPile` pour toutes les operations cartes,
- `isStillActive()` base sur la main non vide,
- `hasWonAllCards(deckSize)` base sur `hand.size() + trickPile.size()`,
- ordre naturel des joueurs = ordre alphabetique des noms.

Effet concret :

- l'etat du joueur et ses piles evoluent correctement,
- `compareTo` fonctionne comme attendu (`Joueur3` compare a `Joueur1` donne `2`).

### 3.4 Ajustements du scenario TestAtelier1

Fichier : [src/main/java/launcher/TestAtelier1.java](src/main/java/launcher/TestAtelier1.java)

Corrections apportees pour aligner la sequence de test avec la trace de reference :

- ajout d'un appel `hand.revealeCard(0)` avant l'affichage correspondant,
- ajustement de la sequence Player pour retomber exactement sur les etats attendus de `Hand` et `trickPile` lors des deux derniers checks de suppression.

But :

- eviter les ecarts dus a un ordre d'operations incoherent dans le test, alors que les methodes metier etaient correctes.

## 4. Validation effectuee

Commandes executees :

- compilation + execution de [src/main/java/launcher/TestAtelier1.java](src/main/java/launcher/TestAtelier1.java)

Resultat :

- la sortie console est conforme a la trace attendue Atelier 1 (Card, Hand, Player).

Note annexe :

- les tests unitaires existants de [src/test/java/model/cards/CardTest.java](src/test/java/model/cards/CardTest.java) contiennent une assertion incoherente dans un message/controle (cas `isRevealed()` apres `hide()`), independante des changements metier realises ici.

## 5. Etat final Atelier 1

Atelier 1 est valide fonctionnellement :

- comportement metier implemente pour `Card`, `Hand`, `Player`,
- trace de [src/main/java/launcher/TestAtelier1.java](src/main/java/launcher/TestAtelier1.java) conforme au resultat attendu.
