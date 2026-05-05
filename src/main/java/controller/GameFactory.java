package controller;

import java.util.List;

import allShared.GameType;
import allShared.ICardsCollection;
import allShared.IGame;
import model.games.ClassicWarGame;
import model.games.NewWarGame;

/**
 * Factory qui permet de créer les différents types de Game
 * 
 * @author francoise.perrin
 */
public class GameFactory {
	
	/**
	 * Constructeur privé empêche la construction de plusieurs objets de ce type
	 * par invocation du constructeur (avec new) :
	 * il y aura donc au maximum 1 seule instance de cette classe
	 */
	private GameFactory() {
		
	}
	
	public static IGame getGame(GameType gameType, List<String> playersNames, ICardsCollection deck) {
	
        switch (gameType) {
            case WARGAME_CLASSIC: {
				ClassicWarGame game = new ClassicWarGame(playersNames, deck);
				game.initializeGame();
				return game;
			}
            case WARGAME_NEW: {
				NewWarGame game = new NewWarGame(playersNames, deck);
				game.initializeGame();
				return game;
			}
 //         case BELOTE: return new Belote(playersNames, deck);
		default:
			break;
          
        }
		ClassicWarGame game = new ClassicWarGame(playersNames, deck);
		game.initializeGame();
		return game; // Game par défaut
	}
}