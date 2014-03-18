package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;

public abstract class LongDistancePiece extends Piece {
	ArrayList<Point> possibleDirections;
	
	LongDistancePiece(boolean isWhite, Point location, Board theBoard) {
		super(isWhite, location, theBoard);
		possibleDirections= new ArrayList<Point>();
	}

	
	ArrayList<Point> possibleMoves() {
		// TODO Auto-generated method stub
		ArrayList<Point> answer = new ArrayList<Point>();
		for(Point p : possibleDirections)
		{
			Point thisMightWork = new Point(myLocation.x+p.x,myLocation.y+p.y);
			
			while(myBoard.isOnBoard(thisMightWork)&&myBoard.isEmpty(thisMightWork))//if the offset is on the board and empty, add it.
			{
				answer.add(thisMightWork);
				thisMightWork=new Point(thisMightWork.x+p.x,thisMightWork.y+p.y);//gets the next point with the given offset
			}
			
			if(myBoard.isOnBoard(thisMightWork)&&myBoard.getPiece(thisMightWork).isWhite()!=this.isWhite())//if it's an enemy, we can attack it
			{
				answer.add(thisMightWork);
			}

		}
		return answer;
	}
	void addDirection(Point p)
	{
		possibleDirections.add(p);
	}

}
