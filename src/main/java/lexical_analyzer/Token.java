package lexical_analyzer;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Token {
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

  public static HashMap<String, Token> getTokens() {
    return tokens;
  }

  public static void setTokens(HashMap<String, Token> tokens) {
    Token.tokens = tokens;
  }

  // Maps labels to corresponding tokens.
  private static HashMap<String, Token> tokens = new HashMap<String, Token>();

  private String type;
  private Pattern pattern;
}
