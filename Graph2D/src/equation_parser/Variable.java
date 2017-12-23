package equation_parser;
import java.util.ArrayList;

public class Variable {
	
	public void set_value (double val) {
		value = val;
	}
	public double get_value () {
		return value;
	}
	
	public String get_identifier () {
		return name;
	}
	
// package-private Implementiation
	String			name;
	private double	value;
	
	Variable (String n) {
		name = n;
	}
}
