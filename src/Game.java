package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;
import java.util.jar.JarException;

import javax.swing.JOptionPane;

public class Game {

	private Board myBoard;
	private boolean whitesTurn;
	public Piece chosenPiece;
	public ArrayList<Point> possibleMoves;
	public final boolean LEFT =false;
	public final boolean RIGHT = true;
	private Move lastMove;
	private int moveNumber;
	
	public Game()
	{
		boolean isDefaultSetup=true;
		myBoard = new Board(isDefaultSetup);
		whitesTurn=true;
		moveNumber=0;
	}
	public Game(Board theBoard, boolean whitesTurn)
	{
		myBoard=theBoard;
		this.whitesTurn=whitesTurn;
		moveNumber=0;
	}
	
	
	//takes the point that was clicked on, and parses and processes the input
	public void parseInput(Point thePoint) {
		// TODO Auto-generated method stub
		Piece thePiece = this.chosenPiece;
		boolean moveWorked=false;
		if(thePiece==null)//no piece is currently chosen. Attempt to choose the piece selected
		{
			this.selectPiece(thePoint);

		}else if(this.possibleMoves.contains(thePoint))//thePiece is not null, and the selected piece can go to the cell clicked
		{			
			if(!this.wouldBeInCheck(thePoint))//if the selected move wouldn't move it into check, then lets move it
			{
				Point previousLocation =thePiece.myLocation;
				 moveWorked=this.movePiece(thePoint);
				
				//if the move worked, then that was the previous move
				if(moveWorked)
				{
					thePiece.wasMoved();
					lastMove = new Move(previousLocation,thePoint);
				}
				//check to possibly upgrade the pawn
				if(thePiece instanceof Pawn)
				{
					
					if(thePiece.getY()==7||thePiece.getY()==0)//then the piece is at the end of the board and needs to be updated
					{
						this.upgradePawn(thePiece);
					}
				}

			}else
			{
				JOptionPane.showMessageDialog(null, "This move would put you in check, please move otherwise.", "Illegal move.", 0);
			}
		}else//the selected move isn't possible, we want to deselect the piece and reset the possibleMoves
		{
			this.selectPiece(new Point(100,100));//way out of the board, will set both as null
		}
		
		if(moveWorked&&this.isInCheck())
		{
			String movingPlayer;
			String otherPlayer;
			if(isWhitesTurn())
			{
				movingPlayer="Black ";
				otherPlayer="White ";
			}else
			{
				movingPlayer="White ";
				otherPlayer="Black ";
			}

			if(canGetOutOfCheck())
			{
				JOptionPane.showMessageDialog(null, otherPlayer+" player, you are now in check.", "In check", 0);
			}else//can't get out of check, game is over with other team winning
			{
				JOptionPane.showMessageDialog(null, "Congrats, "+movingPlayer.toLowerCase()+ "player, you won!");
			}
		}

	}

	/* @Pre: Called only if the player who just got their turn is in check, called by parseInput()
	 * @Post: TODO
	 * @Return: whether or not the king in check can get out
	 */
	private boolean canGetOutOfCheck() {
		ArrayList<Piece> piecesAttacking =enemyPiecesThatCanAttack(getCurrentKing().getPoint());
		return canMoveOutOfCheck()||canKillAllAttackingEnemies(piecesAttacking)||canBlockAttack(piecesAttacking);
	}
	
	
	private boolean canBlockAttack(ArrayList<Piece> piecesAttacking) {
		// TODO Auto-generated method stub
		if(piecesAttacking.size()>1)
		{
			return false;//can't block two different paths
		}
		Piece pieceAttacking = piecesAttacking.get(0);
		
		if(pieceAttacking instanceof Knight || pieceAttacking instanceof Pawn)
		{
			return false;//can't block knights (jump) or pawns (right next to the king)
		}
		
		//gets all points between the king and the king attacking it
		ArrayList<Point> pointsBetween = Game.getPointsBetween(getCurrentKing().getPoint(),pieceAttacking.getPoint());
		
		for(Point p : pointsBetween)
		{
			ArrayList<Piece> blockingPieces=piecesThatCanAttack(p,isWhitesTurn());
			if(blockingPieces.size()!=0)
			{
				for(Piece b : blockingPieces)//go through each possible blocking pieces
				{
					if(!wouldBeInCheck(b, p))
					{
						return true;//can block this way.
					}
				}
			}
		}
		return false;
		
		
	}
	
	//returns the point which has vector subtraction of point-point2
	private boolean canKillAllAttackingEnemies(ArrayList<Piece> piecesAttackingKing) {
		// TODO Auto-generated method stub
		if(piecesAttackingKing.size()>1)
		{
			return false;//can't kill two different pieces in one turn
		}
		
		ArrayList<Piece> myPieces=myBoard.getPieces(isWhitesTurn());
		for(Piece p : myPieces)
		{
			try{
			if(p.possibleMoves().contains(piecesAttackingKing.get(0).getPoint()))//can kill attacking piece
			{
				if(!this.wouldBeInCheck(p, piecesAttackingKing.get(0).getPoint())){//wouldn't be in check if we killed like this
					return true;
				}
			}}
			catch(java.lang.IndexOutOfBoundsException exception)
			{
				exception.printStackTrace();
			}
		}
		return false;
	
	}
	
	private King getCurrentKing() {
		// TODO Auto-generated method stub
		if(isWhitesTurn())
		{
			return myBoard.getWhiteKing();
		}else
		{
			return myBoard.getBlackKing();
		}
	}

	ArrayList<Piece> piecesThatCanAttack(Point p, boolean color)
	{
		ArrayList<Piece> thePieces =myBoard.getPieces(color);
		int indexToCheck=0;
		while(indexToCheck<thePieces.size())
		{
			if(thePieces.get(indexToCheck).possibleMoves().contains(p))//this piece can attack the given point
			{
				indexToCheck++;
			}else
			{
				thePieces.remove(indexToCheck);//cant attack the point, get rid of it
			}
		}
		return thePieces;

	}
	
	
	ArrayList<Piece> enemyPiecesThatCanAttack(Point p)
	{
		return  piecesThatCanAttack(p,!isWhitesTurn());
		
	}
	private boolean canMoveOutOfCheck() {
		King theKing =this.getCurrentKing();
		for(Point p :theKing.possibleMoves())//if any of them wouldn't place the king in check, it works
		{
			if(!(wouldBeInCheck(theKing,p)))
			{
				return true;
			}
		}
		return false;
	}
	
	/* @Pre: None
	 * @Post: No changes
	 * @Return: Whether moving the given piece to the given Point would put the moving team into check
	 * 		    Note: this method will return the same value even if the move isn't otherwise legal.
	 *			Note: do not replace this with a call to "isUnderAttack(Point p)" because moving a piece could change that
	 */
	private boolean wouldBeInCheck(Piece thePieceMoving,Point whereToMove)
	{
		if(whereToMove.equals(thePieceMoving.myLocation))
		{
			return isInCheck();
		}
		Board testBoard = new Board(false);
		boolean theTurn = this.isWhitesTurn();//check if the new board set up puts the currently moving king in check
		char[] theCharArray = this.toString().toCharArray();
		theCharArray[whereToMove.x+8*whereToMove.y]=theCharArray[thePieceMoving.myLocation.x+8*thePieceMoving.myLocation.y];//shift the chosen piece to the new potential position
		theCharArray[thePieceMoving.myLocation.x+8*thePieceMoving.myLocation.y]=" ".charAt(0);//no longer a piece there
		
		//sets up the board as if the selected piece was moved to point p
		for(int i = 0 ; i < theCharArray.length;i++)
		{
			if(theCharArray[i]!=" ".charAt(0))
			{
				Point location = new Point();
				location.x=i%8;
				location.y=i/8;
				Piece.setPieceOnBoard(theCharArray[i], location, testBoard);
			}
		}
		Game testGame = new Game(testBoard,theTurn);
		return testGame.isInCheck();

	}
	/* @Pre: A piece is selected
	 * @Post: No changes
	 * @Return: Whether moving the currently selected piece to the given Point would put the moving team into check
	 * 		    Note: this method will return the same value even if the move isn't otherwise legal.
	 *			Note: do not replace this with a call to "isUnderAttack(Point p)" because moving a piece could change that
	 */
	private boolean wouldBeInCheck(Point p)
	{
		return wouldBeInCheck(chosenPiece,p);
	}
	
	/* @Pre: None
	 * @Post: No changes
	 * @Return: whether or not the king whose move it currently is is in check
	 */
	private boolean isInCheck()
	{
		King currentKing;
		if(whitesTurn)
		{
			currentKing=myBoard.getWhiteKing();
		}else
		{
			currentKing=myBoard.getBlackKing();
		}

		Point kingLocation = new Point(currentKing.myLocation);
		return this.isUnderAttack(kingLocation);
		
	}
	
	/* Pre: none
	 * Post: If the piece is a valid choice, then the piece is selected and possibleMoves is set to it's possible moves.
	 *       Otherwise, chosenPiece is set to null, and possibleMoves is set to null
	 * Return: None
	 */
	public void selectPiece(Point p)
	{
		chosenPiece=null;
		possibleMoves=null;
		
		if(!myBoard.isOnBoard(p))//it isn't on the board
		{
			return;//chosenPiece and possible moves should be null
		}
		
		Piece thePiece = myBoard.getPiece(p);
		
		if(thePiece==null)
		{
			return;//if the point doesn't have any piece in it, then chosenPiece and possible moves should be null
		}
		
		if(thePiece.isWhite==whitesTurn)//the piece chosen was the correct color
		{
			chosenPiece=thePiece;
		}else
		{
			return;
		}

		possibleMoves=thePiece.possibleMoves();
		if(thePiece instanceof King)
		{
			if(!thePiece.hasMoved())//the king hasn't moved yet, check if we can castle it
			{
				checkIfCanCastle();
			}
		}
		
		//check if en passant is a legal move, and if it is, then add it to the list of possible moves
		if(thePiece instanceof Pawn)
		{
			addPossibleEnPassantMoves(p);
		}
		
	}
	/* @Pre: PossibleMoves!=null
	 * 		 Called by selectPiece if and only if the move that was selected is a pawn
	 * @Post: If an en passant move is legal, it is added to the list of possible moves
	 * @Return: None
	 */
	private void addPossibleEnPassantMoves(Point p) {
		Point left = new Point(p.x-1,p.y);
		Point right = new Point(p.x+1,p.y);
		Piece thePiece = chosenPiece;
		
		if(myBoard.isOnBoard(left))
		{
			
			Piece mightBeAPiece=getPiece(left);
			if(mightBeAPiece instanceof Pawn && mightBeAPiece.isWhite()!=thePiece.isWhite())
			{
				if(checkIfCanEnPassant(LEFT))
				{
					int dx = -1;
					int dy;
					if(thePiece.isWhite()){//the move would be in the positive y direction
						dy=1;
					}else
					{
						dy=-1;
					}
					
					possibleMoves.add(new Point(thePiece.getX()+dx,thePiece.getY()+dy));
					
					
				}
			}
		}
		if(myBoard.isOnBoard(right))
		{
			Piece mightBeAPiece=getPiece(right);
			if(mightBeAPiece instanceof Pawn && mightBeAPiece.isWhite()!=thePiece.isWhite())
			{

				if(checkIfCanEnPassant(RIGHT))
				{
					int dx = 1;
					int dy;
					if(thePiece.isWhite()){//the move would be in the positive y direction
						dy=1;
					}else
					{
						dy=-1;
					}
					
					possibleMoves.add(new Point(thePiece.getX()+dx,thePiece.getY()+dy));

				}
			}

		}
			

		
	}

	/* @Pre: Called by checkIfCanEnPassant, chosenPiece!=null
	 * @Post: No changes
	 * @Return: Whether or not the chosen piece can en passant in the current direction
	 */
 	private boolean checkIfCanEnPassant(boolean leftOrRight) {
		if(leftOrRight==RIGHT)
		{
			if(lastMove.getCurrentLocation().equals(new Point(chosenPiece.getX()+1,chosenPiece.getY())))
			{
				if(Math.abs(lastMove.getCurrentLocation().y-lastMove.getPreviousLocation().y)==2)
				{
					return true;
				}
			}
		}
		if(leftOrRight==LEFT)
		{
			if(lastMove.getCurrentLocation().equals(new Point(chosenPiece.getX()-1,chosenPiece.getY())))
			{
				if(Math.abs(lastMove.getCurrentLocation().y-lastMove.getPreviousLocation().y)==2)
				{
					return true;
				}
			}
		}
		return false;
	}
	//checks whether or not castling is possible, and if it is, then add the possible moves
	private void checkIfCanCastle() {
		//if it is blacks turn and black king has moved, or vice versa, can't castle
		if((myBoard.getBlackKing().hasMoved()&&!whitesTurn)||(myBoard.getWhiteKing().hasMoved()&&whitesTurn))
		{
			return;
		}
		
		int yValue = chosenPiece.getY();
		Piece closeCorner = getPiece(new Point(0,yValue));
		Piece farCorner = getPiece(new Point(7,yValue));
		
		//if they arent there, or have moved, or aren't rooks, then can't castle that direction
		boolean closeRookMoved = closeCorner==null||closeCorner.hasMoved()||!(closeCorner instanceof Rook);
		boolean farRookMoved=farCorner==null||farCorner.hasMoved()||!(farCorner instanceof Rook);
		
		if(!closeRookMoved)
		{
			if(canCastle(LEFT))
			{
				possibleMoves.add(new Point(1,yValue));//can castle left
			}
			
		}
		
		
		if(!farRookMoved)
		{
			if(canCastle(RIGHT))
			{
				possibleMoves.add(new Point(5,yValue));
			}

			
		}
		
		
		
	}
	/* Pre: The rook in the direction "direction", has not moved, and the king has not moved. The king is currently selected
	 * Post: No changes
	 * Return: Whether or not the chosen king can castle in the given direction
	 */
	private boolean canCastle(boolean direction) {
		// TODO Auto-generated method stub
		if(isInCheck())//can't castle out of check
		{
			return false;
		}
		
		if(direction==RIGHT)
		{
			Point oneRight = new Point(chosenPiece.getX()+1,chosenPiece.getY());
			Point twoRight = new Point(chosenPiece.getX()+2,chosenPiece.getY());
			if(getPiece(oneRight)!=null||getPiece(twoRight)!=null)
			{
				return false;//if either of them aren't empty, you can't castle
			}
			if(isUnderAttack(oneRight)||isUnderAttack(twoRight))
			{
				return false;//can't move through, into or out of check through castling
			}
			if(getPiece(new Point(6,chosenPiece.getY()))!=null)//there is a piece between the king and the rook
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		if(direction==LEFT)
		{
			Point oneLeft = new Point(chosenPiece.getX()-1,chosenPiece.getY());
			Point twoLeft = new Point(chosenPiece.getX()-2,chosenPiece.getY());
			
			if(getPiece(oneLeft)!=null||getPiece(twoLeft)!=null)
			{
				return false;//if either of them aren't empty, you can't castle
			}
			if(isUnderAttack(oneLeft)||isUnderAttack(twoLeft))
			{
				return false;//can't move through, into or out of check through castling
			}
			else
			{
				return true;
			}

		}
		throw new AssertionError("We shouldn't be able to get here");
	}
	
	/* @Pre: None
	 * @Post: no changes
	 * @Return: Returns whether or not a current point is under attack by the enemy
	 */
	private boolean isUnderAttack(Point pointToCheck) {
		// TODO Auto-generated method stub
		return knightsCanAttack(pointToCheck)||diagonalCanAttack(pointToCheck)||lineCanAttack(pointToCheck)||pawnCanAttack(pointToCheck)||kingCanAttack(pointToCheck);
	}
	
	/* @Pre: None
	 * @Post: no changes
	 * @Return: whether or not this point is under attack by an enemy king
	 */
	private boolean kingCanAttack(Point pointToCheck) {
		// TODO Auto-generated method stub
		King enemyKing;
		if(isWhitesTurn())
		{
			enemyKing=myBoard.getBlackKing();
		}else
		{
			enemyKing=myBoard.getWhiteKing();
		}
		int xDistance = Math.abs(pointToCheck.x-enemyKing.getX());
		int yDistance = Math.abs(pointToCheck.y-enemyKing.getY());
		
		if(xDistance<=1&&yDistance<=1)
		{
			return true;
		}
		return false;
	}
	
	
	/* @Pre: None
	 * @Post: no changes
	 * @Return: whether or not this point is under attack by an enemy pawn
	 */
	private boolean pawnCanAttack(Point pointToCheck) {
		ArrayList<Point> possiblePawnLocations= new ArrayList<Point>();
		
		//puts the appropriate positions a pawn could attack from into the list
		if(isWhitesTurn())
		{
			possiblePawnLocations.add(new Point(pointToCheck.x+1,pointToCheck.y+1));
			possiblePawnLocations.add(new Point(pointToCheck.x-1,pointToCheck.y+1));

		}else
		{
			possiblePawnLocations.add(new Point(pointToCheck.x+1,pointToCheck.y-1));
			possiblePawnLocations.add(new Point(pointToCheck.x-1,pointToCheck.y-1));

		}
		
		for(Point p : possiblePawnLocations)
		{
			if(myBoard.isOnBoard(p))
			{
				Piece thePiece = getPiece(p);
				if(thePiece instanceof Pawn&& thePiece.isWhite!=isWhitesTurn())
				{
					return true;
				}
			}
		}
		return false;
	}
	/* @Pre: None
	 * @Post: no changes
	 * @Return: whether or not this point is under attack by an enemy rook or queen (by line) 
	 */
	private boolean lineCanAttack(Point pointToCheck)
	{
		ArrayList<Point> directions=new ArrayList<Point>();
		//adds the 4 line directions to the list
		directions.add(new Point(0,1));
		directions.add(new Point(0,-1));
		directions.add(new Point(1,0));
		directions.add(new Point(-1,0));
		
		//iterates through each direction and sees if an enemy rook or queen is in the way
		for(Point direction : directions)
		{
			boolean keepGoing = true;
			Piece endPiece=null;
			Point mightHavePiece=new Point(pointToCheck.x+direction.x,pointToCheck.y+direction.y);
		
			while (endPiece==null&&keepGoing)
			{
				if(!myBoard.isOnBoard(mightHavePiece))
				{
					keepGoing=false;//if the point isn't on the board, we shouldn't keep going
				}else
				{
					endPiece=myBoard.getPiece(mightHavePiece);
				}
				mightHavePiece.setLocation(mightHavePiece.x+direction.x, mightHavePiece.y+direction.y);
			}
			//if the endPiece exists, and it is either a bishop or a queen, and it has opposite color, then it can attack this square. Return true
			if(endPiece!=null&&(endPiece instanceof Rook ||endPiece instanceof Queen)&&endPiece.isWhite!=this.isWhitesTurn())
			{
				return true;
			}
			
			
		}
		return false;//nothing diagonal could attack, so return false
	}
	/* @Pre: None
	 * @Post: no changes
	 * @Return: whether or not this point is under attack by an enemy knight
	 */
	private boolean knightsCanAttack(Point pointToCheck) {
		// TODO Auto-generated method stub
		ArrayList<Point> possibleKnightAttacks= new ArrayList<Point>();
		
		//adds all L shaped shifts from the pointToCheck to the list
		for(int dx=-1;dx<=1;dx+=2)
		{
			for(int dy = -2; dy<=2;dy+=4)
			{
				possibleKnightAttacks.add(new Point(pointToCheck.x+dx,pointToCheck.y+dy));
				possibleKnightAttacks.add(new Point(pointToCheck.x+dy,pointToCheck.y+dx));
			}
			
		}
		for(Point p : possibleKnightAttacks)
		{
			if(myBoard.isOnBoard(p))//only check the point if it is on the board
			{
				Piece thePiece = getPiece(p);
				
				//if the piece isn't null, and it is a Knight, and it has the opposite color, the position is under attack
				if(thePiece!=null&&thePiece instanceof Knight&& thePiece.isWhite!=this.isWhitesTurn()){
					return true;
				}
			}
		}
		return false;
	}

	/* @Pre: None
	 * @Post: no changes
	 * @Return: whether or not this point is under attack by an enemy bishop or queen (by diagonal) 
	 */
	private boolean diagonalCanAttack(Point pointToCheck)
	{
		ArrayList<Point> directions=new ArrayList<Point>();
		//adds the 4 diagonal directions to the list
		for(int dx=-1;dx<=1;dx+=2)
		{
			for(int dy=-1; dy<=1;dy+=2)
			{
				directions.add(new Point(dx,dy));
			}
		}
		
		//iterates through each direction and sees if an enemy bishop or queen is in the way
		for(Point direction : directions)
		{
			boolean keepGoing = true;
			Piece endPiece=null;
			Point mightHavePiece=new Point(pointToCheck.x+direction.x,pointToCheck.y+direction.y);
		
			while (endPiece==null&&keepGoing)
			{
				if(!myBoard.isOnBoard(mightHavePiece))
				{
					keepGoing=false;//if the point isn't on the board, we shouldn't keep going
				}else
				{
					endPiece=myBoard.getPiece(mightHavePiece);
				}
				mightHavePiece.setLocation(mightHavePiece.x+direction.x, mightHavePiece.y+direction.y);
			}
			//if the endPiece exists, and it is either a bishop or a queen, and it has opposite color, then it can attack this piece. Return true
			if(endPiece!=null&&(endPiece instanceof Bishop ||endPiece instanceof Queen)&&endPiece.isWhite!=this.isWhitesTurn())
			{
				return true;
			}
			
			
		}
		return false;//nothing diagonal could attack, so return false
	}
	
	/* @Pre: None
	 * @Post: move the selected piece to the selected location if it is a valid move
	 * @Return: whether the piece actually moved
	 */
	public boolean movePiece(Point p)
	{
		if(possibleMoves==null)
		{
			return false;
		}
		if(possibleMoves.contains(p))//then we move the piece
		{
			if(chosenPiece instanceof King&&Math.abs(chosenPiece.getX()-p.x)==2){//then this is a castling move, we need to move the appropriate rook
				moveCastlingRook(p);
				
			}
			else if(chosenPiece instanceof Pawn &&Math.abs(chosenPiece.getX()-p.x)==1)//if it is a pawn that is moving diagonally
			{
				if(getPiece(p)==null)//and the location being moved to is empty then it is an en passant, so we have to remove the other pawn
				{
					if(chosenPiece.isWhite())//remove piece below the white piece
					{
						myBoard.removePiece(getPiece(new Point(p.x,p.y-1)));
					}
					else
					{
						myBoard.removePiece(getPiece(new Point(p.x,p.y+1)));
					}
				}
			}
			myBoard.movePiece(chosenPiece, p);
			chosenPiece.wasMoved();
			chosenPiece=null;
			possibleMoves=null;
			whitesTurn=!whitesTurn;
			return true;
		}else//if the point isn't a possible move, then deselect the piece and remove the possible moves
		{
			chosenPiece = null;
			possibleMoves=null;
			return false;
		}
			
	}
	/* @Pre: Called by Game.movePiece(Point) when a castling move is made
	 * @Post: The appropriate rook is moved to the point that the king jumped over
	 * @Return:
	 */
	private void moveCastlingRook(Point p) {
		if(chosenPiece.getX()-p.x>0)//left castle, move rook two right
		{
			Piece theRookToMove =  getPiece(new Point(0,chosenPiece.getY()));
			if(! (theRookToMove instanceof Rook))
			{
				throw new AssertionError("the piece being moved by the castling isn't a rook");
			}
			
			myBoard.movePiece(theRookToMove, new Point(2,p.y));
			theRookToMove.wasMoved();
		}else//right castle, mvoe rook 3 left
		{
			Piece theRookToMove=getPiece(new Point(7,chosenPiece.getY()));
			if(!(theRookToMove instanceof Rook))
			{
				throw new AssertionError("the piece being moved by the castling isn't a rook");
			}
			myBoard.movePiece(theRookToMove,new Point(4,chosenPiece.getY()));
			theRookToMove.wasMoved();
		}
		
	}
	public Piece getPiece(Point p)
	{
		return myBoard.getPiece(p);
	}


	public void upgradePawn(Piece thePiece) {
		Object[] possibilities = {"Queen", "Knight", "Rook", "Bishop"};
		Object answer =null;
		while(answer ==null)
		{
			answer = JOptionPane.showInputDialog(null, "Your pawn can now be upgraded. What would you like it to be?", "Upgrade", JOptionPane.PLAIN_MESSAGE, null, possibilities, "Queen");
		}
		
		Piece newPiece;
		if(answer==possibilities[0])
		{
			newPiece=new Queen(thePiece.isWhite, new Point(thePiece.getX(),thePiece.getY()), this.getBoard());
		}else if(answer == possibilities[1])
		{
			newPiece = new Knight(thePiece.isWhite, new Point(thePiece.getX(),thePiece.getY()), this.getBoard());
		}else if (answer == possibilities[2])
		{
			newPiece = new Rook(thePiece.isWhite, new Point(thePiece.getX(),thePiece.getY()), this.getBoard());
		}else
		{
			newPiece=new Bishop(thePiece.isWhite, new Point(thePiece.getX(),thePiece.getY()), this.getBoard());
		}
		myBoard.upgradePawn(thePiece.myLocation, newPiece);
		
	}
	
	public  String toString()
	{
		return myBoard.toString();
	}
	
	public Board getBoard()
	{
		return myBoard;
	}
	
	private boolean isWhitesTurn() {
		// TODO Auto-generated method stub
		return whitesTurn;
	}
	
	private ArrayList<Piece> getEnemyPieces() {
		// TODO Auto-generated method stub
		return myBoard.getPieces(!this.isWhitesTurn());
	}
	private ArrayList<Piece> getMyPieces() {
		// TODO Auto-generated method stub
		return myBoard.getPieces(this.isWhitesTurn());
	}

	
	private static Point getDistance(Point point, Point point2) {
		// TODO Auto-generated method stub
		return new Point(point.x-point2.x,point.y-point2.y);
	}

	/* @Pre: point and point2 lie on the same line with a slope of either -1, 1, 0 or infinity. point and point2 are not the same point
	 * @Post: None
	 * @Return: An ArrayList of all points which lie between point and point2
	 */
	public static ArrayList<Point> getPointsBetween(Point point, Point point2) {
		// TODO Auto-generated method stub
		ArrayList<Point> answer= new ArrayList<Point>();
		Point difference = Game.getDistance(point, point2);
		if(difference.x==0&&difference.y==0)
		{
			throw new AssertionError("There are no points between the same point");
		}
		if(Math.abs(difference.x)!=Math.abs(difference.y))//abs(slope)!=1
		{
			if(difference.x!=0&&difference.y!=0)//isn't horizontal or vertical
			{
				throw new AssertionError("These points dont lie on a line that has a slope of 0, -1, 1 or inf");
				
			}
		}
		
		if(difference.x!=0)
		{
			difference.x/=Math.abs(difference.x);//get to magnitude of one
		}
		if(difference.y!=0)
		{
			difference.y/=Math.abs(difference.y);//get to magnitude of 1
		}
		int dx = difference.x;
		int dy=difference.y;
		Point pointToAdd = new Point(point.x-dx, point.y-dy);
		while(!pointToAdd.equals(point2))
		{
			answer.add(pointToAdd);
			pointToAdd= new Point(pointToAdd.x-dx,pointToAdd.y-dy);
		}
		return answer;
		
	}

}

class Move
{
	private Point previousLocation;
	private Point currentLocation;
	public Move(Point previousLocation, Point thePoint) {
		// TODO Auto-generated constructor stub
		this.previousLocation=previousLocation;
		this.currentLocation=thePoint;
	}
	
	public Point getPreviousLocation()
	{
		return previousLocation;
	}
	
	public Point getCurrentLocation()
	{
		return currentLocation;
	}
	
	
}
