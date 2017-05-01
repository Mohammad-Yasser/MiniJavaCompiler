package syntax_analyzer;

import java.util.ArrayList;
import java.util.HashMap;

import lexical_analyzer.Token;

public class Symbol {

  public static Symbol getSymbol(String label) {
    // TODO:
    if (!symbols.containsKey(label)) {
      return new Symbol(label);
    }

    return symbols.get(label);
  }

  public String toString() {
    return label;
  }

  // Creates a symbol with the given label, and binds it to a token that has the same label, if
  // exists.
  private Symbol(String label) {
    this(label, Token.getToken(label));
  }

  // Creates a symbol with the given label, binded to the given token.
  private Symbol(String label, Token token) {
    this.label = label;
    this.token = token;

    assert (!symbols.containsKey(label)) : "The label " + label + " was previously declared!";

    symbols.put(label, this);
  }

  public static HashMap<String, Symbol> getSymbols() {
    return symbols;
  }

  public static void setSymbols(HashMap<String, Symbol> symbols) {
    Symbol.symbols = symbols;
  }


  public static Symbol getStartSymbol() {
    return startSymbol;
  }

  public static void setStartSymbol(Symbol startSymbol) {
    Symbol.startSymbol = startSymbol;
  }

  public ArrayList<Production> getProductions() {
    return productions;
  }

  public void setProductions(ArrayList<Production> productions) {
    this.productions = productions;
  }

  public String getLabel() {
    return label;
  }

  public Token getToken() {
    return token;
  }

  // Maps labels to their corresponding "Symbol" objects.
  private static HashMap<String, Symbol> symbols = new HashMap<String, Symbol>();
  private static Symbol startSymbol;



  private final String label;
  // The corresponding token for terminal symbols. It's set to null for non-terminal symbols.
  private final Token token;
  private ArrayList<Production> productions = new ArrayList<>();
}
