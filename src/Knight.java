package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;

public class Knight extends Piece {


	public Knight(boolean isWhite, Point location, Board theBoard) {
		super(isWhite, location,theBoard);
	}

	ArrayList<Point> possibleMoves() {
		// TODO Auto-generated method stub
		ArrayList<Point> answer = new ArrayList<Point>();
		for(int dx = -2;dx<=2;dx++)
		{
			if(dx==-2||dx==2)
			{
				for(int dy = -1;dy<=1;dy+=2)
				{
					Point pointToCheck = new Point(this.myLocation.x+dx,this.myLocation.y+dy);
					if(myBoard.isOnBoard(pointToCheck)&&(myBoard.getPiece(pointToCheck)==null||myBoard.getPiece(pointToCheck).isWhite()!=this.isWhite())){
						answer.add(pointToCheck);
					}
				}
			}else if( dx==-1||dx==1)
			{
				for(int dy = -2; dy<=2;dy+=4)
				{
					Point pointToCheck = new Point(this.myLocation.x+dx,this.myLocation.y+dy);
					if(myBoard.isOnBoard(pointToCheck)&&(myBoard.getPiece(pointToCheck)==null||myBoard.getPiece(pointToCheck).isWhite()!=this.isWhite()))
					{
						answer.add(pointToCheck);
					}
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
			return "knight_white.png";
		}else
		{
			return "knight_black.png";
		}
	}

}
