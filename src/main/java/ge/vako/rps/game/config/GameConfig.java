package ge.vako.rps.game.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br/><br/>
 * Created by <b> Vako Beridze &lt;vako.beridze@gmail.com&gt; </b> <br/>
 * Created at  <b> 7/29/19 </b> <br/>
 */
@Getter
@AllArgsConstructor
public class GameConfig {
	private int movesToRemember;
	private double adaptChangesPercentage;
}
