package project.controllers;

import project.models.Model;
import project.models.fractals.MandelbrotFractal;
import project.models.fractals.ModelledFractal;
import project.models.fractals.PythagorasFractal;
import project.models.fractals.iFractal;
import project.models.fractals.newton.NewtonFractalBigD;
import project.models.fractals.newton.NewtonFractalDbl;

public record Controller(Model model) {
	public void zoom(double factor) {
		this.model.rerender(factor, 0, 0, model.getIterations());
	}

	public void refresh() {
		this.model.rerender(1, 0, 0, model.getIterations());
	}

	public void offset(int offsetX, int offsetY) {
		this.model.rerender(1, offsetX, offsetY, model.getIterations());
	}

	public void updateIterations(int iterations) {
		this.model.rerender(1, 0, 0, iterations);
	}

	public int getIterations() {
		return model.getIterations();
	}

	public void instanciateFractal(ModelledFractal m) {
		iFractal newFractal = switch (m) {
			case NEWTON_DBL -> new NewtonFractalDbl(10, 1);
			case NEWTON_BIG_D -> new NewtonFractalBigD(10, 1);
			case MANDELBROT -> new MandelbrotFractal(10, 1);
			case PYTHAGORAS -> new PythagorasFractal();
		};
		model.setFractal(newFractal);
	}
}
