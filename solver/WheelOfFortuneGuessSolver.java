package solver;

import java.util.*;


/**
 * Guessing strategy for Wheel of Fortune Hangman variant. (task D)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class WheelOfFortuneGuessSolver extends HangmanSolver
{

	
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

	// store the already guessed character and its positions
	private Map<Character, ArrayList<ArrayList<Integer>>> guessedCharPosition;
	
    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn from.
     */
    public WheelOfFortuneGuessSolver(Set<String> dictionary) {
        // Implement me!
    	

		// Implement me!

		dic = dictionary;
		sameWordLength = new HashSet<String>();
		charCount = new HashMap<>();
		guessedCharacter = new ArrayList<Character>();

		guessedCharPosition = new HashMap<Character, ArrayList<ArrayList<Integer>>>();
	
    } // end of WheelOfFortuneGuessSolver()


    @Override
    public void newGame(int[] wordLengths, int maxTries) {
        // Implement me!
    	
    	this.wordLengths = wordLengths;
		this.maxIncorrectGuessess = maxTries;
		
		System.out.print("world length is ");
		for(int len : wordLengths ) {
			System.out.print(" " + len + " ");
		}
		
		

		// store word that have same length to sameWordLength dictionary
		for (String s : dic) {
			// add length matched words from the dictionary to sameWordLength 
			// check if the word length is matched and already the word is not added on the set
			for(int wLength : wordLengths) {
					if(s.length() == wLength) {
						if(!sameWordLength.contains(s)) {
							sameWordLength.add(s);
						}
					}
				
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
		
		System.out.println("new word game size would be " + sameWordLength.size());
    } // end of setWordLength()


    @Override
    public char makeGuess() {
        // Implement me!

    	int max = 0;
		char guessedChar = '\0';
		// find the character that in most of the word in dictionary
		for (char key : charCount.keySet()) {
			if (charCount.get(key) >= max) {
				max = charCount.get(key);
				guessedChar = key;
			}
		}

		guessedCharacter.add(guessedChar);
		return guessedChar;
        // TODO: This is a placeholder, replace with appropriate return value.
        //return '\0';
    } // end of makeGuess()


    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer> > lPositions)
    {
        // Implement me!
    	
    	
    	Set<String> temp = new HashSet<String>();
		String str = Character.toString(c);// cast guessed character to string
		// if it is right character find word the contain that character and store in
		// filter the word list if guess character is correct
		if (bGuess) {
			// old one
			for (String s : sameWordLength) {
				if (s.contains(str)) {


					// if the multiple positions are matched , filter word that has the guessed
					// character in the exactly either one or multiple position
				
					 //word with multiple positions are matched // lastIndex is to get the index of
					//multiple character if present in the word. int matched = 0; int lastIndex =
					for (int i = 0; i < lPositions.size(); i++) {
						ArrayList<Integer> positions = lPositions.get(i);
						// ignore the case if the either first or second word does not matched with the guessed character
						if (positions == null) {
							
							continue;
						}
						// matched is to verify if multiple word matched 
						// lastIndex is for to find the index of another positions of the character
						int matched = 0;
						int lastIndex = 0;

						for (int j = 0; j < positions.size(); j++) {

							if (s.indexOf(c, lastIndex) == positions.get(j)) {
								lastIndex = positions.get(j) + 1;
								matched++;
							}

						}

						// if multiple position matched then add the word in the selected dictionary
						if (matched == positions.size()) {
							temp.add(s);
						}

					}

				}
				// this is if the character does not belongs to on of the word
				if (!s.contains(str)) {
					
					for (int i = 0; i < lPositions.size(); i++) {
						
						ArrayList<Integer> positions = lPositions.get(i);
						
						// this will check if there is already any data guessed or not
						
						
						if (guessedCharPosition.size() > 0 ) {
							if (positions == null) {

								// create new temp variableto store the word that does not have guessed character but have previous
								// guessed words for that position
								Set<String>newTemp = new HashSet<String>();
								
								// iterate loop for the already guessed character and that positions
								for (Map.Entry<Character, ArrayList<ArrayList<Integer>>> entry : guessedCharPosition
										.entrySet()) {

									Character alreadyGuessed = entry.getKey();
									ArrayList<ArrayList<Integer>> oldPositions = entry.getValue();

									ArrayList<Integer> oldPosition = oldPositions.get(i);
									
									//System.out.println("possible word is " + s);
									// if old position is null then add word that does not contain that character
									if (oldPosition == null) {
										
										
										int getWordSize = wordLengths[i];
										// select the word with the original length of character without the current guessed character.
										if (!s.contains(str)) {

											temp.add(s);
										}

									}
									else {
										// else follow the filter of words as previous
										//System.out.println("Old character " + alreadyGuessed + " old position " +oldPosition );
										int matched = 0;
										int lastIndex = 0;

										for (int j = 0; j < oldPosition.size(); j++) {

											if (s.indexOf(alreadyGuessed, lastIndex) == oldPosition.get(j)) {
												lastIndex = oldPosition.get(j) + 1;
												matched++;
											}

										}

										//System.out.println("matched " + matched + " last index " + lastIndex);
										// if multiple position matched then add the word in the selected dictionary
										if (matched == oldPosition.size()) {
											newTemp.add(s);
										}
									}

								}
								
								//System.out.println("New temp dict are " + newTemp);
								if (newTemp.size() > 0) {
									for(String tp : newTemp) {
										temp.add(tp);
									}
								}

							}
						}
						else {
							
							int getWordSize = wordLengths[i];
							// select the word with the original length of character without the current guessed character.
							if(!temp.contains(s)) {
								
								temp.add(s);
							}

						}
						
					}
				}
			}
			
			
			guessedCharPosition.put(c, lPositions);
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

			sameWordLength = temp;

			RecalculateCharDistribution();
			// charCount.remove(c);
		}
    	
    } // end of guessFeedback()
    
    
    // method recompute the frequency of the character in the selected word list
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
	


} // end of class WheelOfFortuneGuessSolver
