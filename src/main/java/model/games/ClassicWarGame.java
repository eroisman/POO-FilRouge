package model.games;

import java.util.List;

import allShared.ICardsCollection;
import allShared.IGame;
import allShared.IGameEvaluator;

/**
 * Jeu de Bataille qui utilise un évaluateur de type ClassicWarGameEvaluator
 * 
 * Il faudra discuter de la pertinence de cette classe...
 * 
 * @author francoise.perrin
 */
public class ClassicWarGame extends AbstractWarGame implements IGame {

	public ClassicWarGame(List<String> playersNames,ICardsCollection deck) {
		super(playersNames, deck);
	}

	protected final IGameEvaluator getGameEvaluator() {
		return new ClassicWarGameEvaluator();
	}


}
