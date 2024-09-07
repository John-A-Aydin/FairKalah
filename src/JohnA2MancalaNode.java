/**
 * An extension of MancalaNode with a simple, score-difference utility evaluation function.
 * @author Todd W. Neller
 */
public class JohnA2MancalaNode extends MancalaNode {

	/**
	 * See corresponding <code>MancalaNode</code> standard constructor documentation.
	 */
	public JohnA2MancalaNode() {
		super();
	}

	/**
	 * See corresponding <code>MancalaNode</code> copy constructor documentation.
	 * @param node node to be copied
	 */
	public JohnA2MancalaNode(MancalaNode node) {
		super(node);
	}

	/**
	 * See corresponding <code>MancalaNode</code> FairKalah constructor documentation.
	 * @param stateIndex FairKalah initial state index
	 */
	public JohnA2MancalaNode(int stateIndex) {
		super(stateIndex);
	}

	/**
	 * Return the difference between current MAX and MIN score.
	 */
	public double utility() {
		return state[MAX_SCORE_PIT] - state[MIN_SCORE_PIT];
	}

}
