README for Java FairKalah project
---------------------------------

1. Learn how to play Mancala with resources provided (video, text, and/or, Ludii game interface).

2. Download all Java project source code.

3. **Creating Better Utility (i.e. Heuristic Evaluation) Functions**: Select a unique identifier (denoted here with placeholder <UniqueID>) for your submission, e.g. your username.  Create at least two copies of ScoreDiffMancalaNode.java called <UniqueID>1MancalaNode.java, <UniqueID>2MancalaNode.java, etc.  Use these classes to compare performance for different utility functions.  Consider derived features such as the difference in player free moves, legal moves, etc. in addition to the score difference. 

You will use your best implementation for your MancalaPlayer.  It should outperform the score difference heuristic in win rate when all other considerations are kept equivalent.

Notes:
- There is no one right answer.  This is an empirical research exercise, so you're encouraged to innovate and evaluate.  Use MancalaTournament to gain data to inform of the relative performances of different heuristic evaluation functions.
- Knowledge is power.  Using play data may help you build a predictive model for the utility of being in a given state.  A more advanced approach would use good play data to form a predictive model and then apply that predictive model to heuristic evaluation.
- You don't need the final score difference to be your terminal node value, but any value you use should be consistently used for terminal and nonterminal nodes.  For example, don't have your terminal node be score difference and your non-terminal nodes be an estimated probability of a first-player win.
- START EARLY.  Give yourself time to discover improvements.

4. **Alpha-Beta Pruning**: Learn about alpha-beta pruning and find pseudocode so that you can reimplement depth-limited minimax with alpha-beta pruning.  Note how the provided minimax implementation stores a local best move, so that the top-level recursive call will be the last to write the best move from its local best move.  Make a copy of MinimaxSearcher.java called <UniqueID>AlphaBetaSearcher.java.  Reimplement the eval method to call a recursive helper function with additional parameters alpha and beta.  

Notes:
- Be careful in implementing alpha-beta pseudocode that you take into account the fact that turns do not always alternate in Mancala.  The existence of free moves means that you need to have your code check a node's current player in order to make the correct recursive call. 
- Check your nodeCount with and without alpha-beta pruning.  You should see a significant reduction in node count with alpha-beta pruning.

5. **Time Management**: Read about time management algorithms for real-time play.  Make a copy of SimpleMancalaPlayer.java called <UniqueID>MancalaPlayer.java.  Experiment with different time allocation policies for search across a game.  Find one that utilizes most of the given time limit on the target hardware for tournament evaluation.  Take care to design your policy so as to never run out of time.  In such a case, all points are awarded to the opponent.

Notes:
- Simple, semi-dynamic/dynamic time management strategies of H. Baier, M. Winands. "Time Management for Monte-Carlo Tree Search in Go" are recommended.  
- A principled approach here is to collect play data in order to craft a good time managment strategy.  One can predict the number of remaining turns from the number of pieces in play.  One can predict the time cost of search given the number of remaining turns. 
- Err on the side of caution, e.g. having a dynamic check to make sure you are not overspending your time budget on any search.

Your entry will be evaluated with respect to (1) correct, error-free implementation, and (2) performance with respect to a basic benchmark such as the given SimpleMancalaPlayer improved with alpha-beta pruning.

Restrictions:
- Your instructor will likely want to place restrictions on memory footprint (e.g. no more than 1MB of file memory, no more than 1MB of RAM), as well as a restriction to use a single processor core (i.e. no parallelization). These should be determined by the instructor.
