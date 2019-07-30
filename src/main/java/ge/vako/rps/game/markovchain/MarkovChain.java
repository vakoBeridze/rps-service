package ge.vako.rps.game.markovchain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br/><br/>
 * Created by <b> Vako Beridze &lt;vako.beridze@gmail.com&gt; </b> <br/>
 * Created at  <b> 7/29/19 </b> <br/>
 */
public class MarkovChain {

	private final Character[] gameMoves;
	private Map<String, MarkovPossibleMoves> matrix;
	private double adaptChangesPercentage;

	public MarkovChain(Character[] gameMoves, int movesToRemember, double adaptChangesPercentage) {
		this.gameMoves = gameMoves;
		this.adaptChangesPercentage = adaptChangesPercentage;
		this.init(movesToRemember);
	}

	private void init(int movesToRemember) {
		this.matrix = new HashMap<>();
		List<String> matrixKeys = new ArrayList<>();
		generateMoves(matrixKeys, "", movesToRemember * 2);
		matrixKeys.forEach(key -> matrix.put(key, new MarkovPossibleMoves(gameMoves)));
	}

	public void updateMatrix(String lastMoves, Character newHumanMove) {
		MarkovPossibleMoves possibleMoves = matrix.get(lastMoves);
		possibleMoves.updateOccurrences(newHumanMove, adaptChangesPercentage);
		possibleMoves.updateProbabilities();
	}

	public MarkovPossibleMoves getPredictions(String lastMoves) {
		return matrix.get(lastMoves);
	}


	public Map<String, MarkovPossibleMoves> getMatrix() {
		return matrix;
	}

	private void generateMoves(List<String> matrixKeys, String prefix, int keyLength) {
		if (keyLength == 0) {
			matrixKeys.add(prefix);
			return;
		}

		for (char possibleMove : gameMoves) {
			String newPrefix = prefix + possibleMove;
			generateMoves(matrixKeys, newPrefix, keyLength - 1);
		}
	}
}
