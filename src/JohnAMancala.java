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

import java.util.Random;

public class JohnAMancala {
	protected static final int NUMBER_OF_STATES = 253;
	protected static final int NUMBER_OF_TRIALS = 50;
	/**
	 * <code>main</code> - manage a timed Mancala game
	 *
	 * @param args a <code>String[]</code> value - unused
	 */

	public static void main(String[] args) {
		int []orderOfStates = new int[NUMBER_OF_STATES];
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
		int p1Wins = 0;
		int p2Wins = 0;
		long p1AvgTime = 0;
		long p2AvgTime = 0;
		for (int i = 0; i < NUMBER_OF_TRIALS; i++) {

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
			MancalaNode node = new ScoreDiffMancalaNode(orderOfStates[i]);
			System.out.println(node);

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
					winner = "PLAYER 1 WINS";
					p1Wins++;
				} else if (node.utility() < 0) {
					winner = "PLAYER 2 WINS";
					p2Wins++;
				} else {
					winner = "DRAW";
				}
			System.out.println("Time Taken (ms): ");
			long p1Time = (MILLISECONDS_PER_GAME / 2L - playerMillisRemaining[GameNode.MAX]);
			long p2Time = (MILLISECONDS_PER_GAME / 2L - playerMillisRemaining[GameNode.MIN]);
			p1AvgTime += p1Time/NUMBER_OF_TRIALS;
			p2AvgTime += p2Time/NUMBER_OF_TRIALS;
			System.out.println("Player 1: " + p1Time);
			System.out.println("Player 2: " + p2Time);

			System.out.println(winner);
		}
		System.out.println("\nPlayer 1 Avg Time:" + p1AvgTime);
		System.out.println("Player 2 Avg Time:" + p2AvgTime);
		System.out.println("\nPlayer 1 Wins: " + p1Wins);
		System.out.println("Player 2 Wins: " + p2Wins);
	}
}

