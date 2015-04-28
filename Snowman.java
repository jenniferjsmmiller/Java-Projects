
import java.applet.Applet;

/** A word game. */
public class Snowman extends Applet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * Returns true if known contains no underscores.
	 * 
	 * @param known What the player has already learned (letters or underscores).
	 */
	public static boolean complete(char[] known) {
		for (int i = 0; i < known.length; i++) {
			if (known[i] == '_') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Draws the state of the game.
	 * 
	 * @param guesses Number of guesses the player has left.
	 * @param known What the player has already learned (letters or underscores).
	 * @param word The correct word.
	 */
	public static void draw(int guesses, char[] known, String word) {
		StdDraw.clear();
		StdDraw.line(0.1, 0.3, 0.9, 0.3); // Ground
		if (guesses < 6) { // Base
			StdDraw.setPenColor(new java.awt.Color(StdRandom.uniform(256), StdRandom.uniform(256), StdRandom.uniform(256)));
			StdDraw.circle(0.5, 0.45, 0.15);
		}
		if (guesses < 5) { // Torso
			StdDraw.circle(0.5, 0.7, 0.1);
		}
		if (guesses < 4) { // Head
			StdDraw.circle(0.5, 0.85, 0.05);
		}
		if (guesses < 3) { // Left eye
			StdDraw.filledRectangle(0.48, 0.87, 0.01, 0.01);
		}
		if (guesses < 2) { // Right leg
			StdDraw.filledRectangle(0.52, 0.87, 0.01, 0.01);
		}
		if (guesses < 1) { // Nose
			StdDraw.setPenColor(java.awt.Color.ORANGE);
			StdDraw.filledPolygon(new double[] {0.5, 0.5, 0.54}, new double[] {0.83, 0.84, 0.835});
			StdDraw.setPenColor();
		}
		String s = "" + known[0];
		for (int i = 1; i < known.length; i++) {
			s += " " + known[i];
		}
		StdDraw.text(0.5, 0.2, s);
		if (guesses == 0) {
			StdDraw.text(0.5, 0.1, "You lose. The word was '" + word + "'.");
		} else if (complete(known)) {
			StdDraw.text(0.5, 0.1, "You win!");
		} else {
			StdDraw.text(0.5, 0.1, guesses + " guesses left");
		}
		StdDraw.show(0);
	}

	/**
	 * Returns true if letter occurs at least once in word. The corresponding
	 * elements of known are set to letter.
	 * 
	 * @param letter The letter the player has just guessed.
	 * @param known What the player has already learned (letters or underscores).
	 * @param word The correct word.
	 */
	public static boolean found(char letter, String word, char[] known) {
		boolean isFound = false;
		for (int i = 0; i < known.length; i++) {
			if (word.charAt(i) == letter) {
				known[i] = letter;
				isFound = true;	
			}
		}
		return isFound;
	}

	public static void main(String[] args) {
		String[] dictionary = readDictionary();
		String word = randomWord(dictionary);
		int guesses = 6;
		char[] known = new char[word.length()];
		for (int i = 0; i < known.length; i++) {
			known[i] = '_';
		}
		while (guesses > 0 && !complete(known)) {
			draw(guesses, known, word);
			while (!StdDraw.hasNextKeyTyped()) {
				// Wait for keypress
			}
			char letter = StdDraw.nextKeyTyped();
			if (!found(letter, word, known)) {
				guesses--;
			}
		}
		draw(guesses, known, word);
	}

	/** Returns a random word from dictionary. */
	public static String randomWord(String[] dictionary) {
		int index = (int) (Math.random() * dictionary.length);
		return dictionary[index];
	}

	/** Reads and returns the dictionary. */
	public static String[] readDictionary() {
		In input = new In("enable1.txt");
		java.util.ArrayList<String> dictionary = new java.util.ArrayList<String>();
		while (input.hasNextLine()) {
			String word = input.readLine();
			if (word.equals(word.toLowerCase())) {
				dictionary.add(word);
			}
		}
		return dictionary.toArray(new String[dictionary.size()]);
	}

}
