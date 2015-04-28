/** The game also known as The Captain's Mistress. */
public class Connect4 {

	/** Number of columns on the board. */
	public static final int COLUMNS = 7;

	/** Number of rows on the board. */
	public static final int ROWS = 6;

	/**
	 * Returns black's best move.
	 * @param maxDepth Maximum search depth, e.g., 1 to consider moves but not replies
	 */
	public static int bestMoveForBlack(java.awt.Color[][] board, int maxDepth) {
		int bestResult = -2;
		int move = 0;
		for (int c = 0; c < COLUMNS; c++) {
			if (legal(board, c)) {
				drop(board, StdDraw.BLACK, c);
				int result = min(board, maxDepth, 1);
				undo(board, c);
				if (result >= bestResult) {
					move = c;
					bestResult = result;
				}
			}
		}
		return move;
	}

	/** Draws the board. */
	public static void draw(java.awt.Color[][] board, java.awt.Color currentPlayer) {
		StdDraw.clear(StdDraw.BLUE);
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				StdDraw.setPenColor(board[r][c]);
				StdDraw.filledCircle((1.0 / (COLUMNS + 2)) * (c + 1.5),
						(1.0 / (ROWS + 2)) * (r + 2.5), 0.05);
			}
		}
		StdDraw.setPenColor(StdDraw.WHITE);
		if (winner(board) == StdDraw.BLACK) {
			StdDraw.text(0.5, 0.1, "Black wins!");
		} else if (winner(board) == StdDraw.WHITE) {
			StdDraw.text(0.5, 0.1, "White wins!");
		} else if (full(board)) {
			StdDraw.text(0.5, 0.1, "Draw.");
		} else if (currentPlayer == StdDraw.BLACK) {
			StdDraw.text(0.5, 0.1, "Black to play.");
		} else {
			StdDraw.text(0.5, 0.1, "White to play.");
		}
		StdDraw.show(0);
	}

	/**
	 * Drops a piece of java.awt.Color in column. Modifies board. Assumes the move is
	 * legal.
	 */
	public static void drop(java.awt.Color[][] board, java.awt.Color color, int column) {
		for (int r = 0; r < ROWS; r++) {
			if (board[r][column] == StdDraw.GRAY) {
				board[r][column] = color;
				break;
			}
		}
	}

	/** Returns true if board is full. */
	public static boolean full(java.awt.Color[][] board) {
		boolean isFull = true;
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				if (board[r][c] == StdDraw.GRAY) {
					isFull = false;
				}
			}
		}
		return isFull;
	}

	/** Returns true if column is neither off the edge of the board nor full. */
	public static boolean legal(java.awt.Color[][] board, int column) {
			for (int r = 0; r < ROWS; r++) {
				if ((column >= 0 && column < 7) && (full(board) != true) && (board[r][column] == StdDraw.GRAY))  {
					return true;
				}
			}	
			return false;
	}

	public static void main(String[] args) {
		java.awt.Color[][] board = new java.awt.Color[6][7];
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				board[r][c] = StdDraw.GRAY;
			}
		}
		java.awt.Color currentPlayer = StdDraw.BLACK;
		while ((winner(board) == StdDraw.GRAY) && !full(board)) {
			draw(board, currentPlayer);
			if (currentPlayer == StdDraw.BLACK) { // Computer turn
				// The second argument to bestMoveForBlack() can be increased
				// for a stronger, slower opponent
				drop(board, StdDraw.BLACK, bestMoveForBlack(board, 5));
				currentPlayer = opposite(currentPlayer);
			} else { // Human turn
				while (!StdDraw.mousePressed()) {
					// Wait for mouse press
				}
				int column = (int) (Math.round((StdDraw.mouseX() / (1.0 / (board[0].length + 2))) - 1.5));
				if (legal(board, column)) {
					drop(board, currentPlayer, column);
					currentPlayer = opposite(currentPlayer);
				}
				while (StdDraw.mousePressed()) {
					// Wait for mouse release
				}
			}
		}
		draw(board, currentPlayer);
	}

	/**
	 * Returns the value of board with black to move: 1 if black can force a
	 * win, -1 if black cannot avoid a loss, 0 otherwise.
	 * 
	 * @param maxDepth Maximum search depth, e.g., 1 to consider moves but not replies
	 * @param depth Current search depth, e.g., 0 if this is the root position.
	 */
	public static int max(java.awt.Color[][] board, int maxDepth, int depth) {
		java.awt.Color winner = winner(board);
		if (winner == StdDraw.BLACK) {
			return 1;
		} else if (winner == StdDraw.WHITE) {
			return -1;
		} else if (full(board) || (depth == maxDepth)) {
			return 0;
		} else {
			int bestResult = -2;
			for (int c = 0; c < COLUMNS; c++) {
				if (legal(board, c)) {
					drop(board, StdDraw.BLACK, c);
					int result = min(board, maxDepth, depth + 1);
					undo(board, c);
					if (result >= bestResult) {
						bestResult = result;
					}
				}
			}
			return bestResult;
		}
	}

	/**
	 * Returns the value of board with white to move: 1 if white cannot avoid a
	 * loss, -1 if white can force a win, 0 otherwise.
	 * 
	 * @param maxDepth Maximum search depth, e.g., 1 to consider moves but not replies
	 * @param depth Current search depth, e.g., 0 if this is the root position.
	 */
	public static int min(java.awt.Color[][] board, int maxDepth, int depth) {
		java.awt.Color winner = winner(board);
		if (winner == StdDraw.BLACK) {
			return 1;
		} else if (winner == StdDraw.WHITE) {
			return -1;
		} else if (full(board) || (depth == maxDepth)) {
			return 0;
		} else {
			int bestResult = 2;
			for (int c = 0; c < COLUMNS; c++) {
				if (legal(board, c)) {
					drop(board, StdDraw.WHITE, c);
					int result = max(board, maxDepth, depth + 1);
					undo(board, c);
					if (result <= bestResult) {
						bestResult = result;
					}
				}
			}
			return bestResult;
		}
	}

	/**
	 * opposite(StdDraw.BLACK) returns StdDraw.WHITE. opposite(StdDraw.WHITE)
	 * returns StdDraw.BLACK.
	 */
	public static java.awt.Color opposite(java.awt.Color color) {
		if (color == StdDraw.BLACK) {
			return StdDraw.WHITE;	
		} else {
			return StdDraw.BLACK;
		}	
	}

	/**
	 * Removes the top piece from column. Modifies board. Assumes column is not
	 * empty.
	 */
	public static void undo(java.awt.Color[][] board, int column) {
		int r;
		for (r = ROWS - 1; board[r][column] == StdDraw.GRAY; r--) {
			// All the work is done in the loop header
		}
		board[r][column] = StdDraw.GRAY;
	}

	/**
	 * Returns the java.awt.Color of the player with four in a row starting at r, c and
	 * advancing rOffset, cOffset each step. For example, if rOffset is 1 and
	 * cOffset is 0, r increases by 1 at each step. Returns GRAY if no player
	 * has four in a row here, or if there aren't four spaces starting here.
	 */
	public static java.awt.Color winAt(java.awt.Color[][] board, int r, int c, int rOffset,
			int cOffset) {
		java.awt.Color currentPlayer = board[r][c];
		for (int i = 0; i < 4; i ++) {
			if (r < 0 || r > 5 || c < 0 || c > 6) {
				return StdDraw.GRAY;
			}
			if (board[r][c] != currentPlayer) {
				return StdDraw.GRAY;
			} 
			r += rOffset;
			c += cOffset;
		}
		return currentPlayer;
	}

	/**
	 * Returns StdDraw.BLACK if black has won, StdDraw.WHITE if white has won,
	 * or StdDraw.GRAY if neither player has won.
	 */
	public static java.awt.Color winner(java.awt.Color[][] board) {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				java.awt.Color currentValue = winAt(board, r, c, 1, 0);
				if (currentValue != StdDraw.GRAY) {
					return currentValue;
				}
			}	
		}
		
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				java.awt.Color currentValue = winAt(board, r, c, 0, 1);
				if (currentValue != StdDraw.GRAY) {
					return currentValue;
				}
			}	
		}

		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				java.awt.Color currentValue = winAt(board, r, c, 1, 1);
				if (currentValue != StdDraw.GRAY) {
					return currentValue;
				}
			}	
		}
		
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				java.awt.Color currentValue = winAt(board, r, c, 1, -1);
				if (currentValue != StdDraw.GRAY) {
					return currentValue;
				}
			}	
		}
		return StdDraw.GRAY;
	}

}
