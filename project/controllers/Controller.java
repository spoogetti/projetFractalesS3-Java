package project.controllers;

import project.models.Model;

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
}
