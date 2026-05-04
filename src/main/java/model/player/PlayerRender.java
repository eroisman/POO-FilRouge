package model.player;

import java.util.Objects;

import allShared.IPlayer;

/**
 * Cette classe est un Proxy ou un Adapter [Design Pattern] de la classe Player
 * Ses instances sont utilisées par la View qui
 * n'a accès qu'aux "Getters" de l'objet encapsulé
 * 
 * Cette classe restreint donc le nombre de fonctionnalités existantes de l'objet enveloppé
 * et lui délègue le soin de réaliser les traitements
 * 
 * @author francoise.perrin
 *
 */
public class PlayerRender implements IPlayer {

	private final IPlayer player;
	
	public PlayerRender(IPlayer player) {
		this.player = player;
	}
	
	@Override
	public final String getName() {
		return player.getName();
	}
	
	@Override
	public final boolean isStillActive() {
		return player.isStillActive();
	}

	@Override
	public final boolean isTrickWinner() {
		return player.isTrickWinner();
	}
	

	@Override
	public boolean isGameWinner() {
		return player.isGameWinner();
	}

	@Override
	public String toString() {
		return player.getName();
	}

	@Override
	public int hashCode() {	
		return Objects.hash(player);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerRender other = (PlayerRender) obj;
		return Objects.equals(player, other.player);
	}

	@Override
	public int compareTo(IPlayer o) {
		return this.player.compareTo(o);
	}


}
