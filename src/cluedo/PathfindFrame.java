package cluedo;

import java.util.*;

public class PathfindFrame {
	List<Pair> steps;
	
	int curPosX;
	int curPosY;
	
	public PathfindFrame(List<Pair> s, int x, int y)
	{
		this.curPosX = x;
		this.curPosY = y;
		this.steps = new ArrayList<Pair>(s);
	}
}
