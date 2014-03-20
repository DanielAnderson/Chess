package com.DanAnderson.Chess;
//TODO change the isInCheck to call Game.isUnderAttack(king.location)
//TODO create and change to implement a method that gets all pieces that can possibly attack a certain square
//TODO make it so that piece.possibleMoves includes en passant and castling
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ChessFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BoardCanvas theBoardCanvas;
	private AI whiteAI;
	private AI blackAI;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChessFrame theFrame=new ChessFrame("Chess- Dan Anderson");
		Game theGame =theFrame.getGame();

		int lastTurnUpdated = theGame.getTurnNumber();
		while(!theGame.isGameOver())
		{
			if(theGame.getTurnNumber()!=lastTurnUpdated)//a turn has elapsed
			{
				theFrame.repaint();
			}
			if(theGame.isWhitesTurn()&&theGame.getWhiteController()!=Game.HUMAN_CONTROLLED)
			{
				
				try{
					theFrame.whiteAI.makeMove();
				}catch (java.lang.IllegalArgumentException ex)
				{
					theGame.displayTieOrWinMessage();
				}
				
			}
			else if(!theGame.isWhitesTurn()&&theGame.getBlackController()!=Game.HUMAN_CONTROLLED)
			{
				try{
					theFrame.blackAI.makeMove();
				}catch (java.lang.IllegalArgumentException ex)
				{
					theGame.displayTieOrWinMessage();
				}
				
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Game getGame() {
		return theBoardCanvas.getGame();
		
	}
	public ChessFrame(String title)
	{
		super(title);
		int whiteType = promptForInput("white");
		int blackType = promptForInput("black");
		theBoardCanvas= createNewGame(this, whiteType, blackType);
		
		whiteAI=AI.createAI(getGame(),whiteType, Game.WHITE);
		
		blackAI=AI.createAI(getGame(),blackType, Game.BLACK);
		
		this.add(theBoardCanvas);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private int promptForInput(String string) {
		// TODO Auto-generated method stub
		Object[] possibilities = {"Human Controlled", "AI-random", "AI-random aggressive"};
		Object answer =null;
		while(answer ==null)
		{
			answer = JOptionPane.showInputDialog(null, "What would you like the "+string+" player to be controlled by?", null, JOptionPane.PLAIN_MESSAGE, null, possibilities, possibilities[0]);
		}
		
		for(int i = 0 ; i < possibilities.length;i++)
		{
			if(answer.equals(possibilities[i]))
			{
				return i;
			}
		}
		throw new AssertionError("We need an answer here!");
	}

	public BoardCanvas createNewGame(ChessFrame theFrame, int whiteType, int blackType)
	{
		return new BoardCanvas(50, whiteType, blackType, theFrame);
	}
	public void repaint()
	{
		super.repaint();
		this.theBoardCanvas.repaint();
	}

	public AI getAI(boolean whitesTurn) {
		// TODO Auto-generated method stub
		if(whitesTurn)
		{
			return whiteAI;
		}else
		{
			return blackAI;
		}
	}

}
