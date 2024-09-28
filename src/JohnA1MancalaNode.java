/**
 * An extension of MancalaNode with a simple, score-difference utility evaluation function.
 * @author Todd W. Neller
 */
public class JohnA1MancalaNode extends MancalaNode {

  /**
   * See corresponding <code>MancalaNode</code> standard constructor documentation.
   */
  public JohnA1MancalaNode() {
    super();
  }

  /**
   * See corresponding <code>MancalaNode</code> copy constructor documentation.
   * @param node node to be copied
   */
  public JohnA1MancalaNode(MancalaNode node) {
    super(node);
  }

  /**
   * See corresponding <code>MancalaNode</code> FairKalah constructor documentation.
   * @param stateIndex FairKalah initial state index
   */
  public JohnA1MancalaNode(int stateIndex) {
    super(stateIndex);
  }

	/**
	 * Return the difference between current MAX and MIN score.
	 */
	public double utility() {
		double max_pieces = 0;
		double min_pieces = 0;
		for (int i = 0; i < MAX_SCORE_PIT; i++) {
		  max_pieces += state[i];
		}
		for (int i = MAX_SCORE_PIT + 1; i < MIN_SCORE_PIT; i++) {
		  min_pieces += state[i];
		}
		return (max_pieces + state[MAX_SCORE_PIT]) - (min_pieces + state[MIN_SCORE_PIT]);
	}
}
