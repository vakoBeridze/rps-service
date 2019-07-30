package ge.vako.rps.game.markovchain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MarkovChainTest {

    private MarkovChain chain;

    @Before
    public void init() {
        chain = new MarkovChain(new Character[]{'R', 'P', 'S'}, 1, 1);
    }

    @Test
    public void updateMatrixAndGetPredictions() {
        MarkovPossibleMoves rr = chain.getPredictions("RR");
        assertEquals(3, rr.getMoves().size());
        assertEquals(1 / 3.0, rr.getMoves().get('R').getProbability(), 0.000000001);
        assertEquals(1 / 3.0, rr.getMoves().get('P').getProbability(), 0.000000001);
        assertEquals(1 / 3.0, rr.getMoves().get('S').getProbability(), 0.000000001);

        assertEquals(0, rr.getMoves().get('R').getOccurrences(), 0.000000001);
        assertEquals(0, rr.getMoves().get('P').getOccurrences(), 0.000000001);
        assertEquals(0, rr.getMoves().get('S').getOccurrences(), 0.000000001);

        chain.updateMatrix("RR", 'S');

        MarkovPossibleMoves afterOneMove = chain.getPredictions("RR");
        assertEquals(3, afterOneMove.getMoves().size());
        assertEquals(0, afterOneMove.getMoves().get('R').getProbability(), 0.000000001);
        assertEquals(0, afterOneMove.getMoves().get('P').getProbability(), 0.000000001);
        assertEquals(1, afterOneMove.getMoves().get('S').getProbability(), 0.000000001);

        assertEquals(0, afterOneMove.getMoves().get('R').getOccurrences(), 0.000000001);
        assertEquals(0, afterOneMove.getMoves().get('P').getOccurrences(), 0.000000001);
        assertEquals(1, afterOneMove.getMoves().get('S').getOccurrences(), 0.000000001);

        chain.updateMatrix("RR", 'P');

        MarkovPossibleMoves afterSecondMove = chain.getPredictions("RR");
        assertEquals(3, afterSecondMove.getMoves().size());
        assertEquals(0, afterSecondMove.getMoves().get('R').getProbability(), 0.000000001);
        assertEquals(0.5, afterSecondMove.getMoves().get('P').getProbability(), 0.000000001);
        assertEquals(0.5, afterSecondMove.getMoves().get('S').getProbability(), 0.000000001);

        assertEquals(0, afterSecondMove.getMoves().get('R').getOccurrences(), 0.000000001);
        assertEquals(1, afterSecondMove.getMoves().get('P').getOccurrences(), 0.000000001);
        assertEquals(1, afterSecondMove.getMoves().get('S').getOccurrences(), 0.000000001);
    }
}