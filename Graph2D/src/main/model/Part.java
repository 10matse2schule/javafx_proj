package main.model;

public class Part {
	
	protected double coefficient;
	protected double exponent;
	
	public Part(double coefficient, double exponent) {
		this.coefficient = coefficient;
		this.exponent = exponent;
	}
	
	public double calculatePart(double x) {
//		if(exponent % 2 == 0) {
//			return coefficient*(Math.pow(x,exponent));
//		} else {
//			if(exponent % 2 == 1) {
//				if(x < 0) {
//					x *= -1;
//					return coefficient*(-1*(Math.pow(x,exponent)));
//				} else {
//					return coefficient*(Math.pow(x,exponent));
//				}
//			}
//			else {
//				if(x < 0) {
//					x *= -1;
//					return coefficient*(Math.pow(x,exponent));
//				} else {
//					return coefficient*(Math.pow(x,exponent));
//				}
//			}
//		}
		return coefficient*(Math.pow(x,exponent));
	}

	public double getCoefficent() {
		return coefficient;
	}

	public void setCoefficent(double coefficent) {
		this.coefficient = coefficent;
	}

	public double getExponent() {
		return exponent;
	}

	public void setExponent(double exponent) {
		this.exponent = exponent;
	}
	
	
	
	
}
