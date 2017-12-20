package main.model;

public class SinPart extends Part {

	public SinPart(double coefficient, double exponent) {
		super(coefficient, exponent);
	}
	
	@Override
	public double calculatePart(double x) {
		x = coefficient*(Math.pow(x,exponent));
		return Math.sin(x);
	}

}
