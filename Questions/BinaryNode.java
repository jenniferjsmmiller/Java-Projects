public class BinaryNode {

	private String name;
	private BinaryNode left;
	private BinaryNode right;
	
	public BinaryNode(String string) {
		name = string;
		left = null;
		right = null;
	}

	public BinaryNode(String string, BinaryNode binaryNode,
			BinaryNode binaryNode2) {
		name = string;
		left = binaryNode;
		right = binaryNode2;
	}

	public void setLeft(BinaryNode b) {
		left = b;
	}

	public void setRight(BinaryNode d) {
		right = d;
	}

	public BinaryNode getLeft() {
		return left;
	}

	public BinaryNode getRight() {
		return right;
	}

	public boolean isLeaf() {
		return left == null && right == null;
	}

	public int height() {
		if (this.isLeaf()) {
			return 0;
		} else {
			if (this.left.height() > this.right.height()) {
				return this.left.height() + 1;
			} else {
				return this.right.height() + 1;
			}
		}
	}

	public void learn(String string, String string2) {
		left = new BinaryNode(string);
		right = new BinaryNode(name);
		this.name = string2;

	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String indent) {
		if (isLeaf()) {
			return indent + name + "\n";
		} else {
			return indent + name + "\n" + left.toString(indent + "  ") + right.toString(indent + "  ");
		}
	}
	
	public String getKey() {
		return name;
	}
}
