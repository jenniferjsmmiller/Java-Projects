
/** GUI for the Questions game. */
public class Questions {

	/** Radius of circles drawn for nodes. */
	public static final double CIRCLE_RADIUS = 0.01;

	public static void main(String[] args) {
		new Questions().run();
	}

	/** Game model associated with this GUI. */
	private QuestionsModel model;

	/** Question or instructions for the user. */
	private String label;

	/** The text currently being entered by the user. */
	private String textBeingEntered;

	public Questions() {
		model = new QuestionsModel();
		label = "";
		textBeingEntered = "";
	}

	/** Draws the state of the model. */
	public void draw() {
		StdDraw.clear();
		StdDraw.text(0.5, 0.9, label);
		StdDraw.text(0.5, 0.8, textBeingEntered);
		double yDecrement = 0.5 / (model.getRoot().height() + 1);
		drawSubtree(model.getRoot(), 0.5 - yDecrement / 2, yDecrement, 0.0, 1.0);
		StdDraw.show(0);
	}

	/**
	 * Draws the subtree rooted at node.
	 * 
	 * @param y
	 *            y coordinate at which to draw node.
	 * @param yDecrement
	 *            amount to decrease y coordinate for each level of the tree.
	 * @param left
	 *            left boundary of drawing space for this subtree.
	 * @param right
	 *            right boundary of drawing space for this subtree.
	 */
	public void drawSubtree(BinaryNode node, double y, double yDecrement,
			double left, double right) {

		double x = (left + right) / 2;
		StdDraw.setPenColor();
		StdDraw.line(x, y, (x + left) / 2, y - yDecrement);
		StdDraw.line(x, y, (x + right) / 2, y - yDecrement);

		StdDraw.setPenColor(255, 255, 255);
		StdDraw.filledCircle((x + left) / 2, y - yDecrement, CIRCLE_RADIUS);
		StdDraw.filledCircle((x + right) / 2, y - yDecrement, CIRCLE_RADIUS);
		StdDraw.filledCircle(x, y, CIRCLE_RADIUS);

		StdDraw.setPenColor();
		StdDraw.circle((x + left) / 2, y - yDecrement, CIRCLE_RADIUS);
		StdDraw.circle((x + right) / 2, y - yDecrement, CIRCLE_RADIUS);
		StdDraw.circle(x, y, CIRCLE_RADIUS);

		if (node == model.getCurrentNode()) {
			StdDraw.setPenColor(255, 0, 0);
			StdDraw.filledCircle(x, y, CIRCLE_RADIUS);
			StdDraw.setPenColor();
			StdDraw.circle(x, y, CIRCLE_RADIUS);
		}

		if (!node.getLeft().isLeaf()) {
			drawSubtree(node.getLeft(), y - yDecrement, yDecrement, left, x);
		} else if (node.getLeft() == model.getCurrentNode()) {
			StdDraw.setPenColor(255, 0, 0);
			StdDraw.filledCircle((x + left) / 2, y - yDecrement, CIRCLE_RADIUS);
			StdDraw.setPenColor();
			StdDraw.circle((x + left) / 2, y - yDecrement, CIRCLE_RADIUS);
		}
		
		if (!node.getRight().isLeaf()) {
			drawSubtree(node.getRight(), y - yDecrement, yDecrement, x, right);
		} else if (node.getRight() == model.getCurrentNode()) {
			StdDraw.setPenColor(255, 0, 0);
			StdDraw.filledCircle((x + right) / 2, y - yDecrement, CIRCLE_RADIUS);
			StdDraw.setPenColor();
			StdDraw.circle((x + right) / 2, y - yDecrement, CIRCLE_RADIUS);
		}
	}

	/**
	 * Reads a String from the user, displaying it as they type and allowing
	 * backspaces.
	 */
	public String readString() {
		while (true) {
			draw();
			char pressed = waitForKey();
			if (pressed == '\n') {
				String result = textBeingEntered;
				textBeingEntered = "";
				return result;
			} else if (pressed == '\b') {
				textBeingEntered = textBeingEntered.substring(0,
						textBeingEntered.length() - 1);
			} else {
				textBeingEntered += pressed;
			}
		}
	}

	/** Plays games until the user chooses to quit. */
	public void run() {
		while (true) {
			// Descend through the tree to a leaf
			model.reset();
			while (!model.atLeaf()) {
				label = model.getQuestion();
				draw();
				model.descend(waitForKey() == 'y');
			}
			// Now node is a leaf; handle end of game
			label = model.getQuestion();
			draw();
			if (waitForKey() == 'y') {
				label = "I win! Would you like to play again? (y/n)";
			} else {
				label = "I give up! What was it?";
				String correct = readString();
				label = model.getLearningQuestion(correct);
				String question = readString();
				model.learn(correct, question);
				label = "Would you like to play again? (y/n)";
			}
			draw();
			if (waitForKey() != 'y') {
				System.exit(0);
			}
		}
	}

	/** Waits for the user to press a key, then returns that character. */
	public char waitForKey() {
		while (!StdDraw.hasNextKeyTyped()) {
			// Wait for keypress
		}
		return StdDraw.nextKeyTyped();
	}

}
