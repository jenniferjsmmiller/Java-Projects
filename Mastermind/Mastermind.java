public class Mastermind {
	private static final double circle_radius = 0.035;
	private MastermindModel game;
	private String textBeingEntered;

	//constructs the current game
	public Mastermind() {
		game = new MastermindModel();
		textBeingEntered = "";
	}

	//runs the game
	public static void main(String args[]) {
		new Mastermind().run();
	}

	//sets up the basic framework for playing the game (i.e. making guesses, drawing the circles, determining if the player has won or not)
	public void run() {
		StdDraw.text(0.5, 0.12, "Press letter keys (roygbv) to guess.");
		while (true) {
			textBeingEntered = "";
			for (int i = 0; i < 4; i++) {
				char input = waitForKey();
				textBeingEntered = textBeingEntered + input;
				drawGuess(i);
			}
			game.guess(textBeingEntered);
			drawResponse();

			if (game.isWon()) {
				drawWhiteRectangle();
				StdDraw.text(0.5, 0.08, "You win!");
				break;
			} else if (game.isLost()) {
				drawWhiteRectangle();
				StdDraw.text(0.5, 0.12, "You lose. Here's the code.");
				drawCorrect();
				break;
			}
		}
	}

	//draws circles for the players guesses
	public void drawGuess(int index) {
		char[] guessArray = textBeingEntered.toCharArray();
		double y = (double) game.getNumberOfGuessesMade() / 11.6 + 0.2;
		double x = (double) index / 10.0 + 0.2;

		getColor(guessArray, index);
		
		//draws circle of the color the user input
		StdDraw.filledCircle(x, y, circle_radius);
		StdDraw.setPenColor();
		StdDraw.circle(x, y, circle_radius);
	}

	//draws white and black circles to the right
	public void drawResponse() {
		double y = (double) (game.getNumberOfGuessesMade() - 1) / 11.6 + 0.2;
		StdDraw.setPenColor();
		StdDraw.circle(0.7, y, circle_radius);
		StdDraw.filledCircle(0.8, y, circle_radius);
		StdDraw.text(0.7, y, "" + game.getNumberOfWhitePegs(game.getGuess(game.getNumberOfGuessesMade() - 1)));
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(0.8, y, "" + game.getNumberOfBlackPegs(game.getGuess(game.getNumberOfGuessesMade() - 1)));
	}

	//displays the correct answer at the bottom of the screen
	public void drawCorrect() {
		for (int index = 0; index < 4; index++) {
			char[] correctArray = game.getCorrect().toCharArray();
			double y = 0.025;
			double x = (double) index / 10.0 + 0.2;

			getColor(correctArray, index);

			StdDraw.filledCircle(x, y, circle_radius);
			StdDraw.setPenColor();
			StdDraw.circle(x, y, circle_radius);
		}
	}
	
	//gets the colors for the circles 
	public void getColor(char[] array, int index) {
		if (array[index] == 'r') {
			StdDraw.setPenColor(StdDraw.RED);
		} else if (array[index] == 'o') {
			StdDraw.setPenColor(StdDraw.ORANGE);
		} else if (array[index] == 'y') {
			StdDraw.setPenColor(StdDraw.YELLOW);
		} else if (array[index] == 'g') {
			StdDraw.setPenColor(StdDraw.GREEN);
		} else if (array[index] == 'b') {
			StdDraw.setPenColor(StdDraw.BLUE);
		} else if (array[index] == 'v') {
			StdDraw.setPenColor(128, 0, 255);
		}
	}
	
	//draws a rectangle to cover up text that shouldn't be there
	public void drawWhiteRectangle() {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(0.5, 0.1, 0.4, 0.06);
		StdDraw.setPenColor();
	}

	public char waitForKey() {
		while (!StdDraw.hasNextKeyTyped()) {
		}
		return StdDraw.nextKeyTyped();
	}

}
