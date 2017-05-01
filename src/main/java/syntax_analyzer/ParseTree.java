package syntax_analyzer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lexical_analyzer.Lexeme;
import lexical_analyzer.Token;

public class ParseTree {
  public static class Node {
    // Returns true if it can build the tree. It doesn't have to consume all the content.
    // "lexemes" will contain the unconsumed ones after the function call.
    private boolean buildSubTree(List<Lexeme> lexemes) {

      // System.out.println(symbol + " " + lexemes);

      if (symbol.getToken() != null) {
        if (lexemes.isEmpty())
          return false;
        if (symbol.getToken() != Token.getToken(lexemes.get(0).getTokenType()))
          return false;

        content.add(lexemes.get(0));
        lexemes.remove(0);
        return true;
      }

      boolean has_lambda = false;

      for (Production production : symbol.getProductions()) {
        ArrayList<Symbol> right_side = production.getRightSide();

        // Saving the old size to check if some lexeme was consumed.
        int old_size = lexemes.size();

        for (Symbol child_symbol : right_side) {
          // System.out.println("Calling child " + child_symbol + " from parent: " + symbol);

          if (child_symbol == Symbol.getSymbol("LAMBDA")) {
            has_lambda = true;
            break;
          }
          Node child = new Node();
          child.symbol = child_symbol;
          if (!child.buildSubTree(lexemes))
            break;
          content.addAll(child.content);
          children.add(child);
        }


        if (children.size() == right_side.size())
          return true;

        if (lexemes.size() != old_size) {
          System.out.println(symbol + " " + right_side);
          return false;
        }
      }

      return has_lambda;
    }

    public String toString() {
      return toString(0);
    }

    public String toString(int offset) {
      String res = new String(new char[offset]).replace('\0', ' ');
      res += "Symbol: " + symbol + " " + children.size();

      for (Node child : children) {
        res += '\n' + child.toString(offset + 2);
      }

      if (children.isEmpty() && !content.isEmpty()) {
        res += content.get(0).toString();
      }

      return res;
    }

    private Symbol symbol;
    private List<Lexeme> content = new LinkedList<Lexeme>();
    private ArrayList<Node> children = new ArrayList<Node>();
  }

  public static ParseTree parse(List<Lexeme> stream) throws FileNotFoundException {
    Production.initialize();

    Node root = new ParseTree.Node();
    root.symbol = Symbol.getStartSymbol();

    if (!root.buildSubTree(stream))
      return null;

    return new ParseTree(root);
  }


  private ParseTree(Node root) {
    this.setRoot(root);
  }

  public Node getRoot() {
    return root;
  }

  public String toString() {
    return root.toString();
  }

  private void setRoot(Node root) {
    this.root = root;
  }

  private Node root;
}
