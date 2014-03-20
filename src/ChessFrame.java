package com.DanAnderson.Chess;
import java.awt.Component;
import java.awt.Point;







//TODO change the isInCheck to call Game.isUnderAttack(king.location)
//TODO create and change to implement a method that gets all pieces that can possibly attack a certain square
//TODO make it so that piece.possibleMoves includes en passant and castling
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

public class ChessFrame extends JFrame {

	private BoardCanvas theBoardCanvas;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChessFrame theFrame=new ChessFrame("Chess- Dan Anderson");
		Game theGame =theFrame.getGame();

		long lastUpdated = System.nanoTime();
		int lastTurnUpdated = theGame.getTurnNumber();
		while(!theGame.isGameOver())
		{
			System.out.println(theGame.getTurnNumber());
			if(theGame.getTurnNumber()!=lastTurnUpdated)//a turn has elapsed
			{
				theFrame.repaint();
			}
			if(theGame.isWhitesTurn()&&theGame.getWhiteController()!=Game.HUMAN_CONTROLLED)
			{
				RandomAI r = new RandomAI(theGame,Game.WHITE);
				try{
					r.makeMove();
				}catch (java.lang.IllegalArgumentException ex)
				{
					theGame.displayTieMessage();
				}
				
			}
			else if(!theGame.isWhitesTurn()&&theGame.getBlackController()!=Game.HUMAN_CONTROLLED)
			{
				RandomAI r = new RandomAI(theGame,Game.BLACK);
				r.makeMove();
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private static void displayTieMessage() {
		// TODO Auto-generated method stub

	}
	private Game getGame() {
		return theBoardCanvas.getGame();
		
	}
	public ChessFrame(String title)
	{
		super(title);
		theBoardCanvas= createNewGame();
		this.add(theBoardCanvas);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public BoardCanvas createNewGame()
	{
		return new BoardCanvas(50);
	}
	public void repaint()
	{
		super.repaint();
		this.theBoardCanvas.repaint();
	}

}
