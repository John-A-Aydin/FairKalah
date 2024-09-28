/**
 * An extension of MancalaNode with a simple, score-difference utility evaluation function.
 * @author Todd W. Neller
 */
public class JohnA3MancalaNode extends MancalaNode {

	/**
	 * See corresponding <code>MancalaNode</code> standard constructor documentation.
	 */
	public JohnA3MancalaNode() {
		super();
	}

	/**
	 * See corresponding <code>MancalaNode</code> copy constructor documentation.
	 * @param node node to be copied
	 */
	public JohnA3MancalaNode(MancalaNode node) {
		super(node);
	}

	/**
	 * See corresponding <code>MancalaNode</code> FairKalah constructor documentation.
	 * @param stateIndex FairKalah initial state index
	 */
	public JohnA3MancalaNode(int stateIndex) {
		super(stateIndex);
	}

	/**
	 * Return the difference between current MAX and MIN score.
	 */
	public double utility() {
		// Look at enemy piece stealing and double turns
		double double_turn_weight = 10.0;
		double max_enemy_score_gain = 0;
		for (int i = MAX_SCORE_PIT + 1; i < MIN_SCORE_PIT; i++) {
			int ending_pos = state[i] % (MIN_SCORE_PIT + 1);
			if (ending_pos == MIN_SCORE_PIT) {
				max_enemy_score_gain = double_turn_weight;
			} else if (ending_pos > MAX_SCORE_PIT) {
				int opposite_side = 2*MAX_SCORE_PIT - ending_pos;
				if (state[opposite_side] > max_enemy_score_gain) {
					max_enemy_score_gain = state[opposite_side];
				}
			}
		}
		double max_pieces = 0;
		double min_pieces = 0;
		for (int i = 0; i < MAX_SCORE_PIT; i++) {
			max_pieces += state[i];
		}
		for (int i = MAX_SCORE_PIT + 1; i < MIN_SCORE_PIT; i++) {
			min_pieces += state[i];
		}
		return (state[MAX_SCORE_PIT] - state[MIN_SCORE_PIT]) - max_enemy_score_gain + (max_pieces - min_pieces);
	}
}
