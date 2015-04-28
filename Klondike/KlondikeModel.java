/**
 * Model of the Klondike solitaire game.
 */
public class KlondikeModel {

	/** The pile of cards available to move to the tableau or foundations. */
	private Deck discardPile;

	/** The pile of undrawn cards. */
	private Deck drawPile;

	/** Piles of cards building on the aces. */
	private Deck[] foundations;

	/** Splayed piles of cards. */
	private Deck[] tableau;

	public KlondikeModel() {
		// Prepare the draw pile
		drawPile = new Deck();
		drawPile.fill();
		drawPile.shuffle();
		for (int i = 0; i < drawPile.size(); i++) {
			drawPile.get(i).setFaceUp(false);
		}
		// Create the tableau
		tableau = new Deck[7];
		for (int i = 0; i < tableau.length; i++) {
			tableau[i] = new Deck();
			for (int j = 0; j <= i; j++) {
				drawPile.move(tableau[i]);
				if (j == i) {
					tableau[i].get(j).setFaceUp(true);
				}
			}
		}
		// Create the empty foundations
		foundations = new Deck[4];
		for (int i = 0; i < foundations.length; i++) {
			foundations[i] = new Deck();
		}
		// Draw the first card
		discardPile = new Deck();
		drawPile.move(discardPile);
		discardPile.top().setFaceUp(true);
	}

	/**
	 * Takes the next card from the draw pile and places it face up on the
	 * discard pile. Does nothing if the draw pile is empty.
	 */
	public void drawNextCard() {
		if (drawPile.size() > 0) {
			drawPile.move(discardPile);
			discardPile.top().setFaceUp(true);
		}
	}

	/** Returns the discard pile. */
	public Deck getDiscardPile() {
		return discardPile;
	}

	/** Returns the deck. */
	public Deck getDrawPile() {
		return drawPile;
	}

	/**
	 * Returns the ith foundation (indexed by suit).
	 * 
	 * @see Card#getSuit()
	 */
	public Deck getFoundation(int i) {
		return foundations[i];
	}

	/** Returns the ith tableau pile (zero-indexed from left). */
	public Deck getTableau(int i) {
		return tableau[i];
	}

	/**
	 * Moves a sequence of face-up cards from here to there, so that the face-up
	 * cards on that form a sequence in ranks with alternating colors. For
	 * example, if we have:
	 * <p>
	 * a: ## 8d 7s 6d 5s 4d 3c<br>
	 * b: ## ## 6h
	 * <p>
	 * then klondikeMove(a, b) should result in:
	 * <p>
	 * a: ## 8d 7s 6d<br>
	 * b: ## ## 6h 5s 4d 3c
	 * 
	 * Has no effect if the move is illegal.
	 */
	public void klondikeMove(Deck here, Deck there) {
		Card c = there.top(); // The card of top of that, if any
		for (int i = here.size() - 1; (i >= 0) && here.get(i).isFaceUp(); i--) {
			if (successor(here.get(i), c)) {
				here.move(there, here.size() - i);
			}
		}
	}

	/**
	 * Moves a card from source to the specified foundation, if this is legal.
	 * The next card in source (if any) is turned face up.
	 */
	public void moveToFoundation(Deck source, int foundationIndex) {
		if ((foundationIndex >= 0) && (foundationIndex < 4)) {
			Card c = source.top();
			Deck foundation = foundations[foundationIndex];
			if ((c.getSuit() == foundationIndex)
					&& (((foundation.size() == 0) && (c.getRank() == Card.ACE)) || ((foundation
							.size() > 0) && (c.getRank() == foundation.top()
							.getRank() + 1)))) {
				source.move(foundation);
				if (source.size() > 0) {
					source.top().setFaceUp(true);
				}
			}
		}
	}

	/**
	 * Moves one card (if source is the draw pile) or several (if source is a
	 * tableau pile) from source to the indicated tableau pile. Does nothing if
	 * the move would be illegal.
	 */
	public void moveToTableau(Deck source, int tableauIndex) {
		Deck destination = tableau[tableauIndex];
		if (source == discardPile) {
			if (successor(source.top(), destination.top())) {
				source.move(destination);
			}
		} else {
			klondikeMove(source, destination);
			if (source.size() > 0) {
				source.top().setFaceUp(true);
			}
		}
	}

	/**
	 * Returns true if a can be placed on top of b, i.e., either a and b are
	 * different colors and a's rank is one less than b's, or a is a king and b
	 * is null.
	 */
	public boolean successor(Card a, Card b) {
		return ((b == null) && (a.getRank() == Card.KING))
				|| ((b != null) && (a.getRank() == b.getRank() - 1) && (a
						.isRed() != b.isRed()));
	}

}
