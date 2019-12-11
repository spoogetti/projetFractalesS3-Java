package projetFractales;

// 11/11/2017
public class Complexe {
	private double reel;
	private double imaginaire;
	
	// getter & setter
	public double getR() {
		return reel;
	}
	
	public double getI() {
		return imaginaire;
	}
	
	public Complexe (double reel, double imaginaire) {
		this.reel = reel;
		this.imaginaire = imaginaire;
	}
	
	// addition classique des parties réelles et imaginairesl
	public Complexe plus(Complexe c) {
		return new Complexe(reel + c.reel, imaginaire + c.imaginaire);
	}
	
	// soustraction classique des parties réelles et imaginaires
	public Complexe moins(Complexe c) {
		return new Complexe(reel - c.reel , imaginaire - c.imaginaire);
	}
		
	//	multiplication :
	//		a+ai * b+bi
	//		partie réelle : 
	//		a.re * b.re - a.im * b.im
	//		partie imaginaire :
	//		a.re * b.im + a.im * b.re
		
	public Complexe fois(Complexe c) {
		double partieReelle 	= reel * c.reel - imaginaire * c.imaginaire;
		double partieImaginaire = imaginaire * c.reel + reel * c.imaginaire;
		return new Complexe(partieReelle, partieImaginaire);
	}
	
	public Complexe puissance(int puissance) {
		Complexe total = new Complexe(reel, imaginaire);
		for(int i=0; i<puissance-1; i++) {
			total = total.fois(this);
		}
		return total;
	}

	public Complexe divise(Complexe c) {
		double partieReelle = (c.reel*reel + c.imaginaire*imaginaire);
		double partieImaginaire = (c.imaginaire*reel - (c.reel*imaginaire));
		double denominateur = (reel*reel+imaginaire*imaginaire);
		return new Complexe(partieReelle/denominateur, partieImaginaire/denominateur);
	}

	// sans renvoyer de complexe on gagne du temps (moins d'objets créés)

	// addition des parties réelles et imaginaires
	public void autoPlus(Complexe c) {
		this.reel += c.reel;
		this.imaginaire += c.reel;
	}

	// soustraction des parties réelles et imaginaires
	public void autoMoins(Complexe c) {
		this.reel -= c.reel;
		this.imaginaire -= c.imaginaire;
	}

	// multiplication sans création d'objets
	public void autoFois(Complexe c) {
		double cloneReel = this.reel;
		this.reel 		= reel * c.reel - imaginaire * c.imaginaire;
		this.imaginaire = imaginaire * c.reel + cloneReel * c.imaginaire;
	}

	public void autoPuissance(int puissance) {
		Complexe clone = this.clone();
		for(int i=0; i<puissance-1; i++) {
			this.autoFois(clone);
		}
	}

	// division sans creation d'objets
	public void autoDivise(Complexe c) {
		double partieReelle = (c.reel*reel + c.imaginaire*imaginaire);
		double partieImaginaire = (c.imaginaire*reel - (c.reel*imaginaire));
		double denominateur = (reel*reel+imaginaire*imaginaire);
		this.reel = partieReelle/denominateur;
		this.imaginaire = partieImaginaire/denominateur;
	}

	public double absolut() {
		return Math.hypot(reel, imaginaire); 
	}
	
	public String toString() {
		return reel + " + i" + imaginaire;
	}

	public void setComplexe(double a, double b) {
		this.reel = a;
		this.imaginaire = b;
	}

	public Complexe clone() {
		return new Complexe(this.reel, this.imaginaire);
	}
}
