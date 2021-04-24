package project.models.fractals;

public enum ModelledFractal {
	MANDELBROT("Mandelbrot"),
	NEWTON_BIG_D("Newton Big D"),
	NEWTON_DBL("Newton DBL"),
	PYTHAGORAS("Pythagoras");

	public final String label;

	ModelledFractal(String label) {
		this.label = label;
	}
}
