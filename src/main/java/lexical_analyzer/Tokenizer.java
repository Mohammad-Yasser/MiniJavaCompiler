package lexical_analyzer;

import java.util.ArrayList;
import java.util.regex.Matcher;

import lexical_analyzer.Token;

public class Tokenizer {
	private static ArrayList<Token> tokens = new ArrayList<Token>();

	private static void addToken(String type, String regex) {
		tokens.add(new Token(type, regex));
	}

	public static void tokenize(String text) {
		if (tokens.isEmpty()) {
			initialize();
		}

		int current_pos = 0;

		while (current_pos != text.length()) {
			boolean matched = false;
			for (Token token : tokens) {
				Matcher matcher = token.getPattern().matcher(
						text.substring(current_pos));
				// Checking that "matcher.end() != 0" just in case a token was
				// added whose regex matches empty strings.
				if (matcher.lookingAt() && matcher.end() != 0) {
					System.out.println("<" + token.getType() + ">: "
							+ matcher.group());
					current_pos += matcher.end();
					matched = true;
					break;
				}
			}
			if (!matched) {
				++current_pos;
			}
		}

	}

	private static void initialize() {
		// Assure that tokens are added in the correct precedence order. E.g. if
		// "VARIABLE_NAME" preceded "IDENTIFIER", then all the identifiers would
		// be matched to "VARIABLE_NAME".

		addToken("COMMENT1", "\\/\\/.*");
		addToken("COMMENT2", "\\/\\*((?!\\*\\/)[\\w\\W])*\\*\\/");

		// In case of unmatched comment key characters.
		addToken("COMMENT_L", "\\/\\*");
		addToken("COMMENT_R", "\\*\\/");

		addToken("EOL", "\\n");

		// Arithmetic operators.
		addToken("EQUAL", "=");
		addToken("PLUS", "\\+");
		addToken("MINUS", "-");
		addToken("MULTIPLY", "\\*");
		addToken("DIVIDE", "\\/");

		addToken("LESS_THAN", "<");
		addToken("GREATER_THAN", "<");

		addToken("IF", "\bif\b");
		addToken("ELSE", "\belse\b");
		addToken("WHILE", "\bwhile\b");

		addToken("RETURN", "\breturn\b");

		addToken("THIS", "\bthis\b");

		addToken("CLASS", "\bclass\b");

		addToken("PUBLIC", "\bpublic\b");
		addToken("PRIVATE", "\bprivate\b");

		addToken("EXTENDS", "\bextends\b");

		addToken("SYSTEM.OUT.PRINTLN", "\bSystem\\.out\\.println\b");

		addToken("STATIC", "\bstatic\b");

		addToken("NEW", "\bnew\b");

		addToken("FLOAT", "\bfloat\b");
		addToken("INT", "\bint\b");
		addToken("CHARACTER", "\bchar\b");
		addToken("BOOLEAN", "\bboolean\b");
		addToken("String", "\bString\b");
		addToken("VOID", "\bvoid\b");

		
		addToken("TRUE", "\btrue\b");
		addToken("FALSE", "\bfalse\b");

		// Brackets.
		addToken("LEFT_CURLY_BRACKET", "\\{");
		addToken("RIGHT_CURLY_BRACKET", "\\}");
		addToken("LEFT_SQUARE_BRACKET", "\\[");
		addToken("RIGHT_SQUARE_BRACKET", "\\]");
		addToken("LEFT_ROUND_BRACKET", "\\(");
		addToken("RIGHT_ROUND_BRACKET", "\\)");

		addToken("COMMA", ",");
		addToken("SEMICOLON", ";");
		addToken("DOT", "/.");
		addToken("NOT", "/!");

		addToken("AND", "&&");

		// Literals.
		addToken("FLOAT_LITERAL", "[0-9]*\\.[0-9]*");
		addToken("INTEGRAL_LITERAL", "[0-9]+");
		addToken("CHARACTER_LITERAL", "'.'");
		addToken("STRING_LITERAL", "\".*\"");

		addToken("VARIABLE_NAME", "[a-z]+");

		addToken("SINGLE_QUOTE", "'");
		addToken("DOUBLE_QUOTE", "\"");
	}
}
