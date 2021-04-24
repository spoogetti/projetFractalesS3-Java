package project.models.fractals.newton;

import project.models.Model;
import project.models.fractals.iFractal;
import project.models.maths.ComplexNumberBigD;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class NewtonFractalBigD extends Thread implements iFractal {
	private int iterations;
	private final BigDecimal precision = new BigDecimal("0.1");
	private double zoom;

	private final BigDecimal centeringV1 = new BigDecimal(-1);
	private final BigDecimal centeringV2 = new BigDecimal(2);

	private BigDecimal offsetX = BigDecimal.ZERO;
	private BigDecimal offsetY = BigDecimal.ZERO;

	private final int nbThread = 1;

	// Initialization of the roots for the calculations
	private final ComplexNumberBigD four = new ComplexNumberBigD(new BigDecimal(4), new BigDecimal(0));
	private final ComplexNumberBigD one = new ComplexNumberBigD(new BigDecimal(1), new BigDecimal(0));

	private final ComplexNumberBigD root1 = new ComplexNumberBigD(new BigDecimal(1), new BigDecimal(0));
	private final ComplexNumberBigD root2 = new ComplexNumberBigD(new BigDecimal(-1), new BigDecimal(0));
	private final ComplexNumberBigD root3 = new ComplexNumberBigD(new BigDecimal(0), new BigDecimal(1));
	private final ComplexNumberBigD root4 = new ComplexNumberBigD(new BigDecimal(0), new BigDecimal(-1));

	ArrayList<Thread> threadList;

	public NewtonFractalBigD(int iterations, double zoom) {
		this.iterations = iterations;
		this.zoom = zoom;
		threadList = new ArrayList<>();
	}

	public BufferedImage render(double factor, int offsetX, int offsetY, int iterations) {
		zoom = zoom * factor; // This zoom may slow down calculations
		
		this.offsetX = this.offsetX.add(BigDecimal.valueOf(offsetX));
		this.offsetY = this.offsetY.add(BigDecimal.valueOf(offsetY));
		
		this.iterations = iterations;
		
		iterationsPerZoom(factor);
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

	class colorThread implements Runnable {
		int i;
		BufferedImage image;

		colorThread(int i, BufferedImage image) {
			this.i = i;
			this.image = image;
		}

		public void run() {
			long startTime = System.currentTimeMillis();

			ComplexNumberBigD coordPixel = new ComplexNumberBigD(BigDecimal.ZERO, BigDecimal.ZERO);

			BigDecimal xStart = BigDecimal.valueOf(i * (Model.viewWidth / nbThread));
			BigDecimal xEnd = BigDecimal.valueOf((i+1) * (Model.viewWidth / nbThread));

			BigDecimal yStart = BigDecimal.ZERO;
			BigDecimal yEnd = BigDecimal.valueOf(Model.viewHeight);

			BigDecimal bigZoom = BigDecimal.valueOf(zoom);

			BigDecimal bigViewWidth = BigDecimal.valueOf(Model.viewWidth);
			BigDecimal bigViewWidthWithZoom = bigViewWidth.multiply(bigZoom);

			BigDecimal bigViewHeight = BigDecimal.valueOf(Model.viewHeight);
			BigDecimal bigViewHeightWithZoom = bigViewHeight.multiply(bigZoom);

			long setupTime = System.currentTimeMillis();
			System.out.println("Total setup time: " + (setupTime-startTime) + "ms");

			for(BigDecimal x = xStart; x.compareTo(xEnd) < 0; x = x.add(BigDecimal.ONE)) {
				BigDecimal realPart = centeringV1.add(offsetX.add(x).multiply(centeringV2))
						.divide(bigViewWidthWithZoom, RoundingMode.HALF_UP);

				startTime = System.currentTimeMillis();

				for(BigDecimal y = yStart; y.compareTo(yEnd) < 0; y = y.add(BigDecimal.ONE)) {

					BigDecimal imaginaryPart = centeringV1.add(offsetY.add(y).multiply(centeringV2))
							.divide(bigViewHeightWithZoom, RoundingMode.HALF_UP);

					coordPixel.setComplex(realPart, imaginaryPart);


					image.setRGB(x.intValue(), y.intValue(), Color.getHSBColor(50, 100, pixelColor(coordPixel)).getRGB());
				}

				long loopTime = System.currentTimeMillis();
				System.out.println("Total big loop " + x +  " time: " + (loopTime-startTime) + "ms");
			}

		}
	}

	private int pixelColor(ComplexNumberBigD c) {
		if(c.getI().equals(BigDecimal.ZERO) && c.getR().equals(BigDecimal.ZERO))
			return 0;

		boolean converge = false;
		int i = iterations;

		while(i > 0 && !converge) {
			ComplexNumberBigD baseFunction = c.clone(); // c^4-1
			baseFunction.selfPow(4);
			baseFunction.selfSubstract(one);

			ComplexNumberBigD derivative = c.clone(); // c^3
			derivative.selfPow(3);

			// derivative
			derivative.selfMul(four); // 4*c^3

			// function / derivative
			// c^4-1 / 4*c^3
			derivative.selfDiv(baseFunction);

			c.selfSubstract(derivative); // c = c - c^4-1 / 4*c^3

			converge =	c.minus(root1).abs().compareTo(precision) <= 0 ||
					    c.minus(root2).abs().compareTo(precision) <= 0 ||
					    c.minus(root3).abs().compareTo(precision) <= 0 ||
					    c.minus(root4).abs().compareTo(precision) <= 0;

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
