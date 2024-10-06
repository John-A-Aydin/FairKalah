import java.util.Arrays;

/**
 * An extension of MancalaNode with a simple, score-difference utility evaluation function.
 * @author Todd W. Neller
 */
public class JohnA4MancalaNode extends MancalaNode {

	/**
	 * See corresponding <code>MancalaNode</code> standard constructor documentation.
	 */
	public JohnA4MancalaNode() {
		super();
	}

	/**
	 * See corresponding <code>MancalaNode</code> copy constructor documentation.
	 * @param node node to be copied
	 */
	public JohnA4MancalaNode(MancalaNode node) {
		super(node);
	}

	/**
	 * See corresponding <code>MancalaNode</code> FairKalah constructor documentation.
	 * @param stateIndex FairKalah initial state index
	 */
	public JohnA4MancalaNode(int stateIndex) {
		super(stateIndex);
	}

	/**
	 * Return the difference between current MAX and MIN score.
	 */
	public double utility() {

		// Declaring heuristic weights
		double w1 = 0.7;
		double w2 = 0.1;
		double w3 = 0.1;
		double w4 = 0.1;
		// First heuristic: Score Diff
		double h1 = state[MAX_SCORE_PIT] - state[MIN_SCORE_PIT];
//		if (this.gameOver()) {
//			if (h1 > 0) return 1000;
//			if (h1 < 0) return -1000;
//		}
		// Second heuristic: Double turns
		double h2 = 0;
		for (int i = 0; i < MAX_SCORE_PIT; i++) {
			if (state[i] + i == MAX_SCORE_PIT) {
				h2++;
			}
		}
		// Third and Fourth heuristics: Piece distribution
		double h3 = 0;
		for (int i = 0; i < MAX_SCORE_PIT; i++) {
			if (state[i] == 0) {
				h3++;
			}
		}
		double h4 = 0;
		for (int i = MAX_SCORE_PIT + 1; i < MIN_SCORE_PIT; i++) {
			if (state[i] == 0) {
				h4++;
			}
		}
		// Don't your side to be too sparse or too full
		h3 = -(h3-3)*(h3-3) + 9;
		h4 = -(h4-3)*(h4-3) + 9;
		return w1*h1 + w2*h2 + w3*h3 - w4*h4;
	}
}
