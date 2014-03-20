package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class RandomAI extends AI{
	
	public RandomAI(Game theGame, boolean color)
	{
		super(theGame,color);
	}
	public Move chooseMove()
	{
		if(myGame.isGameOver())
		{
			return null;
		}
		ArrayList<Move> legalMoves = myGame.getMyLegalMoves();
		Move theMove = getRandom(legalMoves);
		return theMove;
		
		
	}

	
	
	
	protected static Move getRandom(ArrayList<Move> legalMoves) {
		// TODO Auto-generated method stub
		Random r = new Random();
		return legalMoves.get(r.nextInt(legalMoves.size()));
	}


	public int chooseUpgrade(Point p) {
		// TODO Auto-generated method stub
		return new Random().nextInt(4);
	}
}

