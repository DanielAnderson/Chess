package com.DanAnderson.Chess;

import java.awt.Point;

public class Rook extends LongDistancePiece {

	public boolean hasMoved;
	Rook(boolean isWhite, Point location, Board theBoard) {
		super(isWhite, location, theBoard);
		
		//this piece can go horizontally or vertically
		addDirection(new Point(0,1));
		addDirection(new Point (0,-1));
		addDirection(new Point(1,0));
		addDirection(new Point(-1,0));
		
		hasMoved=false;
	}	
	
	
	public String getImageName() {
		// TODO Auto-generated method stub
		if(isWhite)
		{
			return "rook_white.png";
		}else
		{
			return "rook_black.png";
		}
	}
}
