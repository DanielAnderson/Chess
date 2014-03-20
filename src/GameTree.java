package com.DanAnderson.Chess;

import java.util.ArrayList;

public class GameTree {
	private ArrayList<GameTree> myChildren;
	Game myGame;
	private int myDepth;
	Move myMove;
	int myValue;
	
	public GameTree(Game g, int depth,Move moveToGetHere)
	{
		myDepth=depth;
		myChildren=(new ArrayList<GameTree>(35));
		myGame=g;
		myMove = moveToGetHere;
		if(myDepth>0)
		{
			addChildren();
		}
		
		myValue=determineValue();
		
	}

	private int determineValue() {
		// TODO Auto-generated method stub
		ArrayList<Piece> whitePieces = myGame.getPieces(Game.WHITE);
		ArrayList<Piece> blackPieces = myGame.getPieces(Game.BLACK);
		
		return sumValue(whitePieces)-sumValue(blackPieces);
	}

	private int sumValue(ArrayList<Piece> thePieces) {
		int answer=0;
		for(Piece p : thePieces)
		{
			if(p instanceof Pawn)
			{
				answer+=1;
			}else if(p instanceof Knight|| p instanceof Bishop)
			{
				answer+=3;
			}else if(p instanceof Rook)
			{
				answer+=5;
			}else if(p instanceof Queen)
			{
				answer+=9;
			}
		}
		return answer;
	}

	public void addChildren() {
		ArrayList<Move> myMoves = myGame.getMyLegalMoves();
		for(Move m : myMoves)
		{
			Game resultantGame = Game.createGame(myGame, m);
			System.out.println(resultantGame);
			getMyChildren().add(new GameTree(Game.createGame(myGame,m),myDepth-1,m));
		}
		System.out.println("*****************************************");
	}

	public ArrayList<GameTree> getMyChildren() {
		return myChildren;
	}

}
