/**
 * Mancala - Timed mancala game manager.  To play two programs
 * against each other, change the classes at (*1*) and (*2*).
 * Each player has a maximum of 2.5 minutes of game time, so each
 * game will last at most 5 minutes.  In your testing, you may
 * choose to compete against your program.  Or you may play your
 * program against itself.  In the end, your program will compete
 * as both MAX and MIN against other programs.
 *
 * @author: Todd W. Neller
 */

import java.util.*;

class MancalaRunnable implements Runnable {
	int[] stateIdxs;
	List<Integer> p1Wins;
	List<Integer> p2Wins;
	List<Long> p1AvgTime;
	List<Long> p2AvgTime;
	int thread_number;

	MancalaRunnable(int[] stateIdxs, List<Integer> p1Wins, List<Integer> p2Wins, List<Long> p1AvgTime, List<Long> p2AvgTime, int thread_number) {
		this.stateIdxs = stateIdxs;
		this.p1Wins = p1Wins;
		this.p2Wins = p2Wins;
		this.p1AvgTime = p1AvgTime;
		this.p2AvgTime = p2AvgTime;
		this.thread_number = thread_number;
	}

	public void run() {
		for (int i = 0; i < stateIdxs.length; i++) {
			// Create players
			MancalaPlayer[] player = new MancalaPlayer[2];

			// TODO (*1*) put player one class here
			// player[GameNode.MAX] = new HumanMancalaPlayer();
			player[GameNode.MAX] = new JohnAMancalaPlayer();

			// TODO (*2*) put player two class here
			//  player[GameNode.MIN] = new HumanMancalaPlayer();
			player[GameNode.MIN] = new SimpleMancalaPlayer();

			// Create times
			final long MILLISECONDS_PER_GAME = 300000L; // 5 minutes
			long[] playerMillisRemaining = {MILLISECONDS_PER_GAME / 2L, MILLISECONDS_PER_GAME / 2L};

			// Create a clock
			Stopwatch clock = new Stopwatch();
			long timeTaken;

			// Create a random initial node of a FairKalah (fair Mancala) game
			MancalaNode node = new ScoreDiffMancalaNode(stateIdxs[i]);

			// While game is on...
			int move;
			String winner = "DRAW";
			while (!node.gameOver()) {
				// Request move from current player
				long timeRemaining = playerMillisRemaining[node.player];
				clock.reset();
				clock.start();
				move = player[node.player].chooseMove(node, timeRemaining);
				timeTaken = clock.stop();

				// Deduct time taken
				playerMillisRemaining[node.player] -= timeTaken;
				if (playerMillisRemaining[node.player] < 0) {
					if (node.player == GameNode.MAX) {
						System.out.println("Player 1 game timer expired.");
						winner = "PLAYER 2 WINS";
					} else {
						System.out.println("Player 2 game timer expired.");
						winner = "PLAYER 1 WINS";
					}
					break;
				}
				// Update game state
				node.makeMove(move);
			}
			// Display winner and statistics
			if (node.gameOver())
				if (node.utility() > 0) {
					p1Wins.set(this.thread_number, p1Wins.get(this.thread_number)+1);
				} else if (node.utility() < 0) {
					p2Wins.set(this.thread_number, p2Wins.get(this.thread_number)+1);
				}
			long p1Time = (MILLISECONDS_PER_GAME / 2L - playerMillisRemaining[GameNode.MAX]);
			long p2Time = (MILLISECONDS_PER_GAME / 2L - playerMillisRemaining[GameNode.MIN]);
			p1AvgTime.set(this.thread_number, p1AvgTime.get(this.thread_number) + p1Time/this.stateIdxs.length);
			p2AvgTime.set(this.thread_number, p2AvgTime.get(this.thread_number) + p2Time/this.stateIdxs.length);
		}
	}
}
public class JohnAMancalaMT {
	protected static final int NUMBER_OF_STATES = 253;
	protected static final int TRIALS_PER_THREAD = 36; // Max = 36
	protected static final int NUMBER_OF_THREADS = 7;
	/**
	 * <code>main</code> - manage a timed Mancala game
	 *
	 * @param args a <code>String[]</code> value - unused
	 */

	public static void main(String[] args) {
		int[] orderOfStates = new int[NUMBER_OF_STATES];
		for (int i = 0; i < orderOfStates.length; i++) {
			orderOfStates[i] = i;
		}
		// Shuffling order of fair states
		Random rnd = new Random();
		for (int i = orderOfStates.length - 1; i >= 0; i--) {
			int idx = rnd.nextInt(i + 1);
			int a = orderOfStates[idx];
			orderOfStates[idx] = orderOfStates[i];
			orderOfStates[i] = a;
		}
		List<Integer> p1Wins = new ArrayList<Integer>(Collections.nCopies(NUMBER_OF_THREADS, 0));
		List<Integer> p2Wins = new ArrayList<Integer>(Collections.nCopies(NUMBER_OF_THREADS, 0));
		List<Long> p1AvgTimes = new ArrayList<Long>(Collections.nCopies(NUMBER_OF_THREADS, 0L));
		List<Long> p2AvgTimes = new ArrayList<Long>(Collections.nCopies(NUMBER_OF_THREADS, 0L));
		Thread[] threads = new Thread[NUMBER_OF_THREADS];
		for (int i = 0; i < threads.length; i++) {
			MancalaRunnable r = new MancalaRunnable(Arrays.copyOfRange(orderOfStates, TRIALS_PER_THREAD*i, TRIALS_PER_THREAD*(i+1)),
							p1Wins, p2Wins, p1AvgTimes, p2AvgTimes, i);
			threads[i] = new Thread(r);
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int p1TotalWins = 0;
		int p2TotalWins = 0;
		for (int i = 0; i < p1Wins.size(); i++) {
			p1TotalWins += p1Wins.get(i);
			p2TotalWins += p2Wins.get(i);
		}
		System.out.println("\nPlayer 1 Wins: " + p1TotalWins);
		System.out.println("Player 2 Wins: " + p2TotalWins);
		long p1RuntimeSum = 0;
		long p2RuntimeSum = 0;
		for (int i = 0; i < p1AvgTimes.size(); i++) {
			p1RuntimeSum += p1AvgTimes.get(i);
			p2RuntimeSum += p2AvgTimes.get(i);
		}
		long p1AvgRuntime = p1RuntimeSum / NUMBER_OF_THREADS;
		long p2AvgRuntime = p2RuntimeSum / NUMBER_OF_THREADS;
		System.out.println("\nPlayer 1 Avg Time: " + p1AvgRuntime);
		System.out.println("Player 2 Avg Time: " + p2AvgRuntime);
	}
}

