public class ListNode<T> {

	T key;
	ListNode<T> next;
	ListNode<T> previous;
	
	public ListNode(T key, ListNode<T> next) {
		this.key = key;
		this.next = next;
	}

	public T getKey() {
		return key;
	}

	public void setKey(T k) {
		key = k;
	}

	public ListNode<T> getNext() {
		return next;
	}

	public void setNext(ListNode<T> next) {
		this.next = next;
	}

}
