package application;

public class ExceptionContenu extends Exception {
	public ExceptionContenu(boolean robot, boolean mine) {
		super();
		if(robot)
			System.out.println("Votre robot est vide, il n'y a rien a deposer dans l'entrepot!");
		else if(mine)
			System.out.println("La mine est vide, rien ne peut etre pris");
		else
			System.out.println("Le robot est plein, rien ne peut etre pris");
	}
}
