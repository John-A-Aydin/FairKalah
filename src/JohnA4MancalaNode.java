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
		double w1 = 0.7;
		double w2 = 0.275;
		double w3 = 0.01;
		// First heuristic: Score Diff
		int h1 = state[6] - state[13];

		// Second heuristic: Double turns
		int h2 = 0;
		int h3 = 0;
		int i = 1;
		if (state[0] == 6) {
			h2++;
		} else if (state[0] == 0) {
			h3++;
		}
		if (state[i] + i == 6) {
			h2++;
		} else if (state[i] == 0) {
			h3++;
		}
		i++;
		if (state[i] + i == 6) {
			h2++;
		} else if (state[i] == 0) {
			h3++;
		}
		i++;
		if (state[i] + i == 6) {
			h2++;
		} else if (state[i] == 0) {
			h3++;
		}
		i++;
		if (state[i] + i == 6) {
			h2++;
		} else if (state[i] == 0) {
			h3++;
		}
		i++;
		if (state[i] + i == 6) {
			h2++;
		} else if (state[i] == 0) {
			h3++;
		}
		double h4 = 0;
		if (state[MAX_SCORE_PIT] > 24) {
			h4 = 100;
		} else if (state[MIN_SCORE_PIT] > 24) {
			h4 = -100;
		}
		// Third and Fourth heuristics: Piece distribution
		// Don't want your side to be too sparse or too full
		h3 = (h3-3)*(h3-3) + 9;

		return w1*h1 + w2*h2 + w3*h3 + h4;
	}
}
