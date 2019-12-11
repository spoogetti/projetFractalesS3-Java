package projetFractales;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testsComplexes {
	static Complexe un = new Complexe(1, 0);
	static Complexe deux = new Complexe(2, 0);
	static Complexe moinsDeux = new Complexe(-2, 0);
	static Complexe quatre = new Complexe(4, 0);
	static Complexe huit = new Complexe(8, 0);
	static Complexe seize = new Complexe(16, 0);
	
	static Complexe iun = new Complexe(0, 1);
	static Complexe ideux = new Complexe(0, 2); // hideux
	
	static Complexe arbitraire1 = new Complexe(1.4, 0.4);
	static Complexe arbitraire2 = new Complexe(0.8, 0.8);
	
	@Test
	public void testOperationsDeBase() {
		testAddition();
		testSoustraction();
		testMultiplication();
		testPuissance();
		testDivision();
		testValeurAbsolut();

		testAutoAddition();
		testAutoSoustraction();
		testAutoMultiplication();
		testAutoPuissance();
		testAutoDivision();
	}
	
	 // assertEquals avec des nombres flottant fonctionne de la manière suivante : résultat attendu puis resultat à tester et la différence maximale entre les deux
	public void testAddition() {
		// 1+1 = 2
		assertEquals(new Complexe(2, 0).getR(), un.plus(un).getR(), 0);
		// i+i  = 2i
		assertEquals(new Complexe(0, 3).getI(), iun.plus(ideux).getI(), 0);
	}
	
	public void testSoustraction() {
		// 2-1 = 1
		assertEquals(new Complexe(1, 0).getR(), deux.moins(un).getR(), 0);
		// 2^4-1 = 15
		assertEquals(new Complexe(15, 0).getR(), deux.puissance(4).moins(un).getR(), 0);
		// 2-8/4 = 0
		assertEquals(new Complexe(0, 0).getR(), deux.moins(quatre.divise(huit)).getR(), 0);
	}
	
	public void testMultiplication() {
		// 2*2 = 4
		assertEquals(new Complexe(4, 0).getR(), deux.fois(deux).getR(), 0); // assertEquals avec des nombres flottant fonctionne de la manière suivante : résultat attendu puis resultat à tester et la différence
	}
	
	public void testPuissance() {
		// 2^2 = 4
		assertEquals(new Complexe(4, 0).getR(), deux.puissance(2).getR(), 0);
		// 2^3 = 8
		assertEquals(new Complexe(8, 0).getR(), deux.puissance(3).getR(), 0);
		// 2^4 = 16
		assertEquals(new Complexe(16, 0).getR(), deux.puissance(4).getR(), 0);
		// i*i = -1
		assertEquals(new Complexe(-1, 0).getR(), iun.puissance(2).getR(), 0);
	}
	
	public void testDivision() {
		// 16/2 = 8
		assertEquals(new Complexe(8, 0).getR(), deux.divise(seize).getR(), 0);
		// 8/2 = 4
		assertEquals(new Complexe(4, 0).getR(), deux.divise(huit).getR(), 0);
		// 1.4+0.4i/0.8+0.8i = 1.125 - 0.652i
		assertEquals(new Complexe(1.125, -0.625).getI(), arbitraire2.divise(arbitraire1).getI(), 0.01);
		assertEquals(new Complexe(1.125, -0.625).getR(), arbitraire2.divise(arbitraire1).getR(), 0.01);
	}
	
	public void testValeurAbsolut() {
		// |-2| = 2
		assertEquals(new Complexe(2, 0).getR(), moinsDeux.absolut(), 0);
	}

	public void testAutoAddition() {
		// 1+1 = 2
		un.autoPlus(un);
		assertEquals(new Complexe(2, 0).getR(), un.getR(), 0);
		un = new Complexe(1, 0);

		// 2+2 = 4
		deux.autoPlus(deux);
		assertEquals(new Complexe(4, 0).getR(), deux.getR(), 0);
		deux = new Complexe(2, 0);

	}

	public void testAutoSoustraction() {
		// 1-1 = 0
		un.autoMoins(un);
		assertEquals(new Complexe(0, 0).getR(), un.getR(), 0);
		un = new Complexe(1, 0);

		// 2-1 = 1
		deux.autoMoins(un);
		assertEquals(new Complexe(1, 0).getR(), deux.getR(), 0);
		deux = new Complexe(2, 0);
	}

	public void testAutoMultiplication() {
		// 1*8 = 8
		un.autoFois(huit);
		assertEquals(new Complexe(8, 0).getR(), un.getR(), 0);
		un = new Complexe(1, 0);

		// 2*-2 = -4
		deux.autoFois(moinsDeux);
		assertEquals(new Complexe(-4, 0).getR(), deux.getR(), 0);
		deux = new Complexe(2, 0);
	}

	public void testAutoPuissance() {
		// 2^3 = 8
		deux.autoPuissance(3);
		assertEquals(new Complexe(8, 0).getR(), deux.getR(), 0);
		deux = new Complexe(2, 0);

		// 2^4 = 16
		deux.autoPuissance(4);
		assertEquals(new Complexe(16, 0).getR(), deux.getR(), 0);
		deux = new Complexe(2, 0);
	}

	public void testAutoDivision() {
		// 16/2 = 8
		deux.autoDivise(seize);
		assertEquals(new Complexe(8, 0).getR(), deux.getR(), 0);
		assertEquals(new Complexe(8, 0).getI(), deux.getI(), 0);
		deux = new Complexe(2, 0);

		// 8/2 = 4
		deux.autoDivise(huit);
		assertEquals(new Complexe(4, 0).getR(), deux.getR(), 0);
		assertEquals(new Complexe(4, 0).getI(), deux.getI(), 0);
		deux = new Complexe(2, 0);

		// 1.4+0.4i/0.8+0.8i = 1.125 - 0.652i
		arbitraire2.autoDivise(arbitraire1);
		assertEquals(new Complexe(1.125, -0.625).getI(), arbitraire2.getI(), 0.01);
		arbitraire2 = new Complexe(0.8, 0.8);
		arbitraire2.autoDivise(arbitraire1);
		assertEquals(new Complexe(1.125, -0.625).getR(), arbitraire2.getR(), 0.01);
		arbitraire2 = new Complexe(0.8, 0.8);
	}
}
