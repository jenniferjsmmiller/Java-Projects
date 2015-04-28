
/** Model of the Questions game. */
public class QuestionsModel {

	/** The root of the tree of knowledge. */
	public BinaryNode root;

	/** Current node in the decision tree. */
	private BinaryNode currentNode;

	/** A new Game has two questions and three leaves. */
	public QuestionsModel() {
		root = new BinaryNode("Does it have a motor?", new BinaryNode("Does it store information?", new BinaryNode("a hard drive"), new BinaryNode("a car")), new BinaryNode("a giraffe"));
		currentNode = root;
	}

	/** True if the current node is a leaf. */
	public boolean atLeaf() {
		return currentNode.isLeaf();
	}

	/** Updates the current node to the left or right child, respectively, if answer is true or false. */
	public void descend(boolean answer) {
		if (answer == true) {
			currentNode = currentNode.getLeft();
		} else {
			currentNode = currentNode.getRight();
		}
	}

	/** Returns the current node. */
	public BinaryNode getCurrentNode() {
		return currentNode;
	}

	/** Returns the question asked of the user for learning after losing a game. */
	public String getLearningQuestion(String correct) {
		correct = "What question would distinguish " + correct + " (y) from " + currentNode.getKey() + " (n)?";
		return correct;
	}
	
	/** Returns the questions asked at the current node. */
	public String getQuestion() {
		if (currentNode.isLeaf()) {
			return "Is it " + currentNode.getKey() + "? (y/n)";
		} else {
			return currentNode.getKey() + " (y/n)";
		}
	}

	/** Returns the root of this game's decision tree. */
	public BinaryNode getRoot() {
		return root;
	}

	/**
	 * Replaces the key of the current node (a leaf) with question and gives it two
	 * children. The left child's key is correct. The right child's key is
	 * this node's old key.
	 * 
	 * This is a "delegate" method that simply calls the version from Node.
	 */
	public void learn(String correct, String question) {
		currentNode.learn(correct, question);
	}

	/** Resets the current node to be the root of the decision tree. */
	public void reset() {
		currentNode = root;
	}

	@Override
	public String toString() {
		return root.toString();
	}

}

