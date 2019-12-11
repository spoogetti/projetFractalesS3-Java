package projetFractales;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.Thread;
import java.util.ArrayList;

public class FractaleNewton extends Thread implements iFractale  {
	private int iterations;
	private final double precision = 0.1;
	private double zoom;

	private final double centrageV1 = -1;
	private final double centrageV2 = 2;

	private double offsetX = 0;
	private double offsetY = 0;

	private final int nbThread = 8;

	// Pour les racines et les calculs
	private Complexe quatre = new Complexe(4, 0);
	private Complexe un  = new Complexe(1, 0);

	private Complexe racine1 = new Complexe(1, 0);
	private Complexe racine2 = new Complexe(-1, 0);
	private Complexe racine3 = new Complexe(0, 1);
	private Complexe racine4 = new Complexe(0, -1);

	private Complexe fonction;
	private Complexe derivee;

	ArrayList<Thread> threadList;

	public FractaleNewton(int iterations, double zoom) {
		this.iterations = iterations;
		this.zoom = zoom;
		threadList = new ArrayList<Thread>(Modele.largeurVue);
	}

	public BufferedImage modelisation(double facteur, int offsetX, int offsetY, int iterations) {
		zoom = zoom * facteur; // le zoom ralentis un peu trop les temps de calcul
		
		this.offsetX += offsetX;
		this.offsetY += offsetY;
		
		this.iterations = iterations;
		
		iterationsParZoom(facteur);
		
		BufferedImage image = new BufferedImage(Modele.largeurVue, Modele.hauteurVue, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i<Modele.largeurVue /(Modele.largeurVue /nbThread); i++) { // un thread pour 100 lignes (à généraliser pour n thread et comparer les temps)
			threadCouleur t = new threadCouleur(i, image); // créée l'executable du thread
			threadList.add(i, new Thread(t)); // ajoute à l'indice i un thread
			threadList.get(i).start();		   // lance le thread
		}

		for(int i = 0; i<Modele.largeurVue /(Modele.largeurVue /nbThread); i++) { // attend la fin de tous les threads
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				System.out.println("Interuption inatendue d'un thread");
			}
		}

		return image;
	}

	private void iterationsParZoom(double facteur) {
		if(facteur>1)
			iterations *= (1 + facteur/10);
		if(facteur<1)
			iterations *= (1 - facteur/10);
	}
	
	private int couleurPixel(Complexe c) {
		Boolean convergee = false;
		int i = iterations;

		while(i > 0 && !convergee) {
			fonction = c.clone(); // c^4-1
			fonction.autoPuissance(4);
			fonction.autoMoins(un);

			Complexe cPuissance3 = c.clone(); // c^3
			cPuissance3.autoPuissance(3);

			derivee = quatre.clone();
			derivee.autoFois(cPuissance3); // 4*c^3

			Complexe deriveeDiviseFonction = derivee.clone(); // c^4-1 / 4*c^3
			deriveeDiviseFonction.autoDivise(fonction);

			c.autoMoins(deriveeDiviseFonction); // c = c - c^4-1 / 4*c^3
			
			convergee =	c.moins(racine1).absolut() <= precision ||
					    c.moins(racine2).absolut() <= precision ||
					    c.moins(racine3).absolut() <= precision ||
					    c.moins(racine4).absolut() <= precision;
		i--;
		}

		if(convergee)
			return i%360;
		return 0;
	}

	class threadCouleur implements Runnable {
		int i;
		BufferedImage image;

		threadCouleur(int i, BufferedImage image) {
			this.i = i;
			this.image = image;
		}

		public void run() {
			Complexe coordPixel = new Complexe(0, 0);
			for(int it = i*(Modele.largeurVue /nbThread); it<(i+1)*(Modele.largeurVue /nbThread); it++) {
				for(int j = 0; j<Modele.hauteurVue; j++) {
					coordPixel.setComplexe((centrageV1 + ((offsetX + it) * centrageV2)  / Modele.largeurVue)/zoom,
							               (centrageV1 + ((offsetY + j ) * centrageV2) / Modele.hauteurVue)/zoom);
					image.setRGB(it, j, Color.getHSBColor(50, 100, couleurPixel(coordPixel)).getRGB());
				}
			}
		}
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
}
