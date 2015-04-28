/** Graphic user interface for the Klondike solitaire game. */
public class Klondike {

	/** java.awt.Color for the background. */
	public static final java.awt.Color DARK_GREEN = new java.awt.Color(0, 63, 0);

	public static void main(String[] args) {
		new Klondike().run();
	}

	/** Game model associated with this GUI. */
	private KlondikeModel model;

	/** True when waiting for a first mouse click. */
	private boolean waitingForSource;

	public Klondike() {
		model = new KlondikeModel();
		waitingForSource = true;
	}

	/**
	 * Displays the current state of the game.
	 */
	public void draw() {
		// Draw background
		StdDraw.clear(DARK_GREEN);
		// Draw cards
		draw(model.getDrawPile(), 1 / 8.0, 0.9, false);
		draw(model.getDiscardPile(), 2 / 8.0, 0.9, false);
		for (int i = 0; i < 4; i++) {
			draw(model.getFoundation(i), (i + 4) / 8.0, 0.9, false);
		}
		for (int i = 0; i < 7; i++) {
			draw(model.getTableau(i), (i + 1) / 8.0, 0.7, true);
		}
		// Draw labels for foundations
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(4 / 8.0, 1.0, "C");
		StdDraw.text(5 / 8.0, 1.0, "S");
		StdDraw.text(6 / 8.0, 1.0, "H");
		StdDraw.text(7 / 8.0, 1.0, "D");
		// Draw instructions
		if (waitingForSource) {
			StdDraw.text(0.5, 0.0, "Click on deck, draw pile, or tableau.");
		} else {
			StdDraw.text(0.5, 0.0,
					"Click on destination, or on background to abort move.");
		}
		StdDraw.show(0);
	}

	/**
	 * Draws this deck at x, y. If splayed is false, only the top card is drawn.
	 * Otherwise, the cards appear splayed, with cards closer to the top of the
	 * deck lower on the screen but in front of other cards.
	 */
	public void draw(Deck deck, double x, double y, boolean splayed) {
		if (deck.size() == 0) {
			StdDraw.setPenColor(StdDraw.GRAY);
			StdDraw.filledRectangle(x, y, 0.05, 0.075);
		} else if (splayed) {
			for (int i = 0; i < deck.size(); i++) {
				StdDraw.picture(x, y, imageFilename(deck.get(i)), 0.1, 0.15);
				y -= 0.05;
			}
		} else {
			StdDraw.picture(x, y, imageFilename(deck.get(deck.size() - 1)), 0.1, 0.15);
		}
	}

	/**
	 * Returns the filename of the image for this card. All of the files (from
	 * http://www.jfitz.com/cards/) should be in a directory "card-images".
	 */
	public String imageFilename(Card card) {
		if (!card.isFaceUp()) {
			return "card-images" + java.io.File.separator + "b2fv.png";
		}
		int result = 1 + card.getSuit();
		if (card.getRank() > 1) {
			result += 4 * (14 - card.getRank());
		}
		return "card-images" + java.io.File.separator + result + ".png";
	}

	/** Returns the column of cards in which the mouse is located. */
	public int mouseColumn() {
		int result = (int) Math.round(StdDraw.mouseX() * 8);
		if (result < 1) {
			result = 1;
		} else if (result > 7) {
			result = 7;
		}
		return result;
	}

	/** Plays the game. */
	public void run() {
		Deck source = null;
		while (true) {
			draw();
			while (!StdDraw.mousePressed()) {
				// Wait for mouse press
			}
			if (waitingForSource) {
				if (StdDraw.mouseY() > 0.8) { // Clicked above
					Deck discardPile = model.getDiscardPile();
					if (mouseColumn() == 1) {
						model.drawNextCard();
					} else if ((mouseColumn() == 2) && (discardPile.size() > 0)) {
						source = discardPile;
						waitingForSource = false;
					}
				} else { // Clicked on tableau
					source = model.getTableau(mouseColumn() - 1);
					if (source.size() > 0) {
						waitingForSource = false;
					}
				}
			} else { // Waiting for destination
				waitingForSource = true;
				if (StdDraw.mouseY() > 0.8) { // Clicked above
					model.moveToFoundation(source, mouseColumn() - 4);
				} else { // Clicked on tableau
					model.moveToTableau(source, mouseColumn() - 1);
				}
			}
			while (StdDraw.mousePressed()) {
				// Wait for mouse release
			}
		}
	}

}
