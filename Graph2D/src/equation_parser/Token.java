package equation_parser;

class Token {
// package-private Implementiation
	enum Type {
		EOF			,
		
		LITERAL		,
		VARIABLE	,
		
		PAREN_OPEN	,
		PAREN_CLOSE	,
		
		OP_PLUS		,
		OP_MINUS	,
		OP_MUL		,
		OP_DIV		,
		OP_POW		,
		OP_SQRT		,
		OP_SIN		,
		OP_COS		,
	};
	
	Type	type;
	String	str;
	
	Token (Type t, String s) {
		type = t;
		str = s;
	}
	
}
