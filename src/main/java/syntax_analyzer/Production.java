package syntax_analyzer;

import java.util.ArrayList;

public class Production {
  public static void addProduction(String leftSideLabel, String rightSide) {
    if (rightSide.contains("|")) {
      String rightSides[] = rightSide.split("|");
      for (String singleRightSide : rightSides) {
        addProduction(leftSideLabel, singleRightSide);
      }
      return;
    }


    String[] rightSideLabels = rightSide.split(" ");

    ArrayList<Symbol> rightSideSymbols = new ArrayList<Symbol>();

    for (String label : rightSideLabels) {
      if (isToken(label)) {
        // Strip the string of <> and "".
        rightSideSymbols.add(Symbol.getSymbol(label.substring(1, label.length() - 1)));
      } else {
        rightSideSymbols.add(Symbol.getSymbol(label));
      }
    }

    addProduction(leftSideLabel, rightSideSymbols);
  }

  public static void addProduction(String leftSideLabel, ArrayList<Symbol> rightSide) {
    new Production(Symbol.getSymbol(leftSideLabel), rightSide);
  }

  private static boolean isToken(String label) {
    // TODO: Should I differentiate between the tokens and other terminals?
    return label.startsWith("<") && label.endsWith(">") || isTerminal(label);
  }

  private static boolean isTerminal(String label) {
    return label.startsWith("\"") && label.endsWith("\"");
  }

  private Production(Symbol leftSide, ArrayList<Symbol> rightSide) {
    this.leftSide = leftSide;
    this.rightSide = rightSide;

    leftSide.getProductions().add(this);
  }

  private Symbol leftSide;
  private ArrayList<Symbol> rightSide;
}
