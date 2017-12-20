package util;

import java.util.ArrayList;
import main.model.DoublePoint;
import main.model.Equation;
import main.model.Part;

public class EquationUtil {
	
	public static ArrayList<DoublePoint> generatePoints(Equation equation,double acc,double minRange,double maxRange) {
		//TODO Add range and try to scale the diagramm
		ArrayList<DoublePoint> list = new ArrayList<>();
		double actualX = minRange;
		while(actualX <= maxRange + acc) {
			double actualY = calculateEquation(equation,actualX);
			Double test = new Double(actualY);
			if(test.isInfinite() || test.isNaN()) {
				actualX += acc;
				continue;
			}
			list.add(new DoublePoint(actualX,actualY));
			actualX += acc;
		}
		
		return list;
	}
	
	public static double calculateEquation(Equation eq,double x) {
		double result = 0;
		//TODO FIX STUCK AT getting equation parts
		for(Part p : eq.getEquationParts()) {
			result += p.calculatePart(x);
		}
		return result;
	}
}
