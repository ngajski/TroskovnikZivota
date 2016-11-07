package model;

public class Expens {
	
	private String name;
	
	private float value;
	
	private boolean fixed;
	
	public Expens(String name, float value, boolean fixed) {
		super();
		this.name = name;
		this.value = value;
		this.fixed = fixed;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public boolean isFixed() {
		return fixed;
	}
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	
}
