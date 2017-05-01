package syntax_analyzer;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import lexical_analyzer.Token;
import lexical_analyzer.Tokenizer;

public class Production {
  // Uses the grammar in "CFG.txt".
  // The CFG format should be as follows:-
  // "Right Hand Side Symbol Label" ::= Left hand side on a single line.

  public Symbol getLeftSide() {
    return leftSide;
  }

  public void setLeftSide(Symbol leftSide) {
    this.leftSide = leftSide;
  }

  public ArrayList<Symbol> getRightSide() {
    return rightSide;
  }

  public void setRightSide(ArrayList<Symbol> rightSide) {
    this.rightSide = rightSide;
  }

  public static void initialize() throws FileNotFoundException {
    if (initialized)
      return;
    initialized = true;

    Symbol.setStartSymbol(Symbol.getSymbol("Goal"));

    Scanner in = new Scanner(new FileReader("cfg.txt"));

    while (in.hasNext()) {
      String left_side = in.next();
      if (left_side == "\n")
        continue;
      String operator = in.next();

      assert (operator == "::=");

      String right_side = in.nextLine();
      right_side = right_side.trim();

      addProduction(left_side, right_side);
    }

    in.close();
  }

  public String toString() {
    return leftSide + " ::= " + rightSide;
  }

  public static String getCorrespondingTokenLabel(String label) {
    if (label.startsWith("<")) {
      return label.substring(0, label.length() - 1);
    }
    label = label.substring(1, label.length() - 1);
    label = Tokenizer.getTokenByRegex(label).getType();
    return label;
  }

  private static void addProduction(String leftSideLabel, String rightSide) {
    if (rightSide.contains("|")) {
      String rightSides[] = rightSide.split("\\|");
      for (String singleRightSide : rightSides) {
        addProduction(leftSideLabel, singleRightSide);
      }
      return;
    }

    String[] rightSideLabels = rightSide.trim().split(" ");

    ArrayList<Symbol> rightSideSymbols = new ArrayList<Symbol>();

    for (String label : rightSideLabels) {
      if (label.equals("\"\"")) {
        label = "LAMBDA";
      } else if (isToken(label)) {
        label = getCorrespondingTokenLabel(label);
      }
      rightSideSymbols.add(Symbol.getSymbol(label));
    }

    addProduction(leftSideLabel, rightSideSymbols);
  }

  private static void addProduction(String leftSideLabel, ArrayList<Symbol> rightSide) {
    new Production(Symbol.getSymbol(leftSideLabel), rightSide);
  }


  // TODO: Should I differentiate between the tokens and other terminals?
  private static boolean isToken(String label) {
    return label.startsWith("\"") && label.endsWith("\"");
  }

  private Production(Symbol leftSide, ArrayList<Symbol> rightSide) {
    this.leftSide = leftSide;
    this.rightSide = rightSide;

    leftSide.getProductions().add(this);
  }

  private static boolean initialized = false;

  private Symbol leftSide;
  private ArrayList<Symbol> rightSide;
}
