package ge.vako.rps.game.markovchain;

/**
 * <br/><br/>
 * Created by <b> Vako Beridze &lt;vako.beridze@gmail.com&gt; </b> <br/>
 * Created at  <b> 7/29/19 </b> <br/>
 */
public class MoveMetaData {

	private double occurrences;
	private double probability;

	public MoveMetaData(int totalMoves) {
		this.occurrences = 0;
		this.probability = 1.0 / totalMoves;
	}

	@Override
	public String toString() {
		return "{ prob=" + this.probability + " | occ=" + this.occurrences + " }";
	}

	public double getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(double occurrences) {
		this.occurrences = occurrences;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}
}
