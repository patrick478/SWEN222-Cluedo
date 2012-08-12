package cluedo;

public abstract class GameObject {
	private String name = "missingNo.";
	public String GetName() {
		return this.name;
	}
	
	public void SetName(String s) {
		this.name = s;
	}
}
