package projetFractales;

public class Controleur {
	private Modele modele;
	
	public Controleur(Modele modele) {
		this.modele = modele;
	}
	
	public Modele modele() {
		return modele;
	}
	
	public void zoom(double facteur) {
		this.modele.remodelise(facteur, 0, 0, modele.getIterations()); 
	}
	
	public void refresh() {
		this.modele.remodelise(1, 0, 0, modele.getIterations());
	}

	public void offset(int offsetX, int offsetY) {
		this.modele.remodelise(1, offsetX, offsetY, modele.getIterations());
	}
	
	
	public void updateIterations(int iterations)  {
		this.modele.remodelise(1, 0, 0, iterations);
	}

	public int getIterations() {
		return modele.getIterations();
	}
}
