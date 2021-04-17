package project.models.fractals;

import project.models.Model;
import project.models.maths.ComplexNumber;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.Thread;
import java.util.ArrayList;

public class NewtonFractal extends Thread implements iFractal {
	private int iterations;
	private final double precision = 0.1;
	private double zoom;

	private final double centeringV1 = -1;
	private final double centeringV2 = 2;

	private double offsetX = 0;
	private double offsetY = 0;

	private final int nbThread = 8;

	// Initialization of the roots for the calculations
	private ComplexNumber four = new ComplexNumber(4, 0);
	private ComplexNumber one = new ComplexNumber(1, 0);

	private ComplexNumber root1 = new ComplexNumber(1, 0);
	private ComplexNumber root2 = new ComplexNumber(-1, 0);
	private ComplexNumber root3 = new ComplexNumber(0, 1);
	private ComplexNumber root4 = new ComplexNumber(0, -1);

	private ComplexNumber baseFunction;
	private ComplexNumber derivative;

	ArrayList<Thread> threadList;

	public NewtonFractal(int iterations, double zoom) {
		this.iterations = iterations;
		this.zoom = zoom;
		threadList = new ArrayList<>(Model.viewWidth);
	}

	public BufferedImage render(double factor, int offsetX, int offsetY, int iterations) {
		zoom = zoom * factor; // This zoom may slow down
		
		this.offsetX += offsetX;
		this.offsetY += offsetY;
		
		this.iterations = iterations;
		
		iterationsParZoom(factor);
		
		BufferedImage image = new BufferedImage(Model.viewWidth, Model.viewHeight, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < Model.viewWidth / (Model.viewWidth / nbThread); i++) { // one thread for a hundred lines
			colorThread t = new colorThread(i, image);
			threadList.add(i, new Thread(t));
			threadList.get(i).start();
		}

		for(int i = 0; i< Model.viewWidth /(Model.viewWidth /nbThread); i++) { // wait for the end of all threads
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				System.out.println("Unexpected thread interruption: " + e);
			}
		}

		return image;
	}

	private void iterationsParZoom(double factor) {
		if(factor>=1)
			iterations *= (1 + factor/10);
		if(factor<1)
			iterations *= (1 - factor/10);
	}
	
	private int pixelColor(ComplexNumber c) {
		boolean converge = false;
		int i = iterations;

		while(i > 0 && !converge) {
			baseFunction = c.clone(); // c^4-1
			baseFunction.selfPow(4);
			baseFunction.selfMinus(one);

			ComplexNumber cPower3 = c.clone(); // c^3
			cPower3.selfPow(3);

			derivative = four.clone();
			derivative.selfMul(cPower3); // 4*c^3

			ComplexNumber functionOverDerivative = derivative.clone(); // c^4-1 / 4*c^3
			functionOverDerivative.selfDiv(baseFunction);

			c.selfMinus(functionOverDerivative); // c = c - c^4-1 / 4*c^3
			
			converge =	c.minus(root1).abs() <= precision ||
					    c.minus(root2).abs() <= precision ||
					    c.minus(root3).abs() <= precision ||
					    c.minus(root4).abs() <= precision;

			i--;
		}

		if(converge)
			return i%360;
		return 0;
	}

	class colorThread implements Runnable {
		int i;
		BufferedImage image;

		colorThread(int i, BufferedImage image) {
			this.i = i;
			this.image = image;
		}

		public void run() {
			ComplexNumber coordPixel = new ComplexNumber(0, 0);
			for(int it = i*(Model.viewWidth /nbThread); it<(i+1)*(Model.viewWidth /nbThread); it++) {
				for(int j = 0; j< Model.viewHeight; j++) {
					coordPixel.setComplex((centeringV1 + ((offsetX + it) * centeringV2)  / Model.viewWidth)/zoom,
							               (centeringV1 + ((offsetY + j ) * centeringV2) / Model.viewHeight)/zoom);
					image.setRGB(it, j, Color.getHSBColor(50, 100, pixelColor(coordPixel)).getRGB());
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
