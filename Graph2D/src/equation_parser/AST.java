package equation_parser;
import java.text.NumberFormat;
import java.util.Locale;

class AST {
// package-private Implementiation
	enum Type {
		LITERAL		,
		VARIABLE	,
		
		OP_POS		,
		OP_NEG		,
		OP_SQRT		,
		OP_SIN		,
		OP_COS		,
		
		OP_ADD		,
		OP_SUB		,
		OP_MUL		,
		OP_DIV		,
		OP_POW		,
	
	};
	
	AST		lhs;
	AST		rhs;
	
	Type	type;
	
	double		value; // for LITERAL
	Variable	var; // for VARIABLE
	
	AST (Type t) {
		type = t;
	}
	AST (Type t, double val) {
		type = t;
		value = val;
	}
	AST (Variable v) {
		type = Type.VARIABLE;
		var = v;
	}
	
	double calculate () {
		switch (type) {
			case LITERAL:	return value; // literal has value only calculated once on parse
			case VARIABLE:	return var.get_value();
			
			case OP_POS:	return +rhs.calculate();
			case OP_NEG:	return -rhs.calculate();
			
			case OP_SQRT:	return Math.sqrt(rhs.calculate());
			case OP_SIN:	return Math.sin(rhs.calculate());
			case OP_COS:	return Math.cos(rhs.calculate());
			
			case OP_ADD:	return lhs.calculate() + rhs.calculate();
			case OP_SUB:	return lhs.calculate() - rhs.calculate();
			case OP_MUL:	return lhs.calculate() * rhs.calculate();
			case OP_DIV:	return lhs.calculate() / rhs.calculate();
			case OP_POW:	return Math.pow(lhs.calculate(), rhs.calculate());
			
			default: assert(false); return 0;
		}
	}
	
	String as_nice_string () {
		switch (type) {
			case LITERAL:{
				NumberFormat f = NumberFormat.getInstance(Locale.ENGLISH);
				return f.format(value);
			}
			case VARIABLE:	return var.get_identifier();
			
			case OP_POS:	return rhs.as_nice_string(); // do not print plus sign
			case OP_NEG:	return "-" +rhs.as_nice_string();
			
			case OP_SQRT:	return "sqrt(" +rhs.as_nice_string() +")";
			case OP_SIN:	return "sin(" +rhs.as_nice_string() +")";
			case OP_COS:	return "cos(" +rhs.as_nice_string() +")";
			
			case OP_ADD:	return lhs.as_nice_string() +" +"+	rhs.as_nice_string();
			case OP_SUB:	return lhs.as_nice_string() +" -"+	rhs.as_nice_string();
			case OP_MUL:	return lhs.as_nice_string() +"*"+	rhs.as_nice_string(); // multiply without dot
			case OP_DIV:	return lhs.as_nice_string() +"/"+	rhs.as_nice_string();
			case OP_POW:	return lhs.as_nice_string() +"^"+	rhs.as_nice_string();
			
			default: assert(false); return null;
		}
	}
	
}
