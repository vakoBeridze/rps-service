package ge.vako.rps.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <br/><br/>
 * Created by <b> Vako Beridze &lt;vako.beridze@gmail.com&gt; </b> <br/>
 * Created at  <b> 7/29/19 </b> <br/>
 */
@Data
@Builder
public class PredictionResultModel {
	char moveToBeatHuman;
	List<PredictionModel> predictions;

}
