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
		ArrayList<Move> legalMoves = getMyLegalMoves();
		Move theMove = getRandom(legalMoves);
		return theMove;
		
		
	}

	
	public ArrayList<Move> getMyLegalMoves()
	{
		ArrayList<Piece> myPieces =  getMyPieces();
		ArrayList<Move> answer = new ArrayList<Move>(myPieces.size()*3);

		myGame.deselectPiece();
		for(Piece p : myPieces)
		{
			myGame.parseInput(p.myLocation);//select the current piece
			ArrayList<Point> possibleEndPoints = myGame.possibleMoves;
			for(Point thePoint : possibleEndPoints)
			{
				if(!myGame.wouldBeInCheck(thePoint))
				{
					answer.add(new Move(p.myLocation, thePoint));
				}
				
			}
		}
		return answer;
	}
	
	
	protected Move getRandom(ArrayList<Move> legalMoves) {
		// TODO Auto-generated method stub
		Random r = new Random();
		return legalMoves.get(r.nextInt(legalMoves.size()));
	}

	private  ArrayList<Piece> getMyPieces()
	{
		return myGame.getPieces(myColor);
	}

	public int chooseUpgrade(Point p) {
		// TODO Auto-generated method stub
		return new Random().nextInt(4);
	}
}

