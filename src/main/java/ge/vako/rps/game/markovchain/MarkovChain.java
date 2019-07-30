package ge.vako.rps.game.markovchain;

import java.util.*;

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
		List<String> matrixKeys = generateMoves("", movesToRemember * 2);
		matrixKeys.forEach(this::generatePossibleMoves);
	}

	private void generatePossibleMoves(String key) {
		matrix.put(key, new MarkovPossibleMoves(gameMoves));
	}

	public void updateMatrix(String lastMoves, Character newHumanMove) {
		MarkovPossibleMoves possibleMoves = matrix.get(lastMoves);
		possibleMoves.updateOccurrences(newHumanMove, adaptChangesPercentage);
	}

	public MarkovPossibleMoves getPredictions(String lastMoves) {
		return matrix.get(lastMoves);
	}

	private List<String> generateMoves(String prefix, int keyLength) {
		if (keyLength == 0) {
			return Collections.singletonList(prefix);
		}

		List<String> matrixKeys = new ArrayList<>();
		for (char possibleMove : gameMoves) {
			List<String> generated = generateMoves(prefix + possibleMove, keyLength - 1);
			matrixKeys.addAll(generated);
		}
		return matrixKeys;
	}

	public Map<String, MarkovPossibleMoves> getMatrix() {
		return matrix;
	}
}
