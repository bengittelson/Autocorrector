import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class AutoCorrector {
	private PrefixTree dictionary;
	private EditDistance myEditDistance;
	@SuppressWarnings("unused")
	private LinkedList<String> words;
	@SuppressWarnings("unused")
	private AutoCorrector myCorrector;

	// create a new AutoCorrector object given a filename
	public AutoCorrector(String fileName) throws IOException {
		dictionary = new PrefixTree();
		BufferedReader myReader = new BufferedReader(new FileReader(fileName));
		while (myReader.ready()) {
			String currentWord = myReader.readLine();
			dictionary.addWord(currentWord);
		}
		myReader.close();
	}

	public static void main(String[] args) {
		try {
			// make an autocorrector with the given filename
			AutoCorrector myCorrector = new AutoCorrector(args[0]);
			String suggestion;
			String input;
			Scanner inWord = new Scanner(System.in);

			// enter the loop and ask the user for input
			boolean again = true;
			while (again == true) {
				System.out.println("Please enter a word to autocorrect.");
				input = inWord.next();

				// code below deals with weird scanner errors
				if (input == null) {
					input = inWord.next();
				}
				suggestion = myCorrector.correct(input);
				if (suggestion != null) {
					System.out.println("My suggestion is: " + suggestion);
				}

				// if there's no matching word, print the user's word
				if (suggestion == null) {
					System.out.println("Your word was: " + input);
				}

				System.out
						.println("Do you want to input another word? (yes/no)");
				String choice = inWord.next();
				if (choice == null) {
					choice = inWord.next();
				}
				if (choice.equals("no")) {
					again = false;
				}

				if (!choice.equals("no") && !choice.equals("yes")) {
					System.out.println("You didn't input 'no,' so I'm"
							+ " going to assume you want to keep "
							+ "using the autocorrector. "
							+ "Input 'no' to stop next time.");
				}
			}
			inWord.close();

		} catch (IOException e) {
			System.out.println("Sorry, I couldn't find that file.");
			// e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public String correct(String word) {
		boolean foundPrefix = false;
		String substring = word.substring(0, word.length() - 1);
		int substringLength = word.length();
		String prefixMatch = word;
		PrefixTree wordList;

		// check if the word is contained in the dictionary
		if (dictionary.contains(word)) {
			return word;
		}

		// check each possible prefix using substring
		else {
			while (foundPrefix == false && substring.length() > 1) {
				substringLength--;
				substring = word.substring(0, substringLength);
				// System.out.println("substring: " + substring);
				if (dictionary.contains(substring)) {
					prefixMatch = substring;
					foundPrefix = true;
				}

			}
		}

		// System.out.println("prefixMatch: " + " y " + prefixMatch + " y ");
		if (dictionary.contains(prefixMatch) == false) {
			System.out.println("Unknown word.");
			return null;

		}
		// get the prefix tree for the final prefix
		wordList = dictionary.findPrefix(substring);

		// recursively find the list of words
		LinkedList<String> words = wordList.findWords();
		int counter = 0;

		LinkedList<String> addWords = new LinkedList<String>();
		for (String w : words) {
			addWords.add(counter,
					prefixMatch.substring(0, prefixMatch.length() - 1) + w);
		}

		// System.out.println("addWords: " + addWords.toString());

		// get the minimum edit distance
		int minEditDistance = Integer.MAX_VALUE;
		int editDistance;
		String corrected = "";

		// compare edit distances
		for (String w : addWords) {
			// System.out.println("w: " + w);
			editDistance = myEditDistance.editDistance(w, word);
			// System.out.println("w: " + w + " " + "editDistance: "
			// + editDistance);
			if (editDistance < minEditDistance) {
				minEditDistance = editDistance;
				corrected = w;
			}
		}
		return corrected;
	}
}
