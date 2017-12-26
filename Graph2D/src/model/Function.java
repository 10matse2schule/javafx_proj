package model;

import java.awt.Color;

public class Function {
	
	private Color color;
	private String function;
	
	public Function(String function, Color color) {
		this.color = color;
		this.function = function;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
	
	
	
}
