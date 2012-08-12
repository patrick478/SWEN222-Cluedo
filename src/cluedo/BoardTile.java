package cluedo;

public abstract class BoardTile extends GameObject
{
	public char ShortName()
	{
		return ((String)(this.GetName())).charAt(0);
	}
}
