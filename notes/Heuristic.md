# Search Algoriths

### BFS
- Wont work for this problem since I'm looking for the best ending, not the first

### DFS
- Might be the best option, but will look at all nodes

### Best First Search
- Might work, but I will need to figure out when to skip subtrees

# Other Ideas

Could look at the score obtained from a subtree and ignore current score as it has little effect on gameplay.

Assume opponent is playing optimally / might only need to look at optimal move for opponent

could hash board state for dynamic programming

# Gameplay

### Turns

In each turn I have 12 possible choices which opens up 12 choices for the opponent. Could use this knowledge to choose the option where opponent's best choice next turn is the worst out of the twelve.

NEED TO CHECK IF MULTIPLE TURNS ARE ALLOWED
