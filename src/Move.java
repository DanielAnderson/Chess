package com.DanAnderson.Chess;

import java.awt.Point;
import java.util.ArrayList;

public class Move {
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
	
	public String toString()
	{
		return new String(previousLocation.toString()+" to "+currentLocation.toString());
		
	}

}
