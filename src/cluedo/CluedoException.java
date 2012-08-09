package cluedo;

@SuppressWarnings("serial")
public class CluedoException extends Exception {
	private String message = "";
	public CluedoException(String msg)
	{
		this.message = msg;
	}
	
	@Override
	public String toString()
	{
		return this.message;
	}
}
