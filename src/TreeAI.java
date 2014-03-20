package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class TreeAI extends AI {

	int myDepth;
	public TreeAI(Game theGame, boolean color, int depth) {
		super(theGame, color);
		myDepth=depth;
	}

	@Override
	public Move chooseMove() {
		// TODO Auto-generated method stub
		GameTree theTree = new GameTree(myGame, myDepth,null);//based off of myGame, with a depth of 3
		ArrayList<GameTree> genOneChildren=theTree.getMyChildren();
		
		Move theBest = findBest(genOneChildren);
		return theBest;
	}


	private Move findBest(ArrayList<GameTree> genOneChildren) {
		// TODO Auto-generated method stub
		ArrayList<GameTree> theBest = new ArrayList<GameTree>();
		theBest.add(genOneChildren.get(0));

		for(int i = 1 ; i < genOneChildren.size();i++)
		{
			
			GameTree theNode = genOneChildren.get(i);
			if(myColor==Game.WHITE)
			{
				if(theNode.myValue>theBest.get(0).myValue){//this move is better than the previous best moves
					theBest = new ArrayList<GameTree>();
					theBest.add(theNode);
				}else if(theNode.myValue==theBest.get(0).myValue){//same value, add it to the list
					theBest.add(theNode);
				}else
				{
					//do nothing, because this move is worse than the current list
				}
			}else//myColor = black
			{
				if(theNode.myValue<theBest.get(0).myValue){//this move is better than the previous best moves
					theBest = new ArrayList<GameTree>();
					theBest.add(theNode);
				}else if(theNode.myValue==theBest.get(0).myValue){//same value, add it to the list
					theBest.add(theNode);
				}else
				{
					//do nothing, because this move is worse than the current list
				}
			}
		}
		return this.getRandomMove(theBest);
	}

	private Move getRandomMove(ArrayList<GameTree> theBest) {
		// TODO Auto-generated method stub
		return theBest.get(new Random().nextInt(theBest.size())).myMove;
	}

	@Override
	public int chooseUpgrade(Point p) {
		// TODO Auto-generated method stub
		return 0;
		
	}


}
