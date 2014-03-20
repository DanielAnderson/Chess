Author: Daniel Anderson
Email: Daniel.r.anderson2013@gmail.com
Name: Chess-DanAnderson
Version: 0.22

This is my chess program, written in pure Java. As of now, it is capable of supporting a two player game from the GUI, or either (or both) players being controlled by an AI. 

The AI's currently implemented are:
RandomAI: An AI which chooses a random move to make from all legal moves.
RandomAggressive: An AI which choose a random move which takes an opposing piece, or if none are possible, defaults to doing what RandomAI would do.
TreeAI: An AI which looks at all possible next moves, and choses the 'best' of them. It does this by assigning each piece (other than the king) a value, and tries to minimize the total value of the opponent. It does not look past one move, though that functionality has been built to be supported. If there are multiple moves which have the best associated value, it chooses randomly from them.

The next goal is to create a fairly simple AI, though one that actually 'thinks' and plans ahead. This  will probably be done by changing the TreeAI to plan ahead.

Another goal will be to implement an endgame AI which takes over for all of the others with the attempt to checkmate the opponent. As of now, the AI's have no concept of checkmating.

To get the program to run, the jar must be in the same directory as the Pieces directory.