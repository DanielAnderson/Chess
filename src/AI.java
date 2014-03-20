package com.DanAnderson.Chess;

import java.awt.Point;

public abstract class AI {
	protected boolean myColor;
	protected Game myGame;
	public final static int RANDOM =1;
	public final static int RANDOM_AGGRESSIVE=2;
	
	public final int QUEEN=0;
	public final int KNIGHT=1;
	public final int ROOK=2;
	public final int BISHOP =3;
	

	public AI(Game theGame, boolean color)
	{
		myGame=theGame;
		myColor=color;
	}
	
	/* @Pre: 
	 * @Post: 
	 * @Return: 
	 */
	public void makeMove()
	{
		if(myGame.isGameOver())
		{
			return;
		}else
		{
			
			Move chosenMove=chooseMove();
			myGame.parseInput(chosenMove);
		}
	}
	
	/* @Pre: It is the turn of this AI
	 * @Post: No changes
	 * @Return: The ideal move, as determined by the AI
	 */
	public abstract Move chooseMove();
	
	/* @Pre: A pawn, at position p, needs to be upgraded, and was moved to p this turn
	 * @Post: No changes
	 * @Return: The integer representing which upgrade is desired
	 */
	public abstract int chooseUpgrade(Point p);

	public static AI createAI(Game game, int AItype, boolean color) {
		// TODO Auto-generated method stub
		if(AItype==RANDOM)
		{
			return new RandomAI(game, color);
		}else if(AItype==AI.RANDOM_AGGRESSIVE)
		{
			AI answer = new RandomAggressive(game,color);
			return answer;
		}else
		{
			return null;
		}
	}
}
