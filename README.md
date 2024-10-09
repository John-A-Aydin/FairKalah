# Report
### Key Idea

FairKalah is a version of Mancala where all starting states are fair, meaning, with optimal play, the game will result in a draw. In this project, I found a suitable heuristic for minimax search, implemented alpha-beta pruning, and leveraged different time management strategies to build a capable Mancala player. This player will be pitted against a simple mancala player for evaluation.

## Data Needed:

The set of fair initial states for Mancala.

## Methods:

### Heuristics

The heuristic I chose looks at:

- Score difference
- Double moves
- Piece distribution

```Java
double w1 = 0.7;  
double w2 = 0.275;  
double w3 = 0.01;  
  
// First heuristic: Score diff  
int h1 = state[6] - state[13];  
  
// Second heuristic: Double turns 
// Third heuristic: Piece distribution
int h2 = 0;  
int h3 = 0;  
for (int i = 0; i < MAX_SCORE_PIT; i++) {
	if (state[i] + i == MAX_SCORE_PIT) {  
	    h2++;  
	} else if (state[i] == 0) {  
	    h3++;  
	}  
}
  
// Don't want your side to be too sparse or too full  
h3 = -(h3-3)*(h3-3) + 9;  
  
return w1*h1 + w2*h2 + w3*h3;
```

To find a suitable heuristic, I gave each of these metrics a weight and tested them in a fixed depth game against a simple player that only looks at score difference. Using binary search, I found the weights that produced the best results across all starting states.

Running my player against a simple player:

162 wins
75 losses
15 ties

### Alpha-Beta Pruning:

Alpha-Beta pruning removes nodes from minimax search that will not effect the outcome of the final result. This cuts the time for any given search significantly, but introduces large variations in time taken, where some searches take a very long time and others do not.

Both my player and the simple player were implemented using alpha beta pruning.

### Time Management:

Time management was the key in beating the simple mancala player.

#### Heuristic Optimization

The first issue I had to address was the time difference between my player and the simple player during state evaluation. Testing the two side by side with a search depth of 10, my player took around 630 ms during a full game, while the simple player only took around 300. To close this gap, I used several techniques including loop unrolling, minimizing floating point operations and typecasting, and cache optimization. With these techniques, I was able to bring the total play time, with a search depth of 10, down to 500 ms. 

#### Fail Safe

To prevent the program from going over it's allotted time, I measured the average time it takes for various search depths to complete a full game. Using this information, I created breakpoints where my player will switch from the prescribed time management strategy to using a constant depth that should be able to complete an entire game of mancala in the time that my player has remaining. I found that three levels of this provided optimal results.

#### Side Effects of Alpha-Beta Pruning

One of the first things I noticed while testing different time management strategies is that early game states took significantly longer to evaluate than mid or endgame states. These early game states almost always had search times that were comparable to minimax without alpha-beta pruning.

#### Performance:

Since each game takes around 5 minutes to complete, I only tested different time management strategies over 56 games.

Constant Depth with Fail Safe:
This strategy was the most consistent for me. With a constant depth of 15 my player utilized an average of 1 minute and 20 seconds per game or around half of the total time and only went into fail safe mode in one trial.

```Java
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
```


Wins: 32

Losses: 19

Ties: 5

Higher Depth Towards the End:
This strategy was very hard to get working correctly, but provided the best results. The strategy relies on the decreasing amount of nodes that need to be searched for any given search depth as the game progresses.

```Java
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
```

The first two moves in every game of FairKalah took 2-20x longer than any other move with the same search depth. A search depth of 14 was optimal for these moves because it provided solid depth while only allocating around 30 seconds of the total play time to these moves.

Wins: 36

Losses: 18

Ties: 4

## What I didn't get to do:

The project recommends using a dynamic or semi dynamic time strategy where your next move is derived from the amount of time you're willing to spend on that move. I wasn't able to do this because of the amount of variation I saw in the amount of time each move took. When I tried using this strategy my player was only using around a third or fourth of its time and never entered the emergency fail safe mode.

Another reason for this shortcoming was that each time I wanted to test a time management strategy, I had to use every thread on my computer for 10-20 minutes which severely limited the amount of changes I could try.

## Experience

This was a really cool project. It showed me how important the basic operation is when considering the time complexity of an algorithm, gave me hands on experience with the advantages and limitations of minimax search with alpha-beta pruning, and showed me the importance of computational power when working with AI.

I also inadvertently learned about overfitting. When I tested my heuristics I used a small constant depth, ran it across all 252 FairKalah states, and tweaked the weights until I found a local maximum. When I changed the depth and started working on time management I found that my program was worse than the simple player for the same depth of 15. I then had to rethink my heuristic and find one that would work more generally.