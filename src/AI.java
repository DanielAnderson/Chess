package com.DanAnderson.Chess;

public abstract class AI {
	protected boolean myColor;
	protected Game myGame;
	public final int RANDOM =1;

	public AI(Game theGame, boolean color)
	{
		myGame=theGame;
		myColor=color;
	}
	
	public abstract void makeMove();
}
