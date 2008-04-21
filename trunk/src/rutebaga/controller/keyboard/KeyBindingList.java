package rutebaga.controller.keyboard;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class KeyBindingList<C> implements Iterable<KeyBinding<C>> {
	private KeyBinding<C>[] bindings;
	private int bindingCount;
	
	public KeyBindingList() {
		bindings = (KeyBinding<C>[]) new KeyBinding[255];
	}
	
	private void ensureCapacity(int size) {
		if (size > bindings.length) {
			KeyBinding<C>[] newCommands = (KeyBinding<C>[]) new KeyBinding[size];
			System.arraycopy(bindings, 0, newCommands, 0, bindings.length);
			bindings = newCommands;
		}
	}
	
	private boolean validIndex(int index) {
		return index >= 0 && index < bindings.length;
	}
	
	public void set(KeyBinding<C> binding) {
		if (get(binding.getKeyCode()) == null)
			++bindingCount;
		int code = binding.getKeyCode().getKeyCode();
		ensureCapacity(code);
		bindings[code] = binding;
	}
	
	public void set(KeyCode code, C binding) {
		set(new ConcreteKeyBinding<C>(code, binding));
	}
	
	public KeyBinding<C> get(KeyCode code) {
		return validIndex(code.getKeyCode()) ? bindings[code.getKeyCode()] : null;
	}
	
	public void remove(KeyBinding<C> binding) {
		remove(binding.getKeyCode());
	}
	
	public void remove(KeyCode code) {
		if (code != null && validIndex(code.getKeyCode())) {
			if (bindings[code.getKeyCode()] != null)
				--bindingCount;
			bindings[code.getKeyCode()] = null;
		}
	}
	
	public int size() {
		return bindingCount;
	}
	
	public Iterator<KeyBinding<C>> iterator() {
		return new IteratorImpl();
	}
	
	private class IteratorImpl implements Iterator<KeyBinding<C>>{

		private int index;
		private KeyBinding<C> nextBinding;
		
		public IteratorImpl() {
			index = 0;
			nextBinding = bindings[index];
		}
		
		public boolean hasNext() {
			if (nextBinding != null)
				return true;
			while (index < bindings.length && nextBinding == null)
				nextBinding = bindings[++index];
			return nextBinding != null;
		}

		public KeyBinding<C> next() {
			if (nextBinding == null)
				throw new NoSuchElementException();
			KeyBinding<C> binding = nextBinding;
			nextBinding = null;
			hasNext();
			return binding;
		}

		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
	}
}
