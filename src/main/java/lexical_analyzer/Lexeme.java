package lexical_analyzer;

public class Lexeme {

  public Lexeme(String token_type, String content) {
    this.tokenType = token_type;
    this.content = content;
  }

  public String toString() {
    return "<" + tokenType + ">: -" + content + "-\n";
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String token_type) {
    this.tokenType = token_type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  private String tokenType;
  private String content;
}
