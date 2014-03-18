package com.DanAnderson.Chess;

import java.awt.Point;

public class Queen extends LongDistancePiece{
	public boolean hasMoved;
	Queen(boolean isWhite, Point location, Board theBoard) {
		super(isWhite, location, theBoard);
		
		//this piece can go vertically, horizontally or diagonally
		addDirection(new Point(0,1));
		addDirection(new Point (0,-1));
		addDirection(new Point(1,0));
		addDirection(new Point(-1,0));
		addDirection(new Point(1,1));
		addDirection(new Point (-1,-1));
		addDirection(new Point(1,-1));
		addDirection(new Point(-1,1));

		
		hasMoved=false;
	}	
//	public String toString()
//	{
//		return "Q"+getColor();
//	}
	
	public String getImageName() {
		// TODO Auto-generated method stub
		if(isWhite)
		{
			return "queen_white.png";
		}else
		{
			return "queen_black.png";
		}
	}


}
