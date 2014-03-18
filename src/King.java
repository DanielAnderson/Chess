package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;

public class King extends Piece {

	King(boolean isWhite, Point location, Board theBoard) {
		super(isWhite, location, theBoard);
		
	}

	ArrayList<Point> possibleMoves() {
		ArrayList<Point> answer = new ArrayList<Point>();
		for(int deltaX= -1; deltaX<=1;deltaX++)
		{
			for(int deltaY=-1; deltaY<=1;deltaY++)
			{
				Point pointToCheck = new Point(myLocation.x+deltaX,myLocation.y+deltaY);
				
				/* this piece can move somewhere if the place it is moving to is either open or if it has an opponent piece in it
				 * 
				 * 
				 */
				if(myBoard.isOnBoard(pointToCheck)&&(myBoard.isEmpty(pointToCheck)||myBoard.getPiece(pointToCheck).isWhite()!=isWhite))
				{
					answer.add(pointToCheck);
				}
			}
		}
		return answer;
	}
//	public String toString()
//	{
//		return "K"+getColor();
//	}
	
	public String getImageName() {
		// TODO Auto-generated method stub
		if(isWhite)
		{
			return "king_white.png";
		}else
		{
			return "king_black.png";
		}
	}

}
