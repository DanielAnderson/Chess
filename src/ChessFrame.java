package com.DanAnderson.Chess;
//TODO change the isInCheck to call Game.isUnderAttack(king.location)
//TODO create and change to implement a method that gets all pieces that can possibly attack a certain square
//TODO make it so that piece.possibleMoves includes en passant and castling
import javax.swing.JFrame;

public class ChessFrame extends JFrame {

	private BoardCanvas theBoardCanvas;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChessFrame theFrame=new ChessFrame("Chess- Dan Anderson");

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

}
