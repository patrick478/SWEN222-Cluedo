package cluedo;

public class PathfindFrame {
	int numSteps = 0;
	
	int curPosX;
	int curPosY;
	
	public PathfindFrame(int n, int x, int y)
	{
		this.numSteps = n;
		this.curPosX = x;
		this.curPosY = y;
	}
}
