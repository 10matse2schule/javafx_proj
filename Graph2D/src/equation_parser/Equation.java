package equation_parser;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

public class Equation {
	
	public Equation(String str) {
		lex = new Lexer(str);
	}
	
	public Variable add_var (String name) {
		Variable v = find_var(name);
		if (v == null) {
			v = new Variable(name);
			variables.add(v);
		}
		return v;
	}
	
	public boolean parse () {
		lex.reset();
		
		/* // debug: show all tokens
		Lexer dbg_lex = new Lexer(lex);
		for (;;) {
			System.out.println("token: "+ dbg_lex.next_tok.type.toString() +" '"+ dbg_lex.next_tok.str +"'");
			if (dbg_lex.next_tok.type == Token.Type.EOF) break;
			dbg_lex.lex_next_token();
		}
		*/
		
		ast = expression(0);
		if (ast == null) return false;
		
		if (lex.next_tok.type != Token.Type.EOF) {
			syntax_error();
			return false;
		}
		return true;
	}
	
	public double calculate () {		return ast.calculate(); }
	public String as_nice_string () {	return ast.as_nice_string(); }
	
	public String get_error_msg () {
		return error_msg;
	}
	
// package-private Implementiation
	Lexer	lex;
	
	ArrayList<Variable> variables = new ArrayList<Variable>();
	
	AST		ast = null;
	
	String	error_msg = null;
	
	Variable find_var (String name) {
		for (Variable v : variables)
			if (v.name.equals(name)) return v;
		return null;
	}
	
	AST syntax_error (String msg) {
		if (error_msg == null) error_msg = msg;
		return null;
	}
	AST syntax_error () {
		return null;
	}
	
	// recursive descent parser
	Token consume () {
		Token tmp = lex.next_tok;
		lex.lex_next_token();
		return tmp;
	}
	Token peek (Token.Type t) {
		if (lex.next_tok.type == t) {
			return lex.next_tok;
		}
		return null;
	}
	Token accept (Token.Type t) {
		if (lex.next_tok.type == t) {
			return consume();
		}
		return null;
	}
	Token expect (Token.Type t, String err_msg) {
		Token tmp = accept(t);
		if (tmp != null) return tmp;
		
		syntax_error(err_msg);
		return null;
	}
	
	AST number () {
		Token tok;
		if (		(tok = accept(Token.Type.LITERAL)) != null ) {
			
			double val = Double.parseDouble(tok.str); // string should always be valid double, since we checked the digits in tokenize
			
			return new AST(AST.Type.LITERAL, val);
			
		} else if (	(tok = accept(Token.Type.VARIABLE)) != null ) {
			
			Variable v = find_var(tok.str);
			if (v == null) return syntax_error("unknown variable '"+ tok.str +"'");
			
			return new AST(v);
			
		} else {
			return null;
		}
	}
	
	AST unary_expression () {
		int precedence = 4;
		
		AST op;
		if ( 		accept(Token.Type.OP_PLUS) != null )	op = new AST(AST.Type.OP_POS);
		else if (	accept(Token.Type.OP_MINUS) != null )	op = new AST(AST.Type.OP_NEG);
		else {
			return null;
		}
		
		if ( (op.rhs = expression(precedence)) == null ) return syntax_error();
		
		return op;
	}
	
	AST binary_expression (AST lhs, int precedence) {
		
		Token op_tok = consume();
		
		AST op;
		switch (op_tok.type) {
			case OP_PLUS:	op = new AST(AST.Type.OP_ADD);	break;
			case OP_MINUS:	op = new AST(AST.Type.OP_SUB);	break;
			case OP_MUL:	op = new AST(AST.Type.OP_MUL);	break;
			case OP_DIV:	op = new AST(AST.Type.OP_DIV);	break;
			case OP_POW:	op = new AST(AST.Type.OP_POW);	break;
			default: assert(false); return null;
		}
		
		op.lhs = lhs;
		if ( (op.rhs = expression(precedence)) == null ) return syntax_error();
		
		return op;
	}
	
	AST parenthesis_expression () {
		
		if (accept(Token.Type.PAREN_OPEN) == null) return null;
		
		AST expr;
		if ( (expr = expression(0)) == null ) return syntax_error();
		
		expect(Token.Type.PAREN_CLOSE, "expected ')' after parenthesis_expression");
		
		return expr;
	}
	
	AST function_expression () {
		
		AST op;
		if (		accept(Token.Type.OP_SQRT) != null )	op = new AST(AST.Type.OP_SQRT);
		else if (	accept(Token.Type.OP_SIN) != null )		op = new AST(AST.Type.OP_SIN);
		else if (	accept(Token.Type.OP_COS) != null )		op = new AST(AST.Type.OP_COS);
		else {
			return null;
		}
		
		expect(Token.Type.PAREN_OPEN, "expected '(' after function name in function_expression");
		
		AST arg0;
		if ( (arg0 = expression(0)) == null ) return syntax_error();
		
		op.rhs = arg0;
		
		expect(Token.Type.PAREN_CLOSE, "expected ')' after function_expression");
		
		return op;
	}
	
	AST expression (int precedence) {
		AST expr;
		if (		(expr = number()) != null ) {}
		else if (	(expr = unary_expression()) != null ) {}
		else if (	(expr = parenthesis_expression()) != null ) {}
		else if (	(expr = function_expression()) != null ) {}
		else {
			return syntax_error("expected  number or unary_expression or parenthesis_expression or function_expression  in expression");
		}
		
		for (;;) {
			
			int		recurse_precedence;
			boolean	associativity_left = true;
			
			if (		peek(Token.Type.OP_PLUS) != null ) {
				recurse_precedence = 1;
			} else if (	peek(Token.Type.OP_MINUS) != null ) {
				recurse_precedence = 1;
			//
			} else if (	peek(Token.Type.OP_MUL) != null ) {
				recurse_precedence = 2;
			} else if (	peek(Token.Type.OP_DIV) != null ) {
				recurse_precedence = 2;
			//
			} else if (	peek(Token.Type.OP_POW) != null ) {
				recurse_precedence = 3;
				associativity_left = false;
			} else {
				break;
			}
			
			if (recurse_precedence < precedence) break;
			
			if (associativity_left) recurse_precedence += 1;
			
			if ( (expr = binary_expression(expr, recurse_precedence)) == null ) syntax_error("expected binary_expression after binary operator");
		}
		
		return expr;
	}
	
}
