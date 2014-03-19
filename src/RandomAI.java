package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class RandomAI {
	Game myGame;
	private boolean myColor;
	
	public RandomAI(Game theGame, boolean color)
	{
		myGame = theGame;
		myColor=color;
	}
	
	public ArrayList<Move> getMyLegalMoves()
	{
		ArrayList<Piece> myPieces =  getMyPieces();
		ArrayList<Move> answer = new ArrayList<Move>(myPieces.size()*3);

		
		for(Piece p : myPieces)
		{
			myGame.selectPiece(p.myLocation);
			ArrayList<Point> possibleEndPoints = myGame.possibleMoves;
			for(Point thePoint : possibleEndPoints)
			{
				answer.add(new Move(p.myLocation, thePoint));
			}
		}
		return answer;
	}
	
	public void makeMove()
	{
		ArrayList<Move> legalMoves = getMyLegalMoves();
		Move theMove = getRandom(legalMoves);
		myGame.selectPiece(theMove.getPreviousLocation());
		myGame.movePiece((theMove.getCurrentLocation()));
	}
	
	private Move getRandom(ArrayList<Move> legalMoves) {
		// TODO Auto-generated method stub
		Random r = new Random();
		return legalMoves.get(r.nextInt(legalMoves.size()));
	}

	private  ArrayList<Piece> getMyPieces()
	{
		return myGame.getPieces(myColor);
	}
}

