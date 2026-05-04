package model.cards;

import java.util.Objects;

import allShared.ICard;

/**
 * Cette classe peut être vue comme un Proxy ou un Adapter [Design Pattern] de la classe Card
 * Ses instances sont utilisées par la View qui
 * n'a accès qu'aux "Getters" de l'objet encapsulé
 * 
 * Cette classe restreint donc le nombre de fonctionnalités existantes de l'objet enveloppé
 * et lui délègue le soin de réaliser les traitements
 * 
 * @author francoise.perrin
 */
public class CardRender implements ICard, Comparable<CardRender> {
	private final Card card;
	
	public CardRender(Card card) {
		super();
		this.card = card;
	}

	@Override
	public final Rank getRank() {
		// Retourne null si la carte n'est pas révélée
		return card.isRevealed() ? card.getRank() : null;
	}

	@Override
	public final Suit getSuit() {
		// Retourne null si la carte n'est pas révélée
		return card.isRevealed() ? card.getSuit() : null;
	}
	
	@Override
	public final boolean isRevealed() {
		return card.isRevealed();
	}
	
	@Override
	public int compareTo(CardRender o) {
		// Retourne -99999 si la carte n'est pas révélée
		if (!card.isRevealed()) {
			return -99999;
		}
		return this.card.compareTo(o.card);
	}
// regénérer equals et hashcode avec IDE
	@Override
	public int hashCode() {
		return Objects.hash(card);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardRender other = (CardRender) obj;
		return Objects.equals(card, other.card);
	}

	@Override
	public String toString() {
		// Retourne ?-? si la carte n'est pas révélée
		return card.isRevealed() ? card.toString() : "?-?";
	}
}

