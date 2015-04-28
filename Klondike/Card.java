/** A standard playing card. */
public class Card {

	public static final int ACE = 1;

	public static final int CLUBS = 0;

	public static final int DIAMONDS = 3;

	public static final int HEARTS = 2;

	public static final int JACK = 11;

	public static final int KING = 13;

	public static final int QUEEN = 12;

	public static final int SPADES = 1;

	/** True if this card is face-up. */
	private boolean faceUp;

	/**
	 * @see #getRank()
	 */
	private int rank;

	/**
	 * @see #getSuit()
	 */
	private int suit;

	/**
	 * Newly-created cards are face-up by default.
	 * 
	 * @see getRank()
	 * @see getSuit()
	 */
	public Card(int rank, int suit) {
		this.rank = rank;
		this.suit = suit;
		this.faceUp = true;
	}

	/**
	 * Returns the rank of this card: ACE, a number 2 to 10, JACK, QUEEN, or
	 * KING. ACE is defined as 1, and so on upward, for ease of iteration.
	 */
	public int getRank() {
		return this.rank;
	}

	/**
	 * Returns the suit of this card, one of CLUBS, SPADES, HEARTS, or DIAMONDS.
	 * These correspond to the ints 0 through 3 for ease of iteration.
	 */
	public int getSuit() {
		return this.suit;
	}

	/** Returns true if this card is face-up. */
	public boolean isFaceUp() {
		return this.faceUp;
	}

	/** Returns true if this card is red. */
	public boolean isRed() {
		return this.suit == DIAMONDS || this.suit == HEARTS;
	}

	/** Sets the face-up status of this card. */
	public void setFaceUp(boolean faceUp) {
		this.faceUp = faceUp;
	}

}
