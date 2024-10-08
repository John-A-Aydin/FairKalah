import java.util.Arrays;

/**
 * SimpleMancalaPlayer - A simple example implementation of a Mancala
 * player with simple, poor time management.
 *
 * @author Todd W. Neller
 */

public class JohnA1MancalaPlayer implements MancalaPlayer {
	private long[] depth_times;
	private int moves_taken;

	public JohnA1MancalaPlayer() {
		depth_times = new long[15];
		depth_times[0] = 400;
		depth_times[1] = 800;
		depth_times[2] = 1500;
		depth_times[3] = 4000;
		depth_times[4] = 11000;
		depth_times[5] = 27000;
		depth_times[6] = 58000;
		this.moves_taken = 0;
	}

	/**
	 * Choose a move for the given game situation given play time
	 * remaining.  */
	public int chooseMove(MancalaNode node, long timeRemaining) {
		int depth = 15;
		int pieces = piecesRemaining(node);
		long t_next_move = timeRemaining/piecesRemaining(node);

		if (moves_taken <= 2) {
			depth = 14;
		} else if (pieces > 40) {
			depth = 15;
		} else if (pieces > 30) {
			depth = 16;
		} else if (pieces > 20) {
			depth = 17;
		} else {
			depth = 18;
		}

		if (timeRemaining <= 1000) {
			System.out.println("Super duper emergency depth reached: " + piecesRemaining(node));
			depth = 10;
		} else if (timeRemaining <= 5000) {
			System.out.println("Super emergency depth reached: " + piecesRemaining(node));
			depth = 12;
		} else if (timeRemaining <= 10000) {
			System.out.println("Emergency depth reached: " + piecesRemaining(node));
			depth = 14;
		} else if (timeRemaining <= 30000) {
			System.out.println("Emergency depth reached: " + piecesRemaining(node));
			depth = 16;
		}

		// Create a minimax searcher.
		JohnAAlphaBetaSearcher searcher = new JohnAAlphaBetaSearcher(depth);
		JohnA4MancalaNode searchNode = new JohnA4MancalaNode(node);

		long startTime = System.currentTimeMillis();
		searcher.eval(searchNode);
		long endTime = System.currentTimeMillis();
		long current_time  = endTime - startTime;
		moves_taken++;

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
