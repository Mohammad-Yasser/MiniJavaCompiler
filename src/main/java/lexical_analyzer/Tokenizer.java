package lexical_analyzer;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.regex.Matcher;

import lexical_analyzer.Token;

public class Tokenizer {
  private static void addToken(String type, String regex) {
    Token.getTokens().put(type, new Token(type, regex));
  }

  public static ArrayList<Lexeme> tokenize(String text) {
    if (Token.getTokens().isEmpty()) {
      initialize();
    }

    ArrayList<Lexeme> lexemes = new ArrayList<Lexeme>();

    int current_pos = 0;
    String unmatched_block = "";

    while (current_pos != text.length()) {
      boolean matched = false;
      for (Entry<String, Token> entry : Token.getTokens().entrySet()) {
        Token token = entry.getValue();
        Matcher matcher = token.getPattern().matcher(text);
        matcher.region(current_pos, text.length());
        // Checking "!matcher.group().isEmpty()" just in case a token
        // was added whose regex matches empty strings.
        if (matcher.lookingAt() && !matcher.group().isEmpty()) {
          if (!unmatched_block.isEmpty()) {
            lexemes.add(new Lexeme("UNMATCHED_BLOCK", unmatched_block));
            unmatched_block = "";
          }
          lexemes.add(new Lexeme(token.getType(), matcher.group()));
          current_pos = matcher.end();
          matched = true;
          break;
        }
      }
      if (!matched) {
        if (text.charAt(current_pos) == ' ') {
          if (!unmatched_block.isEmpty()) {
            lexemes.add(new Lexeme("UNMATCHED_BLOCK", unmatched_block));
            unmatched_block = "";
          }
        } else {
          unmatched_block += text.charAt(current_pos);
        }
        ++current_pos;
      }
    }

    return lexemes;
  }

  private static void initialize() {
    // Assure that tokens are added in the correct precedence order. E.g. if
    // "VARIABLE_NAME" preceded "INT", then all "INT"s would be matched to
    // "VARIABLE_NAME".

    addToken("COMMENT1", "\\/\\/.*");
    addToken("COMMENT2", "\\/\\*((?!\\*\\/)[\\w\\W])*\\*\\/");

    // In case of unmatched comment key characters.
    addToken("COMMENT_L", "\\/\\*");
    addToken("COMMENT_R", "\\*\\/");

    addToken("EOL", "\\n");

    // Arithmetic operators.
    addToken("PLUS", "\\+");
    addToken("MINUS", "-");
    addToken("MULTIPLY", "\\*");
    addToken("DIVIDE", "\\/");

    addToken("EQUAL", "=");

    addToken("LESS_THAN", "<");
    addToken("GREATER_THAN", "<");

    addToken("IF", "\\bif\\b");
    addToken("ELSE", "\\belse\\b");
    addToken("WHILE", "\\bwhile\\b");

    addToken("RETURN", "\\breturn\\b");

    addToken("THIS", "\\bthis\\b");

    addToken("CLASS", "\\bclass\\b");

    addToken("PUBLIC", "\\bpublic\\b");
    addToken("PRIVATE", "\\bprivate\\b");

    addToken("EXTENDS", "\\bextends\\b");

    addToken("SYSTEM.OUT.PRINTLN", "\\bSystem\\.out\\.println\\b");

    addToken("STATIC", "\\bstatic\\b");

    addToken("NEW", "\\bnew\\b");

    addToken("FLOAT", "\\bfloat\\b");
    addToken("INT", "\\bint\\b");
    addToken("CHARACTER", "\\bchar\\b");
    addToken("BOOLEAN", "\\bboolean\\b");
    addToken("String", "\\bString\\b");
    addToken("VOID", "\\bvoid\\b");

    addToken("TRUE", "\\btrue\\b");
    addToken("FALSE", "\\bfalse\\b");

    // Brackets.
    addToken("LEFT_CURLY_BRACKET", "\\{");
    addToken("RIGHT_CURLY_BRACKET", "\\}");
    addToken("LEFT_SQUARE_BRACKET", "\\[");
    addToken("RIGHT_SQUARE_BRACKET", "\\]");
    addToken("LEFT_ROUND_BRACKET", "\\(");
    addToken("RIGHT_ROUND_BRACKET", "\\)");

    addToken("COMMA", ",");
    addToken("SEMICOLON", ";");
    addToken("DOT", "\\.");
    addToken("NOT", "\\!");

    addToken("AND", "&&");

    // Literals.
    addToken("FLOAT_LITERAL", "\\b[0-9]*\\.[0-9]*\\b");
    addToken("INTEGRAL_LITERAL", "\\b[0-9]+\\b");
    addToken("CHARACTER_LITERAL", "'.'");
    addToken("STRING_LITERAL", "\".*\"");

    addToken("VARIABLE_NAME", "\\b[a-z]+\\w*\\b");

    addToken("SINGLE_QUOTE", "'");
    addToken("DOUBLE_QUOTE", "\"");
  }
}
