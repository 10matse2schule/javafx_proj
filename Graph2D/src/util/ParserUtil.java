package util;

import java.util.ArrayList;
import java.util.regex.Pattern;

import main.model.Equation;
import main.model.Part;
import main.model.SinPart;

public class ParserUtil {
	public static Equation parseEquation(String equation) {
		//TODO ADD real Parser
		//Extremely bad Parser please update
		//Only Supports ParentPart could support any operation if detected
		ArrayList<Part> eq = new ArrayList<>();
		try {
			String[] parts = equation.split(Pattern.quote("+"));
			for(String p : parts) {
				//First index should be the coefficient
				//Second index should be the exponent
				float coefficient;
				float exponent;
				boolean containsSin = false;
//				System.out.println(p);
//				if(p.contains("sin")) {
//					p = p.trim().substring(4,p.length()-1);
//					containsSin = true;
//				}
//				System.out.println(p);
				boolean containsX = p.contains("x");
				String[] temp = p.split("x");
				if(temp.length == 0) {
					eq.add(new Part(1,1));
					continue;
				}
				if(temp[0] == null || temp[0].trim().isEmpty()) {
					coefficient = 1;
				}
				else {
					if(temp[0].equals("-")) {
						coefficient = -1;
					} else {
						coefficient = Float.parseFloat(temp[0].trim());
					}
				}
				if(temp.length > 1) {
					if(temp[1] == null || temp[1].trim().isEmpty()) {
						exponent = 1;
					}
					else {
						exponent = Float.parseFloat(temp[1].trim());
					}
				}
				else {
					exponent = containsX ? 1 : 0;
				}
				System.out.println(coefficient + " " + exponent);
				Part part;
				if(containsSin) {
					part = new SinPart(coefficient,exponent);
				} else {
					part = new Part(coefficient,exponent);
				}
				eq.add(part);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new Equation(eq);
	}
}
