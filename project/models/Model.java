package project.models;

import project.views.Vue;
import project.models.fractals.MandelbrotFractal;
import project.models.fractals.NewtonFractal;
import project.models.fractals.PythagorasFractal;

import java.awt.image.BufferedImage;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Model extends Observable {
	private final NewtonFractal fNewton;
	private final MandelbrotFractal fMandelbrot;
	private final PythagorasFractal fPythagoras;
	private BufferedImage image;

	public static final int screenWidth = 1000;
	public static final int screenHeight = 800;

	public static final int viewWidth = 800;
	public static final int viewHeight = 800;

	public Model() {
		fNewton = new NewtonFractal( 50, 1);
		fMandelbrot = new MandelbrotFractal(200, 1);
		fPythagoras = new PythagorasFractal();
		image = fMandelbrot.render(1, 0, 0 , 300); // zoom *1 offset 0, 0
	}

	public BufferedImage modeledFractal() {
		return image;
	}

	public void rerender(double factor, int offsetX, int offsetY, int iterations) {
		switch (Vue.fractal) {
			case NEWTON -> image = fNewton.render(factor, offsetX, offsetY, iterations); // facteur de zoom, décalage de la souris
			case MANDELBROT -> image = fMandelbrot.render(factor, offsetX, offsetY, iterations);
			case PYTHAGORAS -> image = fPythagoras.render(factor, offsetX, offsetY, iterations);
		}
		setChanged();
		notifyObservers();
	}

	public int getIterations(){
		return switch (Vue.fractal) {
			case NEWTON -> fNewton.getIterations();
			case MANDELBROT -> fMandelbrot.getIterations();
			case PYTHAGORAS -> fPythagoras.getIterations();
		};
	}
}
