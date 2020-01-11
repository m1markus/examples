package ch.m1m.jwt;

public class ObjectGetSet implements java.io.Serializable {
	
	private String name = "toto";
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
