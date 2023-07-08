package fr.gwendal_jouneaux.rob_lang.interpreter.runtime;

import java.util.ArrayList;
import java.util.List;

public class RuntimeArray {
	
	List<Object> elements;
	
	public RuntimeArray(int dimesion) {
		this.elements = new ArrayList<>();
	}

	public int length() {
		return elements.size();
	}
	
	public void set(int index, Object elem) {
		elements.set(index, elem);
	}
	
	public Object get(int index) {
		return elements.get(index);
	}
	
	public void add(Object elem) {
		elements.add(elem);
	}
	
	public void add(int index, Object elem) {
		elements.add(index, elem);
	}
	
	public void remove(int index) {
		elements.remove(index);
	}
}
