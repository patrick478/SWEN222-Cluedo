package cluedo;

public abstract class BoardTile extends GameObject
{
	public char ShortName()
	{	
		if(this.GetName().equals("Pool"))
				return 'X';
		else
		return ((String)(this.GetName())).charAt(0);
	}
}
