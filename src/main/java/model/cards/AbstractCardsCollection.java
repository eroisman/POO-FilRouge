package model.cards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import allShared.ICardsCollection;

/**
 * Classe qui décrit les attributs et méthodes commun(e)s à toutes les Collections de cartes
 * elle implémente l'interface Iterable pour être parcourue par un Iterator [Design pattern]
 * 
 * Elle s'appuie sur les méthodes de sa Collection enveloppée (ici List)  
 * enrichies des méthodes shuffle(), max() et iterator()
 * [On peut la voir comme un adapter de List - Design Pattern]
 * 
 * [Question du Carnet de Bord : qu'est-ce qui justifie l'existence de cette classe ?
 * La réponse n'est pas "... factoriser le code commun à ses classes dérivées" 
 * (même si cette phrase est néanmoins vraie ;-)) ]
 * 
 * @author francoise.perrin
 */
public abstract class AbstractCardsCollection implements ICardsCollection {

	protected final List<Card> cards;

	public AbstractCardsCollection() {
		super();
		this.cards = new ArrayList<Card>();
	}

	public AbstractCardsCollection(Collection<Card> collection) {
		super();
		this.cards = new ArrayList<Card>(collection);
	}

	public AbstractCardsCollection(ICardsCollection iCardsCollection) {
		super();
		this.cards = new ArrayList<Card>();
		for (Card card : iCardsCollection) {
			this.cards.add(card);
		}
	}

	@Override
	public void shuffle() {
		Collections.shuffle(cards);
	}

	@Override
	public Card removeTopCard() {
		return cards.isEmpty() ? null : cards.remove(0);
	}

	@Override
	public Card removeCard(int index) {
		if (index >= 0 && index < cards.size()) {
			return cards.remove(index);
		}
		return null;
	}

	@Override
	public void addCard(Card pc) {
		cards.add(pc);
	}

	@Override
	public void clear() {
		cards.clear();
	}

	@Override
	public int size() {
		return cards.size();
	}

	@Override
	public Card max() {
		if (cards.isEmpty()) {
			return null;
		}
		return Collections.max(cards);
	}

	@Override
	public Card max(Comparator<Card> comparator) {
		if (cards.isEmpty()) {
			return null;
		}
		return Collections.max(cards, comparator);
	}

	@Override
	public void sort() {
		Collections.sort(cards);
	}

	@Override
	public void sort(Comparator<Card> comparator) {
		Collections.sort(cards, comparator);
	}

	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

	@Override
	public String toString() {
		return "[" + cards + "]";
	}
}
