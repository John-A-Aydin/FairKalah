/**
 * SimpleMancalaPlayer - A simple example implementation of a Mancala
 * player with simple, poor time management.
 *
 * @author Todd W. Neller
 */

public class TestMancalaPlayer implements MancalaPlayer {
	private int n;

	public TestMancalaPlayer() {
		n = 0;
	}

	/**
	 * Choose a move for the given game situation given play time
	 * remaining.  */
	public int chooseMove(MancalaNode node, long timeRemaining) {
		int depth = 15;
		long t_next_move = timeRemaining/piecesRemaining(node);

		// Create a minimax searcher.
		JohnAAlphaBetaSearcher searcher = new JohnAAlphaBetaSearcher(depth);
		JohnA4MancalaNode searchNode = new JohnA4MancalaNode(node);

		long startTime = System.currentTimeMillis();
		searcher.eval(searchNode);
		long endTime = System.currentTimeMillis();
		long current_time  = endTime - startTime;
		System.out.println(n + " - " + current_time);
		this.n++;

		return searcher.getBestMove();

	}


	/**
	 * Returns the number of pieces not yet captured.
	 * @return int - uncaptured pieces
	 * @param node MancalaNode - node to check
	 */
	public int piecesRemaining(MancalaNode node) {
		int pieces = 0;
		for (int i = 0; i < 6; i++) pieces += node.state[i];
		for (int i = 7; i < 13; i++) pieces += node.state[i];
		return pieces;
	}

}
