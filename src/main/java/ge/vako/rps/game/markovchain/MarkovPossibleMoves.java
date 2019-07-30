package ge.vako.rps.game.markovchain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * <br/><br/>
 * Created by <b> Vako Beridze &lt;vako.beridze@gmail.com&gt; </b> <br/>
 * Created at  <b> 7/29/19 </b> <br/>
 */
@Getter
public class MarkovPossibleMoves {
	private Map<Character, MoveMetaData> moves;

	public MarkovPossibleMoves(Character[] gameMoves) {
		moves = new HashMap<>();
		for (Character gameMove : gameMoves) {
			moves.put(gameMove, new MoveMetaData(gameMoves.length));
		}
	}

	public void updateOccurrences(Character newMove, double adaptChangesPercentage) {
		moves.forEach((move, moveMetaData) -> {
			moveMetaData.setOccurrences(adaptChangesPercentage * moveMetaData.getOccurrences());
			if (move.equals(newMove)) {
				moveMetaData.setOccurrences(moveMetaData.getOccurrences() + 1);
			}
		});
	}

	public void updateProbabilities() {
		// sum total occurrences
		double totalOccurrences = moves.values().stream().mapToDouble(MoveMetaData::getOccurrences).sum();

		// calculate and set new probability for each possible move
		moves.values().forEach(probability -> probability.setProbability(probability.getOccurrences() / totalOccurrences));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		moves.forEach((character, moveMetaData) -> builder
				.append("\nMove: ")
				.append(character)
				.append(" ")
				.append(moveMetaData));
		return builder.toString();
	}
}
