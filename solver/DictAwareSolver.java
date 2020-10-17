package solver;

import java.util.*;
import java.lang.System;
import java.util.Map.Entry;

/**
 * Dictionary aware guessing strategy for Hangman. (task B) You'll need to
 * complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class DictAwareSolver extends HangmanSolver {
	// word lengths
	private int[] wordLengths;
	// max incorrect guessess count
	private int maxIncorrectGuessess;
	// dictionary for algorithms to use
	private Set<String> dic;

	// storage for word that have same length as guessed words
	private Set<String> sameWordLength;

	// storage for charactor count amount
	private Map<Character, Integer> charCount;
	// storage for guessed character
	private ArrayList<Character> guessedCharacter;

	/**
	 * Constructor.
	 *
	 * @param dictionary Dictionary of words that the guessed words are drawn from.
	 */
	public DictAwareSolver(Set<String> dictionary) {
		dic = dictionary;
		sameWordLength = new HashSet<String>();
		charCount = new HashMap<>();
		guessedCharacter = new ArrayList<Character>();
	} // end of DictAwareSolver()

	@Override
	public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
		this.wordLengths = wordLengths;
		this.maxIncorrectGuessess = maxIncorrectGuesses;
		// store word that have same length to sameWordLength dictionary
		for (String s : dic) {
			if (s.length() == wordLengths[0]) {
				sameWordLength.add(s);
			}
		}

		// store character and number of word contain that character

		for (String s : sameWordLength) {
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (!charCount.containsKey(c)) {
					charCount.put(c, 1);
				} else {
					int temp = charCount.get(c) + 1;
					charCount.put(c, temp);
				}
			}
		}

		/*
		 * for (char key : charCount.keySet()) { System.out.println(key + " : " +
		 * charCount.get(key)); }
		 */
		/*
		 * for (String s : sameWordLength){ System.out.println(s); }
		 */
	} // end of newGame()

	@Override
	public char makeGuess() {

		int max = 0;
		char guessedChar = '\0';
		// find the character that in most of the word in dictionary
		for (char key : charCount.keySet()) {
			if (charCount.get(key) >= max) {
				max = charCount.get(key);
				guessedChar = key;
			}
		}

		/*
		 * // find the character that in most of the word in dictionary that store same
		 * length of guessedword for (Entry<Character, Integer> entry :
		 * charCount.entrySet()) { if (entry.getValue() == max) { guessedChar =
		 * entry.getKey(); break; } }
		 */
		guessedCharacter.add(guessedChar);
		return guessedChar;
	} // end of makeGuess()

	@Override
	public void guessFeedback(char c, Boolean bGuess, ArrayList<ArrayList<Integer>> lPositions) {
		Set<String> temp = new HashSet<String>();
		String str = Character.toString(c);// cast guessed character to string
		// if it is right character find word the contain that character and store in
		// set
		if (bGuess) {
			for (String s : sameWordLength) {
				if (s.contains(str)) {

					// if the multiple positions are matched , filter word that has the guessed
					// character in the exactly either one or multiple position
					ArrayList<Integer> positions = lPositions.get(0);
					// matched to track the word with multiple positions are matched 
					// lastIndex is to get the index of multiple character if present in the word.
					int matched = 0;
					int lastIndex = 0;

					for (int i = 0; i < positions.size(); i++) {

						if (s.indexOf(c, lastIndex) == lPositions.get(0).get(i)) {
							lastIndex = lPositions.get(0).get(i) + 1;
							matched++;
						}

					}

					// if multiple position matched then add the word in the selected dictionary
					if (matched == positions.size()) {
						temp.add(s);
					}

					/*
					 * if (s.indexOf(c) == lPositions.get(0).get(0)) { temp.add(s); }
					 * 
					 */

				}
			}
			
			System.out.println("new word size would be " + temp.size());
			System.out.println(temp);
			sameWordLength = temp;
			// recaulate Character distribution again
			RecalculateCharDistribution();
			// remove character that already guessed
			charCount.remove(c);

		} else {
			// if character is not in word so remove the word for the list
			temp = sameWordLength;

			for (Iterator<String> iterator = temp.iterator(); iterator.hasNext();) {
				String s = iterator.next();
				if (s.contains(str)) {
					iterator.remove();
				}
			}
			/*
			 * for (String s : sameWordLength) { if (s.contains(str)) { temp.remove(s); } }
			 */

			sameWordLength = temp;

			RecalculateCharDistribution();
			// charCount.remove(c);
		}
		// Implement me!
	} // end of guessFeedback()

	private void RecalculateCharDistribution() {
		Map<Character, Integer> temp = new HashMap<>();
		for (String s : sameWordLength) {
			for (int i = 0; i < s.length(); i++) {

				char ch = s.charAt(i);
				if (guessedCharacter.contains(ch)) {

				} else {
					if (!temp.containsKey(ch)) {
						temp.put(ch, 1);
					} else {
						int t = temp.get(ch) + 1;
						temp.put(ch, t);
					}
				}
			}
		}

		charCount = temp;

	}
} // end of class DictAwareSolver
