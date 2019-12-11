package projetFractales;

public class Main {
	public Main() {
		Modele m = new Modele();
		Controleur c = new Controleur(m);
		Vue v = new Vue("Roubaix Champlon", c);
		m.addObserver(v);
	}

	public static void main(String [] args)
	{
		Main m = new Main();
	}
}