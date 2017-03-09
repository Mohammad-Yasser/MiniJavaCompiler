package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import lexical_analyzer.Tokenizer;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		
		
		Scanner in = new Scanner(new FileReader("code.txt"));
		String text = new String();

		while (in.hasNext()) {
			text += in.nextLine() + "\n";
		}
		
		Tokenizer.tokenize(text);

		in.close();
	}

}
