package projetFractales;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FractaleMandelbrot extends JFrame implements iFractale {

	private int iterations;

	private double offsetX; // décalage en x et y
	private double offsetY;

	private double centrage; // décalage pour centrer la fractale

	private double echelle; // zoom (rapport entre le plan complexe et les coordonnées )

	private final int nbThread = 8;

	ArrayList<Thread> threadList;
	
    public FractaleMandelbrot(int iterations, double zoom) {
		this.iterations = iterations;
		threadList = new ArrayList<Thread>(Modele.largeurVue);

		this.echelle = 200;

		offsetX = 0;
		offsetY = 0;
    }

    public BufferedImage modelisation(double facteur, int offsetX, int offsetY, int iterations) {

		BufferedImage image = new BufferedImage(Modele.largeurVue, Modele.hauteurVue, BufferedImage.TYPE_INT_RGB);
		echelle *= facteur;

		this.offsetX += offsetX/echelle;
		this.offsetY += offsetY/echelle;
		
		this.iterations = iterations;

		centrage = (Modele.largeurVue/2)/echelle; // zoom sur le centre

		for(int i = 0; i<Modele.largeurVue /(Modele.largeurVue /nbThread); i++) {
		     threadCouleur t = new threadCouleur(i, image); // créée l'executable du thread
		     threadList.add(i, new Thread(t)); 				// ajoute à l'indice i un thread
		     threadList.get(i).start();		   				// lance le thread
		}
		
		for(int i = 0; i<Modele.largeurVue /(Modele.largeurVue /nbThread); i++) { // attend la fin de tous les threads
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
		}

		return image;
	}
    
	private int couleurPixel(int x, int y) {
		// La classe complexe ralentis les calculs (de l'ordre de 10* plus lent)
		double cReel = x / echelle + this.offsetX - centrage; // le c dans la formule de la suite zn+1 = z²+c
		double cImage = y / echelle + this.offsetY - centrage;

		double zReel = 0; // variable pour le calcul de la valeur de la suite Zn+1 = z²+c
		double zImage = 0;

		int it = iterations;
        while (zReel*zReel+zImage*zImage<4 && it > 0) { // (a+ib)² < 4
			double tmp = zReel;
			zReel = zReel * zReel - zImage * zImage + cReel;
			zImage = 2 * zImage * tmp + cImage;
            it--;
        }
        if(it != 0)
			return (it+200)%360; // %360 c'est le nombre de valeur que les couleurs peuvent prendre en HSB
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
			for(int it = (i*(Modele.largeurVue /nbThread)); it<(i+1)*(Modele.largeurVue /nbThread); it++) { // gère 100 colonnes
				for(int j = 0; j<Modele.hauteurVue; j++) {
					image.setRGB(it, j, Color.getHSBColor(50, 100, couleurPixel( it, j)).getRGB());
				}
			}
		}
	}

	public String toString() {
		return "offSetX : " + offsetX + " offSetY : " + offsetY;
	}

	public int getIterations() {
		return iterations;
	}
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
}
