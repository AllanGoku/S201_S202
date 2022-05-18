package application;

public class ExceptionDeplacement extends Exception {
	public ExceptionDeplacement() {
		super();
		System.out.println("Cette direction est inacessible!");
	}
}

