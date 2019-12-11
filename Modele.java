package projetFractales;

import java.awt.image.BufferedImage;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Modele extends Observable{
	private FractaleNewton fNewton;
	private FractaleMandelbrot fMandelbrot;
	private FractalePythagore fPythagore;
	private BufferedImage image;

	static final int largeurEcran = 1000;
	static final int hauteurEcran = 800;

	static final int largeurVue = 800;
	static final int hauteurVue = 800;

	public Modele() {
		fNewton = new FractaleNewton( 50, 1); // nombre d'setIterations, zoom
		fMandelbrot = new FractaleMandelbrot(200, 1); // nombre d'setIterations, zoom
		fPythagore = new FractalePythagore(); // nombre d'setIterations, zoom
		image = fMandelbrot.modelisation(1, 0, 0 , 300); // zoom *1 offset 0, 0
	}

	public BufferedImage fractaleModelisee() {
		return image;
	}

	public void remodelise(double facteur, int offsetX, int offsetY, int iterations) {
		switch(Vue.fractale) {
		case NEWTON :
			image = fNewton.modelisation(facteur, offsetX, offsetY, iterations); // facteur de zoom, décalage de la souris
			break;
		case MANDELBROT :
			image = fMandelbrot.modelisation(facteur, offsetX, offsetY, iterations);
			break;
		case PYTHAGORE:
			image = fPythagore.modelisation(facteur, offsetX, offsetY, iterations);
			break;
		}
		setChanged();
		notifyObservers();
	}

	public int getIterations(){
		int iterations = 0;
		switch(Vue.fractale) {
			case NEWTON :
				iterations = fNewton.getIterations();
				break;
			case MANDELBROT :
				iterations = fMandelbrot.getIterations();
				break;
			case PYTHAGORE:
				iterations = fPythagore.getIterations();
				break;
		}
		return iterations;
	}
}
