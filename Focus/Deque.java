import java.util.Iterator;

public class Deque<T> implements Iterable<T> {

	private ListNode<T> front;
	private ListNode<T> back;
	private int size;
	
	public Deque() {
		front = null;
		back = front;
		size = 0;
	}
	
	public void addFront(T i) {
		if (front == null) {
			ListNode<T> node = new ListNode<T> (i, null);
			front = node;
			back = front;
		} else {
			ListNode<T> node = new ListNode<T> (i, null);
			node.setNext(front);
			front = node;
		}
		size++;
	}
	
	public void addBack(T i) {
		if (isEmpty() == true) {
			ListNode<T> n = new ListNode<T>(i, null);
			front = n;
			back = n;
		} else {
			ListNode<T> n = new ListNode<T>(i, null);
			back.setNext(n);
			back = n;
		}
		size++;
	}

	public T removeFront() {
		T thing = front.getKey();
		if (size == 1) {
			front = null;
			back = null;
		} else {
			front = front.getNext();
		}
		size--;
		return thing;
	}

	public T removeBack() {
		T thing = back.getKey();
		if (size == 1) {
			front = null;
			back = null;
		} else {
			ListNode<T> node = front;
			while (node.getNext() != back) {
				node = node.getNext();
			}
			back = node;
			back.setNext(null);
		}
		size--;
		return thing;
	}

	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public String toString() {
		if (isEmpty()) {
			return "<>";
		} else {
			String stringToReturn = "<";
			ListNode<T> tracker = front;
			while (tracker != back) {
				T k = tracker.getKey();
				stringToReturn += k + ", ";
				tracker = tracker.getNext();
			}
			stringToReturn += tracker.getKey();
			return stringToReturn + ">";
		}
	}
	
	public Iterator<T> iterator() {
		return new DequeIterator<T>(front);
	}

}
