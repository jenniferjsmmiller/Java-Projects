import java.util.Iterator;

public class DequeIterator<T> implements Iterator<T> {
	
	ListNode<T> current;
	
	public DequeIterator(ListNode<T> current) {
		this.current = current;
	}

	public boolean hasNext() {
		return (current != null);
	}

	public T next() {
		if (hasNext()) {
			ListNode<T> temp = current;
			current = current.getNext();
			return temp.getKey();
		} else {
			return current.getKey();
		}
	}

}
