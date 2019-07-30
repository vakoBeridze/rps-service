package ge.vako.rps.model;

import ge.vako.rps.game.markovchain.MarkovPossibleMoves;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <br/><br/>
 * Created by <b> Vako Beridze &lt;vako.beridze@gmail.com&gt; </b> <br/>
 * Created at  <b> 7/30/19 </b> <br/>
 */

@Data
@Builder
public class BotThoughtsModel {

	private List<String> history;
	private Map<String, MarkovPossibleMoves> thoughts;

}
