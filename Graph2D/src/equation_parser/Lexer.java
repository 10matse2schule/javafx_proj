package equation_parser;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

public class Lexer {
	
	public Lexer (String str) {
		source_str = str;
		
		reset();
	}
	public void reset () {
		cur_char = new StringCharacterIterator(source_str);
		
		lex_next_token(); // next_tok becomes the first token
	}
	
	public Lexer (Lexer r) {
		source_str = r.source_str;
		reset();
	}
	
// package-private Implementiation
	final String				source_str;
	StringCharacterIterator		cur_char;
	
	Token	next_tok = null;
	
	static boolean is_digit_c (char c) {
		return c >= '0' && c <= '9';
	}
	static boolean is_identifier_c (char c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_';
	}
	
	void lex_next_token () {
		while (cur_char.current() != StringCharacterIterator.DONE) {
			
			switch (cur_char.current()) {
				case ' ':
				case '\t':
					cur_char.next(); // skip whitespace
					break;
				
				case '+':		next_tok = new Token(Token.Type.OP_PLUS, String.valueOf(cur_char.current()));		cur_char.next(); return;
				case '-':		next_tok = new Token(Token.Type.OP_MINUS, String.valueOf(cur_char.current()));		cur_char.next(); return;
				case '*':		next_tok = new Token(Token.Type.OP_MUL, String.valueOf(cur_char.current()));		cur_char.next(); return;
				case '/':		next_tok = new Token(Token.Type.OP_DIV, String.valueOf(cur_char.current()));		cur_char.next(); return;
				case '^':		next_tok = new Token(Token.Type.OP_POW, String.valueOf(cur_char.current()));		cur_char.next(); return;
				
				case '(':		next_tok = new Token(Token.Type.PAREN_OPEN, String.valueOf(cur_char.current()));	cur_char.next(); return;
				case ')':		next_tok = new Token(Token.Type.PAREN_CLOSE, String.valueOf(cur_char.current()));	cur_char.next(); return;
				
				default:
				if (		is_digit_c(cur_char.current()) || cur_char.current() == '.' ) { 
					
					StringCharacterIterator tok_begin = (StringCharacterIterator)cur_char.clone();
					
					while (cur_char.current() != StringCharacterIterator.DONE && is_digit_c(cur_char.current())) cur_char.next();
					
					if (cur_char.current() != StringCharacterIterator.DONE && cur_char.current() == '.') { cur_char.next();
						
						while (cur_char.current() != StringCharacterIterator.DONE && is_digit_c(cur_char.current())) cur_char.next();
					}
					
					next_tok = new Token(Token.Type.LITERAL, source_str.substring(tok_begin.getIndex(), cur_char.getIndex()));
					return;
					
				} else if (	is_identifier_c(cur_char.current()) ) {
					
					StringCharacterIterator tok_begin = (StringCharacterIterator)cur_char.clone();
					
					while (cur_char.current() != StringCharacterIterator.DONE && is_identifier_c(cur_char.current())) cur_char.next();
					
					String identifier = source_str.substring(tok_begin.getIndex(), cur_char.getIndex());
					
					if (		identifier.equals("sqrt") )	next_tok = new Token(Token.Type.OP_SQRT, identifier);
					else if (	identifier.equals("sin") )	next_tok = new Token(Token.Type.OP_SIN, identifier);
					else if (	identifier.equals("cos") )	next_tok = new Token(Token.Type.OP_COS, identifier);
					else									next_tok = new Token(Token.Type.VARIABLE, identifier);
					
					return;
					
				} else {
					System.out.println("lex_next_token:: unknown char '"+ cur_char.current() +"'");
					
					cur_char.next();
				}
				break;
			}
			
		}
		
		next_tok = new Token(Token.Type.EOF, null);
	}
	
}
