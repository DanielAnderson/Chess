package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Piece {

	Point myLocation;
	final boolean isWhite;
	Board myBoard;
	
	
	private boolean hasMoved;//keeps track of whether or not a piece has moved.
	public static final int baseValue=65;
	public static final int kingValue=0;
	public static final int queenValue = 1;
	public static final int bishopValue=2;
	public static final int knightValue=3;
	public static final int rookValue=4;
	public static final int pawnValue=5;
	public static final int whiteValue=0;
	public static final int blackValue=6;
	/*char(65) is the baseline
	 * King adds   0
	 * Queen adds  1
	 * Bishop adds 2
	 * Knight adds 3
	 * Rook adds   4
	 * Pawn adds   5
	 * white adds  0
	 * black adds  6
	 * */
	
	Piece(boolean isWhite, Point location, Board theBoard)
	{
		this.isWhite=isWhite;
		myLocation=location;
		myBoard=theBoard;
		hasMoved=false;
	}
	
	
	abstract ArrayList<Point> possibleMoves();
	
	boolean isWhite() {
		return isWhite;
	}
	
	
	public static void setPieceOnBoard(char theCharacter, Point location, Board newBoard)
	{
		theCharacter-=baseValue;//get rid of the base value
		boolean isWhite = theCharacter/blackValue==0;//figure out whether it is black or not
		
		char type = (char) (theCharacter%6);//find what type of piece it is
		if(type==kingValue)
		{
			King theKing = new King(isWhite,location,newBoard);
			newBoard.setPiece(theKing);
			if(isWhite)
			{
				newBoard.setWhiteKing(theKing);
			}else
			{
				newBoard.setBlackKing(theKing);
			}
		}else if(type==queenValue)
		{
			Piece theQueen=new Queen(isWhite,location,newBoard);
			newBoard.setPiece(theQueen);
		}
		else if(type ==bishopValue)
		{
			Piece theBishop=new Bishop(isWhite,location,newBoard);
			newBoard.setPiece(theBishop);
		}
		else if(type ==rookValue)
		{
			Piece theRook = new Rook(isWhite,location,newBoard);
			newBoard.setPiece(theRook);
		}
		else if(type==knightValue)
		{
			Piece theKnight = new Knight(isWhite,location,newBoard);
			newBoard.setPiece(theKnight);
		}else if(type==pawnValue)
		{
			Piece thePawn = new Pawn(isWhite,location,newBoard);
			newBoard.setPiece(thePawn);
		}
	}
	
	public final String toString()
	{
		char myChar =baseValue;
		if(this instanceof Pawn)
		{
			myChar+=pawnValue;
		}else if(this instanceof Queen)
		{
			myChar+=queenValue;
		}else if(this instanceof Bishop)
		{
			myChar+=bishopValue;
		}else if(this instanceof Knight)
		{
			myChar+=knightValue;
		}else if(this instanceof Rook)
		{
			myChar+=rookValue;
		}else if(this instanceof King)
		{
			myChar+=kingValue;
		}else
		{
			throw new AssertionError("The piece isn't a piece");
		}
		
		if(this.isWhite)
		{
			myChar+=whiteValue;
		}
		
		else if(!this.isWhite)
		{
			myChar+=blackValue;
		}
		return String.valueOf((char)myChar);
		
	}

	public void setLocation(Point theLocation) {
		myLocation = theLocation;
		
	}
	abstract public String getImageName();
	public String getColor()
	{
		if(isWhite)
		{
			return "white";
		}else
		{
			return "black";
		}
	}
	public int getX()
	{
		return myLocation.x;
	}
	public int getY()
	{
		return myLocation.y;
	}
	
	public void wasMoved()
	{
		hasMoved=true;
	}
	
	public boolean hasMoved()
	{
		return hasMoved;
	}
	
	public Point getPoint()
	{
		return new Point(myLocation.x,myLocation.y);
	}
}
