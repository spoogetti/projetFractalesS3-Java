package project.models;

import project.models.fractals.MandelbrotFractal;
import project.models.fractals.iFractal;

import java.awt.image.BufferedImage;
import java.util.Observable;

public class Model extends Observable {
	private iFractal fractal;
	private BufferedImage image;

	public static final int screenWidth = 1422;
	public static final int screenHeight = 800;

	public static final int viewWidth = 800;
	public static final int viewHeight = 800;

	public Model() {
		fractal = new MandelbrotFractal(200, 1);
		image = fractal.render(1, 0, 0 , fractal.getIterations()); // zoom *1 offset 0, 0
	}

	public BufferedImage modeledFractalImage() {
		return image;
	}

	public void rerender(double factor, int offsetX, int offsetY, int iterations) {
		image = fractal.render(factor, offsetX, offsetY, iterations);
		setChanged();
		notifyObservers();
	}

	public int getIterations(){
		return fractal.getIterations();
	}

	public void setFractal(iFractal fractal) {
		this.fractal = fractal;
	}
}
