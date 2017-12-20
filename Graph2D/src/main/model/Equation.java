package main.model;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Equation {

	private ArrayList<Part> equationParts;
	
	public Equation(ArrayList<Part> equationParts) {
		this.equationParts = equationParts;
	}
	
	@Override
	public String toString() {
		String eq = "";
		double coef;
		double exp;
		DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(false);
        
		for(int i = 0;i < equationParts.size();i++) {
			Part p = equationParts.get(i);
			coef = p.getCoefficent();
			exp = p.getExponent();
			if(exp == 0) {
				eq += format.format(coef);
			} else {
				if(coef == 1) {
					eq  += "x";
				}
				else if(coef == -1) {
					eq += "-x";
				}
				else {
					eq += format.format(coef)+"x";
				}
				if(exp != 1) {
					eq += "^" + format.format(exp);
				}
			}
			if(i < (equationParts.size() - 1)) {
				eq += " + ";
			}
		}
		return eq;
		
	}
	
	public ArrayList<Part> getEquationParts() {
		return equationParts;
	}

	public void setEquationParts(ArrayList<Part> equationParts) {
		this.equationParts = equationParts;
	}
	

}
