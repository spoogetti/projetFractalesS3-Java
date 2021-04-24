package project.models.fractals.newton;

import project.models.Model;
import project.models.fractals.iFractal;
import project.models.maths.ComplexNumberDbl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class NewtonFractalDbl extends Thread implements iFractal {
	private int iterations;
	private final double  precision = .1;
	private double zoom;

	private final double  centeringV1 = -1;
	private final double  centeringV2 = 2;

	private double  offsetX = 0;
	private double  offsetY = 0;

	private final int nbThread = 1;

	// Initialization of the roots for the calculations
	private final ComplexNumberDbl four = new ComplexNumberDbl(4, 0);
	private final ComplexNumberDbl one = new ComplexNumberDbl(1, 0);

	private final ComplexNumberDbl root1 = new ComplexNumberDbl(1, 0);
	private final ComplexNumberDbl root2 = new ComplexNumberDbl(-1, 0);
	private final ComplexNumberDbl root3 = new ComplexNumberDbl(0, 1);
	private final ComplexNumberDbl root4 = new ComplexNumberDbl(0, -1);

	ArrayList<Thread> threadList;

	public NewtonFractalDbl(int iterations, double zoom) {
		this.iterations = iterations;
		this.zoom = zoom;
		threadList = new ArrayList<>();
	}

	public BufferedImage render(double factor, int offsetX, int offsetY, int iterations) {
		zoom = zoom * factor; // This zoom may slow down calculations
		
		this.offsetX += offsetX;
		this.offsetY += offsetY;
		
		this.iterations = iterations;
		
		iterationsPerZoom(factor);
		BufferedImage image = new BufferedImage(Model.viewWidth, Model.viewHeight, BufferedImage.TYPE_INT_RGB);

		for(int i = 0; i < Model.viewWidth / (Model.viewWidth / nbThread); i++) { // one thread for a hundred lines
			colorThread t = new colorThread(i, image);
			threadList.add(i, new Thread(t));
			threadList.get(i).start();
		}

		for(int i = 0; i< Model.viewWidth / (Model.viewWidth /nbThread); i++) { // wait for the end of all threads
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				System.out.println("Unexpected thread interruption: " + e);
			}
		}

		return image;
	}

	class colorThread implements Runnable {
		int i;
		BufferedImage image;

		colorThread(int i, BufferedImage image) {
			this.i = i;
			this.image = image;
		}

		public void run() {
			ComplexNumberDbl coordPixel = new ComplexNumberDbl(0, 0);

			int  xStart = i * (Model.viewWidth / nbThread);
			int  xEnd = (i+1) * (Model.viewWidth / nbThread);

			int  yStart = 0;
			int  yEnd = Model.viewHeight;

			double  viewWidthWithZoom = Model.viewWidth * zoom;
			double  viewHeightWithZoom = Model.viewHeight * zoom;

			for(int x = xStart; x < xEnd; x++) {
				long startTime = System.currentTimeMillis();
				double realPart = centeringV1 + ((offsetX + x) * centeringV2) / viewWidthWithZoom;

				for(int y = yStart; y < yEnd; y++) {
					double imaginaryPart =   centeringV1 + ((offsetY + y) * centeringV2) / viewHeightWithZoom;
					coordPixel.setComplex(realPart, imaginaryPart);
					image.setRGB(x, y, Color.getHSBColor(50, 100, pixelColor(coordPixel)).getRGB());
				}

				long loopTime = System.currentTimeMillis();
				System.out.println("Total big loop " + x +  " time: " + (loopTime-startTime) + "ms");
			}

		}
	}

	private int pixelColor(ComplexNumberDbl c) {
		if(c.getI() == 0 && c.getR() == 0)
			return 0;

		boolean converge = false;
		int i = iterations;

		while(i > 0 && !converge) {
			ComplexNumberDbl baseFunction = c.clone(); // c^4-1
			baseFunction.selfPow(4);
			baseFunction.selfSubstract(one);

			ComplexNumberDbl derivative = c.clone(); // c^3
			derivative.selfPow(3);

			// derivative
			derivative.selfMul(four); // 4*c^3

			// function / derivative
			// c^4-1 / 4*c^3
			derivative.selfDiv(baseFunction);

			c.selfSubstract(derivative); // c = c - c^4-1 / 4*c^3

			converge =	c.substract(root1).abs() <= precision ||
					    c.substract(root2).abs() <= precision ||
					    c.substract(root3).abs() <= precision ||
					    c.substract(root4).abs() <= precision;

			i--;
		}

		if(converge)
			return i%360;
		return 0;
	}

	private void iterationsPerZoom(double factor) {
		if(factor>=1)
			iterations *= (1 + factor/10);
		if(factor<1)
			iterations *= (1 - factor/10);
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
}
