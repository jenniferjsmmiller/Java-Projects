/** A deck or pile of cards. */
public class Deck {

	/** The cards in this deck. */
	private Card[] cards;

	/** @see #size() */
	private int size;

	/** A new deck is initially empty. */
	public Deck() {
		this.cards = new Card[52];
	}

	/** Adds card to the top of this deck. */
	public void add(Card card) {
		this.cards[size] = card;
		size++;
	}

	/** Adds all cards in a standard deck. */
	public void fill() {
		size = 0;
		for (int suit = 0; suit < 4; suit++) {
			for (int rank = 1; rank < 14; rank++) {
				cards[size] = new Card(rank, suit);
				size++;
			}
		}
	}

	/**
	 * Returns the ith card in this deck, where card 0 is the one on the bottom.
	 * Assumes the deck is not empty.
	 */
	public Card get(int i) {
		return this.cards[i];
	}

	/** Moves one card from the top of this deck to the top of that deck. */
	public void move(Deck that) {
		Card toMove = this.cards[size - 1];
		that.add(toMove);
		size--;
	}

	/**
	 * Moves the top n cards from the top of this deck to the top of that deck.
	 * They maintain their order, so that the card that used to be on top of
	 * this becomes the top of that.
	 */
	public void move(Deck that, int n) {
		for (int i = 0; i < n ; i++) {
			that.add(cards[size - (n - i)]);
		}
		size = size - n;
	}

	/**
	 * Randomly reorders the cards in this deck.
	 */
	public void shuffle() {
		for (int i = 0; i < size; i++) {
			Card x = this.cards[i];
			int random = StdRandom.uniform(size - i) + i;
			Card y = this.cards[random];
			this.cards[i] = y;
			this.cards[random] = x;
		}
	}

	/** Returns the number of cards in this deck. */
	public int size() {
		return this.size;
	}

	/**
	 * Returns the top card on this deck (but does not modify the deck).
	 * 
	 * @return null if this deck is empty.
	 */
	public Card top() {
		if (size == 0) {
			return null;
		} else {
			return this.cards[size - 1];
		}
	}

}
