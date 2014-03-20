package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;

public class RandomAggressive extends RandomAI {

	public RandomAggressive(Game theGame, boolean color) {
		super(theGame, color);
	}
	
	public Move chooseMove()
	{
		ArrayList<Move> allMoves = myGame.getMyLegalMoves();
		ArrayList<Move> attackingMoves = this.getAttackingMoves(allMoves);
		if(attackingMoves.size()!=0)
		{
			return super.getRandom(attackingMoves);
		}
		else
		{
			return super.getRandom(allMoves);
		}
	}

	private ArrayList<Move> getAttackingMoves(ArrayList<Move> allMoves) {
		// TODO Auto-generated method stub
		ArrayList<Move> answer = new ArrayList<Move>(allMoves.size()); 
		
		for(Move m : allMoves)
		{
			Piece pieceBeingAttacked=myGame.getPiece(m.getCurrentLocation());
			if(pieceBeingAttacked!=null&&pieceBeingAttacked.isWhite!=this.myColor|| isPawnAttack(m))
			{
				answer.add(m);
			}
		}
		return answer;
	}

	private boolean isPawnAttack(Move m) {
		// TODO Auto-generated method stub
		if(myGame.getPiece(m.getPreviousLocation()) instanceof Pawn)
		{
			Point difference = Game.getDistance(m.getPreviousLocation(), m.getCurrentLocation());
			if(difference.x!=0&&difference.y!=0)//pawn must be attacking, takes care of en passant
			{
				return true;
			}
		}
		return false;
	}

}
