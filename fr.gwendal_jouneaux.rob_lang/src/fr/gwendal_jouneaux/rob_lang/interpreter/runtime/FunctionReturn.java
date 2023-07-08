package fr.gwendal_jouneaux.rob_lang.interpreter.runtime;

public class FunctionReturn extends Exception {

	private static final long serialVersionUID = 3375385441438480556L;
	private Object toReturn;
	
	public FunctionReturn(Object toReturn) {
		this.toReturn = toReturn;
	}
	
	public Object toReturn() {
		return toReturn;
	}

}
