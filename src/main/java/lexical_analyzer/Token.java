package lexical_analyzer;

import java.util.regex.Pattern;

public class Token {
	private String type;
	private Pattern pattern;

	Token(String type, String regex) {
		setType(type);
		setPattern(Pattern.compile(regex));
	}

	public boolean matches(String lexeme) {
		return pattern.matcher(lexeme).matches();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

}
