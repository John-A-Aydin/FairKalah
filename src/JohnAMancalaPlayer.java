import java.util.Arrays;

/**
 * SimpleMancalaPlayer - A simple example implementation of a Mancala
 * player with simple, poor time management.
 *
 * @author Todd W. Neller
 */

public class JohnAMancalaPlayer implements MancalaPlayer {
	private long time_left;
	private int depth;
	private long[] depth_times;
	private int[] depth_counts;

	public JohnAMancalaPlayer() {
		this.time_left = 150000;
		this.depth = 15;
		this.depth_times = new long[30];
		this.depth_counts = new int[30];
        Arrays.fill(this.depth_times, 0);
        Arrays.fill(this.depth_counts, 0);
	}

	/**
	 * Choose a move for the given game situation given play time
	 * remaining.  */
	public int chooseMove(MancalaNode node, long timeRemaining) {
		// TODO - WARNING: This is a simple time management effort 
		// to distribute search time over course of game.  
		// It under-utilizes time, so you should design better time management.
		//final double DEPTH_FACTOR = 1.3;
		//int depthLimit = (int) (DEPTH_FACTOR * Math.log((double) timeRemaining / piecesRemaining(node)));
		//if (depthLimit < 1) depthLimit = 1;
		// At the start of the game, ab pruning doesn't do much
//		if (piecesRemaining(node) >= 36) {
//			this.depth = 15;
//		} else if (piecesRemaining(node) >= 26) {
//			this.depth = 17;
//		} else {
//			this.depth = 18;
//		}
		this.depth = 15;
		if (piecesRemaining(node) <= 24) {
			this.depth = 16;
		}


		if (this.time_left <= 1000) {
			System.out.println("Super duper emergency depth reached: " + piecesRemaining(node));
			this.depth = 10;
		} else if (this.time_left <= 5000) {
			System.out.println("Super emergency depth reached: " + piecesRemaining(node));
			this.depth = 12;
		} else if (this.time_left <= 20000) {
			System.out.println("Emergency depth reached: " + piecesRemaining(node));
			this.depth = 14;
		}

		// Create a minimax searcher.
		JohnAAlphaBetaSearcher searcher = new JohnAAlphaBetaSearcher(this.depth);
		long total_time = 150000;
		// Create a new copy of the input node that uses the
		// score difference heuristic evaluation function. 
		JohnA4MancalaNode searchNode = new JohnA4MancalaNode(node);

		long startTime = System.currentTimeMillis();
		searcher.eval(searchNode);
		long endTime = System.currentTimeMillis();
		long current_time  = endTime - startTime;
		this.time_left -= current_time;

//		// Update the average time for the depth that was just done
//		this.depth_times[this.depth] = (this.depth_times[this.depth]*this.depth_counts[this.depth])/(this.depth_counts[this.depth] + 1);
//		depth_counts[depth]++;
//		this.depth_times[depth] += current_time/this.depth_counts[this.depth];
//
//		// Determine the next depth based on previous performance
//		// Not good. Once it reaches a certain depth the 0 tells it to keep increasing
//		if (this.depth_times[this.depth] < total_time / 8 && this.depth_counts[this.depth] > 1) {
//			this.depth++;
//		} else if (this.depth_times[this.depth] > total_time / 8) {
//			this.depth--;
//		}




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
