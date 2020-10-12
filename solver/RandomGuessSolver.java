package solver;

import java.util.*;
import java.lang.System;

/**
 * Random guessing strategy for Hangman. (task A)
 * You'll need to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class RandomGuessSolver extends HangmanSolver
{

	
	// store the already guessed characters might be right or wrong
	private ArrayList<Character> guessedCharacters;
	
    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn from.
     */
    public RandomGuessSolver(Set<String> dictionary) {
        // Implement me!
    	

    	 guessedCharacters = new ArrayList<Character>();
    	
    } // end of RandomGuessSolver()


    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
        // Implement me!

    
  	
    	//System.out.println("Random guess solver new game called implement me");
    } // end of newGame()


    @Override
    public char makeGuess() {
        // Implement me!
    	
    	System.out.println(" make guess called ");
    	
    	char guessedChar =  this.getRandomCharacter();
    	
    	// store the previous guessed character that was created randomly
    	guessedCharacters.add(guessedChar);
    	
    
    	return  guessedChar;

    	
    	
    	
        // TODO: This is a placeholder, replace with appropriate return value.
       // return '\0';
    } // end of makeGuess()


    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer> > lPositions)
    {
        // Implement me!
    	
    	
    	System.out.println(" guess feedback called ");
    	
    	
    	//System.out.println("Random guess solver guess Feedback called implement me");
    	
    } // end of guessFeedback()
    
    // check if the random character generated is already generated on the guessed characters
    public char getRandomCharacter() {
    	
    	boolean valid = false;
    	Random rand = new Random();
    	String characters = "abcdefghijklmnopqrstuvwxyz'";
    	char c = '\0';
    	while(!valid) {
    		
    		
    		 c = characters.charAt(rand.nextInt(characters.length()));
 
    		if (!guessedCharacters.contains(c)) {
    			valid = true;
    		}

    	}

    	return c;
    	
    	//return '\0';
    }

} // end of class RandomGuessSolver
