package com.DanAnderson.Chess;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class BoardCanvas extends Canvas implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Game myGame;
	private int myCellSize;
	private Graphics canvasBackground;
	private BufferedImage backgroundImage;
	private int myWidth;
	private int myHeight;
	BufferedImage offscreen;
	Graphics bufferGraphics;
	
	private HashMap<String, BufferedImage> stringToImage;
	
	
	/* Pre: None
	 * Post: No changes
	 * Return: The string representation of the board
	 */
	private String getBoard() {
		// TODO Auto-generated method stub
		return myGame.getBoard().toString();
	}

	/* @Pre: None
	 * @Post: None
	 * @Return: A boardcanvas
	 */
	public BoardCanvas(int sizeOfCell)
	{
		
		stringToImage = new HashMap<String,BufferedImage>();
		myCellSize=sizeOfCell;
		myGame=createGame();
		this.addMouseListener(this);

		setupImages();

		setupCanvas();
		offscreen=new BufferedImage(myWidth,myHeight, 4);
		bufferGraphics=offscreen.getGraphics();

	}

	private Game createGame() {
		// TODO Auto-generated method stub
		int whiteControlled=promptForInput("white");
		int blackControlled=promptForInput("black");
		return new Game(whiteControlled,blackControlled);
		
	}

	private int promptForInput(String string) {
		// TODO Auto-generated method stub
		Object[] possibilities = {"Human Controlled", "AI-random"};
		Object answer =null;
		while(answer ==null)
		{
			answer = JOptionPane.showInputDialog(null, "What would you like the "+string+" player to be controlled by?", null, JOptionPane.PLAIN_MESSAGE, null, possibilities, possibilities[0]);
		}
		
		if(answer ==possibilities[0])
		{
			return 0;
		}else
		{
			return 1;
		}

	}

	//sets up the images for the pieces
	private void setupImages() {
		String[] imageNames = {"bishop_black.png","bishop_white.png","king_black.png","king_white.png","knight_white.png","knight_black.png","pawn_black.png","pawn_white.png","queen_black.png","queen_white.png","rook_white.png","rook_black.png"};
		
		for(String name:imageNames)
		{
			BufferedImage b;
			try {
				File theFile = new File("Pieces/"+name);
				b = ImageIO.read(theFile);
				stringToImage.put(name, b);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

		
	
	//sets up the canvas
	private void setupCanvas() {
		// TODO Auto-generated method stub
		myWidth=myCellSize*8;
		myHeight=myCellSize*8;
		this.setSize(myWidth,myHeight);
		
		this.setBackground(Color.white);
	}

	//paints the background, then the pieces, then the possible moves (if any) and the piece selected
	public void paint(Graphics g)
	{
		bufferGraphics.clearRect(0, 0, myWidth, myHeight);
		paintBackgroundAndPieces(bufferGraphics);
		
		paintSelectedPieceAndMoves(bufferGraphics);
		
		g.drawImage(offscreen, 0,0,this);
	}

	public void update(Graphics g)
	{
		paint(g);
	}
	
	//to be called by the paint method to paint indicators for the selected piece and the moves it can make
	//TODO, show selection of possible moves
	private void paintSelectedPieceAndMoves(Graphics g) {
		Color tempColor = g.getColor();

		if(myGame.chosenPiece!=null)
		{
			int x = myGame.chosenPiece.getX();
			int y =7- myGame.chosenPiece.getY();
			g.setColor(Color.red);
			g.drawRect(x*myCellSize,y*myCellSize,myCellSize,myCellSize);
			
		}
		if(myGame.possibleMoves!=null)
		{
			for(Point p : myGame.possibleMoves)
			{
				g.drawRect((p.x)*myCellSize, (7-p.y)*myCellSize, myCellSize, myCellSize);
			}

		}g.setColor(tempColor);
		
	}

	//to be called by the paint method to paint the background and then all of the pieces 
	private void paintBackgroundAndPieces(Graphics g) {
		for(int i = 0 ;i < 64;i++)
		{
			int x = i%8;
			int y = i/8;
			
			if((x+y)%2==0)
			{g.fillRect(myCellSize*(x), myCellSize*(y), myCellSize, myCellSize);}
			
			Piece pieceAtPosition = myGame.getPiece(new Point(x,7-y));
			if(pieceAtPosition!=null)
			{
				g.drawImage(stringToImage.get(pieceAtPosition.getImageName()), myCellSize*x, myCellSize*y, this);
			}
		}
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int x = arg0.getX()/myCellSize;
		int y = arg0.getY()/myCellSize;
		Point thePoint = new Point(x,7-y);
		myGame.parseInput(thePoint);
		repaint();
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public String toString()
	{
		return myGame.toString();
	}

	public Game getGame() {
		// TODO Auto-generated method stub
		return myGame;
	}
}
