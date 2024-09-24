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
    double scoreDiff = state[MAX_SCORE_PIT] - state[MIN_SCORE_PIT];
    // Try looking at possible range of score gain for next turn
    // What function will produce the best results
    //
    // My play pits: 0-5
    // My goal: 6
    // Other play pits: 7-12
    // Other goal: 13
    for (int i = 0; i < state.length; i++) {
      if (i + state[i] <= 5) { // This move will allow a pit steal
      }
    }
    return scoreDiff;
  }
}
