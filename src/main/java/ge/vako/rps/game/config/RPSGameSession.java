package ge.vako.rps.game.config;

import ge.vako.rps.game.markovchain.MarkovChain;
import ge.vako.rps.game.markovchain.MarkovPossibleMoves;
import ge.vako.rps.model.BotThoughtsModel;
import ge.vako.rps.model.GameStatisticsModel;
import ge.vako.rps.model.PredictionModel;
import ge.vako.rps.model.PredictionResultModel;

import java.util.*;

/**
 * <br/><br/>
 * Created by <b> Vako Beridze &lt;vako.beridze@gmail.com&gt; </b> <br/>
 * Created at  <b> 7/29/19 </b> <br/>
 */
public class RPSGameSession {

	private Character[] gameMoves = {'R', 'P', 'S'};
	private MarkovChain markovChain;
	private List<String> sessionHistory;
	private int movesToRemember;

	private int totalRounds = 0;
	private double humanScore = 0;
	private double botScore = 0;
	private double tie = 0;


	public RPSGameSession(GameConfig config) {
		this.movesToRemember = config.getMovesToRemember();
		this.markovChain = new MarkovChain(this.gameMoves, this.movesToRemember, config.getAdaptChangesPercentage());
		this.sessionHistory = new ArrayList<>();
	}

	public PredictionResultModel getNextPrediction() {
		if (sessionHistory.size() > movesToRemember) {
			String lastMoves = getLastMoves();
			MarkovPossibleMoves possibleMoves = markovChain.getPredictions(lastMoves);
			return generateModel(possibleMoves);
		}
		return generateRandomModel();
	}

	public void saveHistory(Character botChoice, Character humanChoice) {
		totalRounds++;
		updateWinner(botChoice, humanChoice);

		if (sessionHistory.size() >= movesToRemember) {
			String lastMoves = getLastMoves();
			markovChain.updateMatrix(lastMoves, humanChoice);
		}

		String movePair = botChoice.toString() + humanChoice.toString();
		sessionHistory.add(movePair);
	}

	public GameStatisticsModel getGameStatistics() {
		return GameStatisticsModel.builder()
				.totalRounds(totalRounds)
				.botScore(botScore)
				.humanScore(humanScore)
				.tie(tie)
				.build();
	}

	private Character beat(Character prediction) {
		switch (prediction) {
			case 'R':
				return 'P';
			case 'P':
				return 'S';
			case 'S':
				return 'R';
			default:
				return null;
		}
	}

	private String getLastMoves() {
		List<String> subList = sessionHistory.subList(sessionHistory.size() - movesToRemember, sessionHistory.size());
		return String.join("", subList);
	}

	private void updateWinner(Character bot, Character human) {
		if (bot.equals(human)) {
			tie++;
			return;
		}

		Character moveToBeatHuman = beat(human);
		if (bot.equals(moveToBeatHuman)) {
			botScore++;
		} else {
			humanScore++;
		}
	}

	private PredictionResultModel generateRandomModel() {
		List<PredictionModel> predictionModels = new ArrayList<>();
		for (Character gameMove : gameMoves) {
			predictionModels.add(PredictionModel.builder().move(gameMove).probability(1 / 3.0).build());
		}
		return PredictionResultModel.builder()
				.predictions(predictionModels)
				.moveToBeatHuman(gameMoves[new Random().nextInt(gameMoves.length)])
				.build();
	}

	private PredictionResultModel generateModel(MarkovPossibleMoves possibleMoves) {
		List<PredictionModel> predictionModels = new ArrayList<>();
		possibleMoves.getMoves().forEach((character, moveMetaData) -> predictionModels.add(
				PredictionModel.builder()
						.move(character)
						.probability(moveMetaData.getProbability())
						.build()
				)
		);

		PredictionResultModel resultModel = PredictionResultModel.builder()
				.predictions(predictionModels)
				.build();

		Optional<PredictionModel> highestProbabilityOptional = predictionModels.stream().max(Comparator.comparing(PredictionModel::getProbability));
		highestProbabilityOptional.ifPresent(highestProbability -> resultModel.setMoveToBeatHuman(beat(highestProbability.getMove())));

		return resultModel;
	}

	public BotThoughtsModel getBotThoughts() {
		return BotThoughtsModel.builder()
				.history(sessionHistory)
				.thoughts(markovChain.getMatrix())
				.build();
	}
}
