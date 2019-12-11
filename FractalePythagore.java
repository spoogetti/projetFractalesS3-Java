package projetFractales;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.Thread;
import java.util.Random;

public class FractalePythagore extends Thread implements iFractale {

	private int iterations = 8;
	private double cote;
	private double echelle = 1;
	private int offsetX;
	private int offsetY;

	public BufferedImage modelisation(double facteur, int offsetX, int offsetY, int iterations) {

		echelle *= facteur;

		this.offsetX += offsetX/echelle;
		this.offsetY += offsetY/echelle;

		this.iterations = iterations;
		
		BufferedImage image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = (Graphics2D) image.getGraphics();

		cote = 120 * echelle;

		int x = Modele.largeurVue / 2 - (int)cote / 2;
		int y = Modele.hauteurVue;

		dessinerArbre(x-this.offsetX, y-this.offsetY, x + (int)cote - this.offsetX, y - this.offsetY, (int)cote, g2d, iterations);

		return image;
	}

	//    5
	//   / \
	//  1---2
	//  |   |
	//  3---4
	// Avec les points 3 et 4 et une taille on calcule tout l'arbre

	public void dessinerArbre(int x1, int y1, int x2, int y2, int taille, Graphics2D g2d, int iterations) {

		int dx = x2 - x1;
		int dy = y1 - y2;

		Point trois = new Point(x1, y1);
		Point quatre = new Point(x2, y2);

		int x3 = x2 - dy; // transformation point 4 en point 2
		int y3 = y2 - dx;

		int x4 = x1 - dy; // transformation point 3 en point 1
		int y4 = y1 - dx;

		Point un = new Point(x4, y4);
		Point deux = new Point(x3, y3);

		int x5 = (int) (x4 + 0.5 * (dx - dy));
		int y5 = (int) (y4 - 0.5 * (dx + dy));

		Point triangle = new Point(x5, y5);

		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());

		g2d.setColor(Color.getHSBColor(rnd.nextInt() % 360, rnd.nextInt() % 100, rnd.nextInt() % 100));
		g2d.drawLine(trois.x, trois.y, quatre.x, quatre.y); // 3---4

		g2d.setColor(Color.getHSBColor(rnd.nextInt() % 360, rnd.nextInt() % 100, rnd.nextInt() % 100));
		g2d.drawLine(trois.x, trois.y, un.x, un.y);         // 3|1

		g2d.setColor(Color.getHSBColor(rnd.nextInt() % 360, rnd.nextInt() % 100, rnd.nextInt() % 100));
		g2d.drawLine(un.x, un.y, deux.x, deux.y);           // 1---2

		g2d.setColor(Color.getHSBColor(rnd.nextInt() % 360, rnd.nextInt() % 100, rnd.nextInt() % 100));
		g2d.drawLine(quatre.x, quatre.y, deux.x, deux.y);   // 4|2

		iterations--;

		if (iterations > 0) {
			dessinerArbre(un.x, un.y, triangle.x, triangle.y, (int) (taille * 0.5 * Math.sqrt(2)), g2d, iterations);
			dessinerArbre(triangle.x, triangle.y, deux.x, deux.y, (int) (taille * 0.5 * Math.sqrt(2)), g2d, iterations);
		}
	}

	public int getIterations() {
		return iterations;
	}
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
}
