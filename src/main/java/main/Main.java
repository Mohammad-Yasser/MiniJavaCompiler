package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import lexical_analyzer.Lexeme;
import lexical_analyzer.Tokenizer;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner in = new Scanner(new FileReader("code.txt"));
		String text = new String();

		while (in.hasNext()) {
			text += in.nextLine() + "\n";
		}

		ArrayList<Lexeme> lexemes = Tokenizer.tokenize(text);

		System.out.println(Arrays.deepToString(lexemes.toArray()));

		in.close();
	}

}
