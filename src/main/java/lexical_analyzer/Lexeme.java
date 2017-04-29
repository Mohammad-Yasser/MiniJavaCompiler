package lexical_analyzer;

public class Lexeme {
	private String token_type;
	private String content;
	
	public Lexeme(String token_type, String content) {
		this.token_type = token_type;
		this.content = content;
	}

	public String toString() {
		return "<" + token_type + ">: -" + content + "-\n";
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
