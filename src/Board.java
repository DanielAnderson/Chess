package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;

public class Board {
	
	
	//(x,0) is bottom, white is there
	//(x,7) is top, black is there
	Piece[][] theGrid;
	
	public final boolean WHITE = true;
	public final boolean BLACK = false;
	private King whiteKing;
	private King blackKing;
		
	public Board(boolean defaultBoard)
	{
		if(defaultBoard)
		{
			theGrid = new Piece[8][8];
			addPieces();
		}else
		{
			theGrid=new Piece[8][8];
		}
	}
	
	private void addPieces() {
		for(int x = 0 ; x< 8 ; x++)
		{
			Pawn whitePawn = new Pawn(true, new Point(x,1),this);
			Pawn blackPawn = new Pawn(false, new Point(x,6),this);
			
			Piece strongBlack=null;
			Piece strongWhite = null;
			if(x==0||x==7)
			{
				strongWhite = new Rook(true,new Point(x,0),this);
				strongBlack = new Rook(false, new Point(x,7),this);
			}else if(x==1||x==6)
			{
				strongWhite = new Knight(true, new Point(x,0),this);
				strongBlack = new Knight(false, new Point(x,7),this);

			}else if(x==2||x==5)
			{
				strongWhite = new Bishop(true, new Point(x,0),this);
				strongBlack = new Bishop(false, new Point(x,7),this);

			}else if(x==3)
			{
				strongWhite=new King(true, new Point(x,0),this);
				whiteKing=(King) strongWhite;
				strongBlack = new King(false, new Point(x,7),this);
				blackKing = (King) strongBlack;
			}
			else if(x==4)
			{
				strongWhite=new Queen(true, new Point(x,0),this);
				strongBlack = new Queen(false, new Point(x,7),this);
			}
			setPiece(whitePawn);
			setPiece(blackPawn);
			setPiece(strongWhite);
			setPiece(strongBlack);
		}
		
	}
	
	public King getWhiteKing()
	{
		return whiteKing;
	}
	public King getBlackKing()
	{
		return blackKing;
	}

	//returns whether a point is on the board. IE, its x, y positions are in the range [0,8)
	public boolean isOnBoard(Point p)
	{
		return p.x<8&&p.x>=0&&p.y<8&&p.y>=0;
	}
	
	/* @Pre: None
	 * @Post: No changes to the board
	 * @Return: If p is on theGrid and theGrid.get(p)==null, return true. Otherwise return false
	 */
	public boolean isEmpty(Point p)
	{
		return isOnBoard(p)&&null==getPiece(p);
	}

	public Piece getPiece(Point p) {
		// TODO Auto-generated method stub
		
		return theGrid[p.x][p.y];
	}
	
	public void setPiece(Piece p)
	{
		if(getPiece(p.myLocation)==null)
		{
			theGrid[p.myLocation.x][p.myLocation.y]=p;
		}else
		{
			throw new AssertionError("We can't have two pieces at: "+p.myLocation);
		}
	}

	public void movePiece(Piece thePiece, Point theLocation)
	{
		thePiece.wasMoved();
		theGrid[thePiece.myLocation.x][thePiece.myLocation.y]=null;//no longer have a piece there.
		thePiece.setLocation(theLocation);
		theGrid[theLocation.x][theLocation.y]=thePiece;
	}
	
	//should probably only be called for en passant removal
	public void removePiece(Piece thePiece)
	{
		theGrid[thePiece.myLocation.x][thePiece.myLocation.y]=null;
	}
	public void upgradePawn(Point location, Piece newPiece)
	{
		if(!this.isOnBoard(location))
		{
			throw new AssertionError("This location: "+location+"isn't on the board");
		}
		
		if(!(newPiece instanceof Queen ||newPiece instanceof Rook ||newPiece instanceof Bishop||newPiece instanceof Knight))//if it isn't a queen, rook, bishop or knight, we can't upgrade it
		{
			throw new AssertionError("A pawn can't be upgraded to a"+newPiece.getClass().toString());
		}
		
		if(!(this.getPiece(location)instanceof Pawn))//we can't upgrade something that isn't a pawn
		{
			throw new AssertionError("You can't upgrade a "+ newPiece.getClass().toString());
		}
		
		newPiece.setLocation(location);
		
		theGrid[location.x][location.y]=newPiece;
	}
	
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		for(int j = 0 ; j < 8 ;j++)
		{
			for(int i = 0 ;i<8;i++)
			{
				Piece p =theGrid[i][j];
				if(p!=null)
				{
					b.append(p.toString());
				}
				else
				{
					b.append(" ");
				}
				
			}
			//b.append("\n");
		}
		return b.toString();
	}
	public void setWhiteKing(King p)
	{
		if(whiteKing!=null)
		{
			throw new AssertionError("You can't change kings in the middle of the game!");
		}
		
		whiteKing=p;
	}
	public void setBlackKing(King p)
	{
		if(blackKing!=null)
		{
			throw new AssertionError("You can't change kings in the middle of the game!");
		}
		blackKing=p;
	}
	
	
	//returns all pieces of the given color
	public ArrayList<Piece> getPieces(boolean color)
	{
		ArrayList<Piece> answer = new ArrayList<Piece>();
		for(Piece[] pieces:theGrid)
		{
			for(Piece p : pieces)
			{
				if(p!=null&&p.isWhite()==color)
				{
					answer.add(p);
				}
			}
		}
		return answer;
	}
	
}
