package project.models.fractals;

import project.models.Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.Thread;
import java.util.Random;

public class PythagorasFractal extends Thread implements iFractal {

	private int iterations = 8;
	private double scale = 1;
	private int offsetX;
	private int offsetY;

	public BufferedImage render(double factor, int offsetX, int offsetY, int iterations) {

		scale *= factor;

		this.offsetX += offsetX/ scale;
		this.offsetY += offsetY/ scale;

		this.iterations = iterations;
		
		BufferedImage image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = (Graphics2D) image.getGraphics();

		double side = 120 * scale;

		int x = Model.viewWidth / 2 - (int) side / 2;
		int y = Model.viewHeight;

		drawTree(x-this.offsetX, y-this.offsetY, x + (int) side - this.offsetX, y - this.offsetY, (int) side, g2d, iterations);

		return image;
	}

	//    5
	//   / \
	//  1---2
	//  |   |
	//  3---4
	// With the points 3 and 4 and a size we calculate the whole tree

	public void drawTree(int x1, int y1, int x2, int y2, int size, Graphics2D g2d, int iterations) {

		int dx = x2 - x1;
		int dy = y1 - y2;

		Point three = new Point(x1, y1);
		Point four = new Point(x2, y2);

		int x3 = x2 - dy; // transformation point 4 en point 2
		int y3 = y2 - dx;

		int x4 = x1 - dy; // transformation point 3 en point 1
		int y4 = y1 - dx;

		Point one = new Point(x4, y4);
		Point two = new Point(x3, y3);

		int x5 = (int) (x4 + 0.5 * (dx - dy));
		int y5 = (int) (y4 - 0.5 * (dx + dy));

		Point triangle = new Point(x5, y5);

		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());

		g2d.setColor(Color.getHSBColor(rnd.nextInt() % 360, rnd.nextInt() % 100, rnd.nextInt() % 100));
		g2d.drawLine(three.x, three.y, four.x, four.y); // 3---4

		g2d.setColor(Color.getHSBColor(rnd.nextInt() % 360, rnd.nextInt() % 100, rnd.nextInt() % 100));
		g2d.drawLine(three.x, three.y, one.x, one.y);   // 3|1

		g2d.setColor(Color.getHSBColor(rnd.nextInt() % 360, rnd.nextInt() % 100, rnd.nextInt() % 100));
		g2d.drawLine(one.x, one.y, two.x, two.y);       // 1---2

		g2d.setColor(Color.getHSBColor(rnd.nextInt() % 360, rnd.nextInt() % 100, rnd.nextInt() % 100));
		g2d.drawLine(four.x, four.y, two.x, two.y);     // 4|2

		iterations--;

		if (iterations > 0) {
			drawTree(one.x, one.y, triangle.x, triangle.y, (int) (size * 0.5 * Math.sqrt(2)), g2d, iterations);
			drawTree(triangle.x, triangle.y, two.x, two.y, (int) (size * 0.5 * Math.sqrt(2)), g2d, iterations);
		}
	}

	public int getIterations() {
		return iterations;
	}
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
}
