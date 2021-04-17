package project.models.fractals;

import project.models.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MandelbrotFractal extends JFrame implements iFractal {

	private int iterations;

	private double offsetX;
	private double offsetY;

	private double centering;

	private double scale; // zoom (complex to x/y coordinates)

	private final int nbThread = 8;

	ArrayList<Thread> threadList;
	
    public MandelbrotFractal(int iterations, double zoom) {
		this.iterations = iterations;
		threadList = new ArrayList<>(Model.viewWidth);

		this.scale = 200;

		offsetX = 0;
		offsetY = 0;
    }

    public BufferedImage render(double factor, int offsetX, int offsetY, int iterations) {

		BufferedImage image = new BufferedImage(Model.viewWidth, Model.viewHeight, BufferedImage.TYPE_INT_RGB);
		scale *= factor;

		this.offsetX += offsetX/ scale;
		this.offsetY += offsetY/ scale;
		
		this.iterations = iterations;

		centering = (Model.viewWidth/2.)/ scale;

		for(int i = 0; i< Model.viewWidth /(Model.viewWidth /nbThread); i++) {
		     colorThread t = new colorThread(i, image);
		     threadList.add(i, new Thread(t));
		     threadList.get(i).start();
		}

		// wait for the end of all threads
		for(int i = 0; i< Model.viewWidth /(Model.viewWidth /nbThread); i++) {
			try {
				threadList.get(i).join();
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}

		return image;
	}
    
	private int colorPixel(int x, int y) {
		// raw calculations because the ComplexNumber class slow down the calculations,
		// surely because of the instantiations

		// the "c" in the formula of the zn+1 = z²+c sequence
		double cReal = x / scale + this.offsetX - centering;
		double cImaginary = y / scale + this.offsetY - centering;

		// the "z" in the previous formula
		double zReal = 0;
		double zImaginary = 0;

		int it = iterations;
        while (zReal*zReal+zImaginary*zImaginary<4 && it > 0) { // (a+ib)² < 4
			double tmp = zReal;
			zReal = zReal * zReal - zImaginary * zImaginary + cReal;
			zImaginary = 2 * zImaginary * tmp + cImaginary;
            it--;
        }
        if(it != 0)
			return (it+200)%360; // %360 this modulo is to bound the value or color to HSB values
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
			for(int it = (i*(Model.viewWidth /nbThread)); it<(i+1)*(Model.viewWidth /nbThread); it++) { // handle 100 colons
				for(int j = 0; j< Model.viewHeight; j++) {
					image.setRGB(it, j, Color.getHSBColor(50, 100, colorPixel( it, j)).getRGB());
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
