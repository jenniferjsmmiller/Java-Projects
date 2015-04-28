
public class MastermindModel {
	
	private String correctAnswer;
    private char[] correctArray;
    private String[] guesses;
    private int guessesMade;
    private boolean isWon;

    //constructs the model for the game
    public MastermindModel(String string) {
    	correctAnswer = string;
    	correctArray = correctAnswer.toCharArray();
    	guessesMade = 0;
    	guesses = new String[10];
    }

    //this constructs a random, four letter answer for each game
    public MastermindModel() {
    	char[] options = { 'r', 'o', 'y', 'g', 'v', 'b' };
    	correctArray = new char[4];
   	 	for (int i = 0; i < 4; i++) {
   	 		correctArray[i] = options[StdRandom.uniform(0, 6)];
   	 	}
   	 	correctAnswer = "";
   	 	for (int i = 0; i < correctArray.length; i++) {
   	 		correctAnswer += correctArray[i];
   	 	}
   	 	guessesMade = 0;
   	 	guesses = new String[10];
    }

    //returns the number of black pegs (correct color and correct location)
    public int getNumberOfBlackPegs(String string) {
    	char guessArray[] = string.toCharArray();
    	int numberBlackPegs = 0;
    	for (int i = 0; i < string.length(); i++) {
    		if (guessArray[i] == correctArray[i]) {
    			numberBlackPegs++;
    		}
    	}
   	 	return numberBlackPegs;
    }

    //counts how many of each color is in the given string
    public int[] counts(String string) {
    	int[] result = new int[6];
    	for (int i = 0; i < string.length(); i++) {
    		int j = "roygbv".indexOf(string.charAt(i));
    		result[j]++;
    	}
    	return result;
	}

    //returns the number of white pegs (correct color and wrong location)
    public int getNumberOfWhitePegs(String string) {
    	int guessArraySorted[] = counts(string);
    	int correctArraySorted[] = counts(correctAnswer);
    	int numberWhitePegs = 0;
    	for (int i = 0; i < correctArraySorted.length; i++) {
    		while (guessArraySorted[i] > 0 && correctArraySorted[i] > 0) {
    			numberWhitePegs++;
    			guessArraySorted[i]--;
    			correctArraySorted[i]--;
    		}
		}
    	return numberWhitePegs - getNumberOfBlackPegs(string);
    }
    
    //returns the number of guesses made so far
    public int getNumberOfGuessesMade() {
   	 	return guessesMade;
    }

    //returns how many guesses you have left
    public String getGuess(int i) {
   	 	return guesses[i];
    }

    //returns true if the player has won the game
    public boolean isWon() {
    	if (guessesMade > 0 && guesses[guessesMade - 1].equals(correctAnswer)) {
    		return true;
    	}
    	return false;
    }

    //stores a guess the player has made
    public void guess(String correct) {
   	 	guesses[guessesMade] = correct;
   	 	if (correct == correctAnswer) {
   	 		isWon = true;
   	 	}
   	 	guessesMade++;
    }

    //returns true if the player lost the game
    public boolean isLost() {
   	 	if (guessesMade == 10 && !isWon) {
   	 		return true;
   	 	}
   	 	return false;
    }

    //returns the correct answer
    public String getCorrect() {
    	return correctAnswer;
    }

}
