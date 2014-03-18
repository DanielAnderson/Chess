//todo, implement el passant move for pawns;

package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;

public class Pawn extends Piece{ 

	
	boolean hasMoved;

	Pawn(boolean isWhite, Point location, Board theBoard) {
		super(isWhite, location, theBoard);
		hasMoved=false;
	}


	public ArrayList<Point> possibleMoves() {
		int flipY=1;
		if(!isWhite)
		{
			flipY = -1;
		}
		Point singleMove = new Point(myLocation.x,myLocation.y+1*flipY);//moves one up/down
		Point doubleMove = new Point(myLocation.x,myLocation.y+2*flipY);//moves two up/dow
		Point attackLeft = new Point(myLocation.x-1,myLocation.y+1*flipY);//attacks one up/down, 1 left
		Point attackRight= new Point(myLocation.x+1,myLocation.y+1*flipY);//attacks one up/down, 1 right
		
		ArrayList<Point> answer= new ArrayList<Point>();
		if(myBoard.isEmpty(singleMove))
		{
			answer.add(singleMove);
			if(myBoard.isEmpty(doubleMove)&&canMoveTwo())//to move twice, it must be able to, and not have something right in front of it
			{
				answer.add(doubleMove);
			}
		}
		
		if(myBoard.isOnBoard(attackLeft)&&myBoard.getPiece(attackLeft)!=null)
		{
			if(myBoard.getPiece(attackLeft).isWhite()!=this.isWhite()){
				answer.add(attackLeft);
			}
		}
		
		if(myBoard.isOnBoard(attackRight)&&myBoard.getPiece(attackRight)!=null)
		{
			if(myBoard.getPiece(attackRight).isWhite()!=this.isWhite()){
				answer.add(attackRight);
			}
		}
		
		
		return answer;
	}


	private boolean canMoveTwo() {
		//if a white pawn's y location is 1, or a black pawn's y location is 6, it can do a double move
		return myLocation.y==1&&isWhite||myLocation.y==6&&!isWhite;
	}

	
//	public String toString()
//	{
//		return "P"+getColor();
//	}
	
	public String getImageName() {
		// TODO Auto-generated method stub
		if(isWhite)
		{
			return "pawn_white.png";
		}else
		{
			return "pawn_black.png";
		}
	}


}
