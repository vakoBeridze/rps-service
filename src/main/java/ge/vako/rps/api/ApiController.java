package ge.vako.rps.api;

import ge.vako.rps.game.config.GameConfig;
import ge.vako.rps.game.config.RPSGameSession;
import ge.vako.rps.model.BotThoughtsModel;
import ge.vako.rps.model.GameStatisticsModel;
import ge.vako.rps.model.PredictionResultModel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <br/><br/>
 * Created by <b> Vako Beridze &lt;vako.beridze@gmail.com&gt; </b> <br/>
 * Created at  <b> 7/29/19 </b> <br/>
 */
@Slf4j
@NoArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class ApiController {

	private RPSGameSession game;

	@PostMapping("new-game")
	public void start(@RequestParam Double adaptChangesPercentage) {
		log.debug("Creating new RPSGameSession");
		game = new RPSGameSession(new GameConfig(1, adaptChangesPercentage));
	}

	@GetMapping("predict")
	public PredictionResultModel predict() {
		log.debug("getting next prediction");
		return game.getNextPrediction();
	}

	@PostMapping("save")
	public void saveGame(@RequestParam Character botChoice, @RequestParam Character humanChoice) {
		log.debug("saving game result");
		game.saveHistory(botChoice, humanChoice);
	}

	@GetMapping("statistics")
	public GameStatisticsModel statistics() {
		log.debug("getting Statistics");
		return game.getGameStatistics();
	}

	@GetMapping("bot-thoughts")
	public BotThoughtsModel getBotThoughts() {
		log.debug("getting bot thoughts");
		return game.getBotThoughts();
	}
}
