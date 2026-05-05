# Diagrammes d'Architecture de l'Application

Ce document contient trois diagrammes Mermaid pour l'application actuelle :

- Diagramme de classes complet
- Diagramme de sequence principal d'execution
- Diagramme de structure globale (packages/modules)

## 1. Diagramme de Classes Complet

```mermaid
classDiagram

class IGame {
  <<interface>>
  +PlayCards(Map~String,Integer~)
  +evaluateTrickWinner() boolean
  +getGamingMatRender() Map~IPlayer,ICard~
  +isGameEnd() boolean
  +theWinnerTakesItAll()
  +theWinnerIs() PlayerRender
}

class IGameView {
  <<interface>>
  +isMasterView() boolean
  +getGameType() GameType
  +getPlayersName() List~String~
  +getDeckType() CardsCollectionType
  +chooseCardsToPlay() Map~String,Integer~
  +showGamingMatAndTrickWinner(Map~IPlayer,ICard~)
  +showWinner(IPlayer)
  +addViewable(IGameView)
}

class ICardsCollection {
  <<interface>>
  +shuffle()
  +removeTopCard() Card
  +removeCard(index) Card
  +addCard(Card)
  +clear()
  +size() int
  +max() Card
  +max(Comparator~Card~) Card
  +sort()
  +sort(Comparator~Card~)
  +isEmpty() boolean
}

class ICard {
  <<interface>>
  +getRank() Rank
  +getSuit() Suit
  +isRevealed() boolean
}

class IPlayer {
  <<interface>>
  +getName() String
  +isStillActive() boolean
  +isTrickWinner() boolean
  +isGameWinner() boolean
}

class IGameEvaluator {
  <<interface>>
  +evaluateTrickWinner(ICardsCollection) Card
}

class GameLauncher {
  -views : IGameView
  -gameController : GameController
  +main(String[])
}

class GameController {
  -game : IGame
  -view : IGameView
  +run()
}

class GameFactory {
  +getGame(GameType, List~String~, ICardsCollection) IGame
}

class CardsCollectionFactory {
  +getCardsCollection(CardsCollectionType) ICardsCollection
}

class AbstractGame {
  <<abstract>>
  #deck : ICardsCollection
  #initDeckSize : int
  -board : ICardsCollection
  #players : List~Player~
  -gamingMatMap : Map~Player,Card~
  #dealCardsFromDeck(int)*
  +PlayCards(Map~String,Integer~)
  +evaluateTrickWinner() boolean
  +getGamingMatRender() Map~IPlayer,ICard~
  +theWinnerTakesItAll()
  +isGameEnd() boolean*
  +theWinnerIs() PlayerRender
  #getGameEvaluator() IGameEvaluator*
}

class AbstractWarGame {
  <<abstract>>
  #dealCardsFromDeck(int)
  +isGameEnd() boolean
  #getGameEvaluator() IGameEvaluator*
}

class ClassicWarGame {
  #getGameEvaluator() IGameEvaluator
}

class NewWarGame {
  #getGameEvaluator() IGameEvaluator
}

class AbstractGameEvaluator {
  <<abstract>>
  +evaluateTrickWinner(ICardsCollection) Card
  #max(ICardsCollection) Card*
  #comparaison(Card,Card) int*
}

class ClassicWarGameEvaluator {
  #max(ICardsCollection) Card
  #comparaison(Card,Card) int
}

class NewWarGameEvaluator {
  #max(ICardsCollection) Card
  #comparaison(Card,Card) int
}

class AbstractCardsCollection {
  <<abstract>>
  #cards : List~Card~
  +shuffle()
  +removeTopCard() Card
  +removeCard(int) Card
  +addCard(Card)
  +clear()
  +size() int
  +max() Card
  +max(Comparator~Card~) Card
  +sort()
  +sort(Comparator~Card~)
  +isEmpty() boolean
  +iterator() Iterator~Card~
}

class Deck {
  +Deck(int)
  +max() Card
}

class Board

class Hand {
  +playCard(int) Card
  +revealeCard(int) boolean
  +hideCard(int) boolean
}

class Card {
  -rank : Rank
  -suit : Suit
  -isFaceUp : boolean
  +reveale()
  +hide()
  +compareTo(Card) int
}

class NewWarGameCardComparator {
  +compare(Card,Card) int
}

class Player {
  -name : String
  -hand : Hand
  -trickPile : Hand
  -isTrickWinner : boolean
  -isGameWinner : boolean
  +playCard(int) Card
  +addWonCardsBackToHand()
  +hasWonAllCards(int) boolean
}

class CardRender {
  -card : Card
}

class PlayerRender {
  -player : IPlayer
}

class AbstractGameView {
  <<abstract>>
  -isMasterView : boolean
  #gameType : GameType
  #chooseGameType() GameType*
  #createAndShowView()*
  #display(StringBuilder)*
}

class GameConsoleView
class GameSwingView
class GameViews {
  -views : List~IGameView~
  -isMasterView : boolean
}

class GameType {
  <<enumeration>>
  WARGAME_CLASSIC
  WARGAME_NEW
  BELOTE
}

class CardsCollectionType {
  <<enumeration>>
  DECK52
  DECK32
  BOARD
  HAND
}

class Rank {
  <<enumeration>>
}

class Suit {
  <<enumeration>>
}

IGame <|.. AbstractGame
AbstractGame <|-- AbstractWarGame
AbstractWarGame <|-- ClassicWarGame
AbstractWarGame <|-- NewWarGame

IGameEvaluator <|.. AbstractGameEvaluator
AbstractGameEvaluator <|-- ClassicWarGameEvaluator
AbstractGameEvaluator <|-- NewWarGameEvaluator

ICardsCollection <|.. AbstractCardsCollection
AbstractCardsCollection <|-- Deck
AbstractCardsCollection <|-- Board
AbstractCardsCollection <|-- Hand

ICard <|.. Card
ICard <|.. CardRender
IPlayer <|.. Player
IPlayer <|.. PlayerRender

IGameView <|.. AbstractGameView
AbstractGameView <|-- GameConsoleView
AbstractGameView <|-- GameSwingView
IGameView <|.. GameViews

GameLauncher --> GameController
GameLauncher --> GameViews
GameViews o-- IGameView
GameController --> IGameView
GameController --> GameFactory
GameController --> CardsCollectionFactory
GameController --> IGame

GameFactory --> ClassicWarGame
GameFactory --> NewWarGame
CardsCollectionFactory --> Deck
CardsCollectionFactory --> Board
CardsCollectionFactory --> Hand

AbstractGame --> Player
AbstractGame --> Card
AbstractGame --> Board
AbstractGame --> IGameEvaluator
AbstractGame --> CardRender
AbstractGame --> PlayerRender

Player --> Hand
Card --> Rank
Card --> Suit
Hand --> NewWarGameCardComparator
NewWarGameEvaluator --> NewWarGameCardComparator
CardRender --> Card
PlayerRender --> IPlayer

GameConsoleView --> ConsoleTui
GameConsoleView --> GameType
GameConsoleView --> CardsCollectionType
GameSwingView --> GameType
GameSwingView --> CardsCollectionType
```

## 2. Diagramme de Sequence Principal (GameLauncher -> Fin de Partie)

```mermaid
sequenceDiagram
    autonumber

    actor U as Utilisateur
    participant GL as GameLauncher
    participant GV as GameViews (Composite)
    participant GCV as GameConsoleView (maitre)
    participant GSV as GameSwingView
    participant GC as GameController
    participant CCF as CardsCollectionFactory
    participant GF as GameFactory
    participant G as IGame (ClassicWarGame ou NewWarGame)
    participant GE as IGameEvaluator

    U->>GL: lance main()
    GL->>GV: new GameViews(false)
    GL->>GCV: new GameConsoleView(true)
    GL->>GSV: new GameSwingView(false)
    GL->>GV: addViewable(console)
    GL->>GV: addViewable(swing)
    GL->>GC: new GameController(GV)
    GL->>GC: run()

    GC->>GV: getGameType()
    GV->>GCV: getGameType()
    GCV-->>GV: WARGAME_CLASSIC ou WARGAME_NEW
    GV-->>GC: gameType

    GC->>GV: getPlayersName()
    GV->>GCV: getPlayersName()
    GCV-->>GV: [Joueur1, Joueur2]
    GV-->>GC: playersNames

    GC->>GV: getDeckType()
    GV->>GCV: getDeckType()
    GCV-->>GV: DECK32/DECK52
    GV-->>GC: deckType

    GC->>CCF: getCardsCollection(deckType)
    CCF-->>GC: paquet

    GC->>GF: getGame(gameType, playersNames, deck)
    GF-->>GC: instance de jeu

    loop tant que la partie n'est pas finie
      loop sous-tours jusqu'a pli gagne ou fin de partie
            GC->>GV: chooseCardsToPlay()
            GV->>GCV: chooseCardsToPlay()
        GCV-->>GV: null ou map
            GV-->>GC: whichCards

            GC->>G: PlayCards(whichCards)
            GC->>G: evaluateTrickWinner()
            G->>GE: evaluateTrickWinner(currentRoundCards)
        GE-->>G: winnerCard ou null
            G-->>GC: isTrickWon

            GC->>G: getGamingMatRender()
            G-->>GC: map<IPlayer, ICard>
        GC->>GV: showGamingMatAndTrickWinner(rendu)
        GV->>GCV: showGamingMatAndTrickWinner(rendu)
        GV->>GSV: showGamingMatAndTrickWinner(rendu)
        end

        GC->>G: theWinnerTakesItAll()
    end

    GC->>G: theWinnerIs()
    G-->>GC: winner (PlayerRender ou null)
    GC->>GV: showWinner(winner)
    GV->>GCV: showWinner(winner)
    GV->>GSV: showWinner(winner)
```

  ## 3. Diagramme de Structure (Packages et Dependances Principales)

```mermaid
flowchart LR
    subgraph LAUNCHER[launcher]
      GL[GameLauncher]
      T1[TestAtelier1]
      T2[TestAtelier2]
    end

    subgraph CONTROLLER[controller]
      GC[GameController]
      GF[GameFactory]
      CCF[CardsCollectionFactory]
    end

    subgraph VIEW[view]
      AGV[AbstractGameView]
      GCV[GameConsoleView]
      GSV[GameSwingView]
      GVS[GameViews]
      CT[ConsoleTui]
    end

    subgraph ALLSHARED[allShared]
      IGame
      IGameView
      ICardsCollection
      ICard
      IPlayer
      IGameEvaluator
      GT[GameType]
      CCT[CardsCollectionType]
    end

    subgraph MODELCARDS[model.cards]
      ACC[AbstractCardsCollection]
      DECK[Deck]
      BOARD[Board]
      HAND[Hand]
      CARD[Card]
      CR[CardRender]
      CMP[NewWarGameCardComparator]
      RANK[Rank]
      SUIT[Suit]
    end

    subgraph MODELGAMES[model.games]
      AG[AbstractGame]
      AWG[AbstractWarGame]
      CWG[ClassicWarGame]
      NWG[NewWarGame]
      AGE[AbstractGameEvaluator]
      CWE[ClassicWarGameEvaluator]
      NWE[NewWarGameEvaluator]
    end

    subgraph MODELPLAYER[model.player]
      P[Player]
      PR[PlayerRender]
    end

    GL --> GC
    GL --> GVS
    GC --> IGameView
    GC --> GF
    GC --> CCF
    GF --> IGame
    CCF --> ICardsCollection

    IGameView <--> GVS
    GVS --> GCV
    GVS --> GSV
    GCV --> AGV
    GSV --> AGV
    AGV --> IGameView

    GF --> CWG
    GF --> NWG
    CWG --> AWG
    NWG --> AWG
    AWG --> AG
    AG --> IGame
    AG --> IGameEvaluator
    AGE --> IGameEvaluator
    CWE --> AGE
    NWE --> AGE

    CCF --> DECK
    CCF --> BOARD
    CCF --> HAND
    ACC --> ICardsCollection
    DECK --> ACC
    BOARD --> ACC
    HAND --> ACC

    CARD --> ICard
    CR --> ICard
    PR --> IPlayer
    P --> IPlayer

    AG --> P
    AG --> CARD
    P --> HAND
    CARD --> RANK
    CARD --> SUIT
    NWE --> CMP
    HAND --> CMP
```
