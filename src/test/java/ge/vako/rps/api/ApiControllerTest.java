package ge.vako.rps.api;

import ge.vako.rps.game.markovchain.MarkovPossibleMoves;
import ge.vako.rps.game.markovchain.MoveMetaData;
import ge.vako.rps.model.BotThoughtsModel;
import ge.vako.rps.model.GameStatisticsModel;
import ge.vako.rps.model.PredictionModel;
import ge.vako.rps.model.PredictionResultModel;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApiControllerTest {
	private static final double DELTA = 0.00000001;

	@Test
	public void testGamePlay() {
		ApiController controller = new ApiController();
		controller.start(1.0);

		PredictionResultModel firstPrediction = controller.predict();
		List<PredictionModel> predictions = firstPrediction.getPredictions();
		for (PredictionModel prediction : predictions) {
			assertEquals(1 / 3.0, prediction.getProbability(), DELTA);
			assertTrue(prediction.getMove() == 'R' || prediction.getMove() == 'P' || prediction.getMove() == 'S');
		}
		char humanFirstMove = 'R';
		controller.saveGame(firstPrediction.getMoveToBeatHuman(), humanFirstMove);
		GameStatisticsModel statistics = controller.statistics();
		assertEquals(1, statistics.getBotScore() + statistics.getTie() + statistics.getHumanScore(), DELTA);
		assertEquals(1, statistics.getTotalRounds(), DELTA);

		if (statistics.getHumanScore() == 1) {
			assertEquals(0, statistics.getBotScore(), DELTA);
			assertEquals(0, statistics.getTie(), DELTA);
		} else if (statistics.getTie() == 1) {
			assertEquals(0, statistics.getHumanScore(), DELTA);
			assertEquals(0, statistics.getBotScore(), DELTA);
		} else {
			assertEquals(0, statistics.getHumanScore(), DELTA);
			assertEquals(0, statistics.getTie(), DELTA);
		}

		PredictionResultModel secondPrediction = controller.predict();
		predictions = secondPrediction.getPredictions();
		for (PredictionModel prediction : predictions) {
			assertEquals(1 / 3.0, prediction.getProbability(), DELTA);
			assertTrue(prediction.getMove() == 'R' || prediction.getMove() == 'P' || prediction.getMove() == 'S');
		}
		char humanSecondMove = 'P';
		controller.saveGame(secondPrediction.getMoveToBeatHuman(), humanSecondMove);
		statistics = controller.statistics();
		assertEquals(2, statistics.getBotScore() + statistics.getTie() + statistics.getHumanScore(), DELTA);
		assertEquals(2, statistics.getTotalRounds(), DELTA);

		BotThoughtsModel botThoughts = controller.getBotThoughts();
		Map<String, MarkovPossibleMoves> thoughts = botThoughts.getThoughts();
		MoveMetaData moveMetaData = thoughts.get(firstPrediction.getMoveToBeatHuman() + "" + humanFirstMove).getMoves().get(humanSecondMove);
		assertEquals(1, moveMetaData.getProbability(), DELTA);
		assertEquals(1, moveMetaData.getOccurrences(), DELTA);

		PredictionResultModel predict = controller.predict();
		System.out.println(predict.getMoveToBeatHuman());
		System.out.println(controller.getBotThoughts().getHistory());
	}
}