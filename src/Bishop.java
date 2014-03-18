package com.DanAnderson.Chess;

import java.awt.Point;

public class Bishop extends LongDistancePiece {
	public boolean hasMoved;
	
	Bishop(boolean isWhite, Point location, Board theBoard) {
		super(isWhite, location, theBoard);
		
		//this piece can go diagonally
		addDirection(new Point(1,1)); 
		addDirection(new Point (1,-1));
		addDirection(new Point(-1,1));
		addDirection(new Point(-1,-1));
		
		hasMoved=false;
	}	

	public String getImageName() {
		// TODO Auto-generated method stub
		if(isWhite)
		{
			return "bishop_white.png";
		}else
		{
			return "bishop_black.png";
		}
	}

}
