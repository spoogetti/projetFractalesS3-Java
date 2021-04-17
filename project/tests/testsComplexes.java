package project.tests;

import org.junit.Test;
import project.models.maths.ComplexNumber;

import static org.junit.Assert.assertEquals;

public class testsComplexes {
	static ComplexNumber one = new ComplexNumber(1, 0);
	static ComplexNumber two = new ComplexNumber(2, 0);
	static ComplexNumber minusTwo = new ComplexNumber(-2, 0);
	static ComplexNumber four = new ComplexNumber(4, 0);
	static ComplexNumber height = new ComplexNumber(8, 0);
	static ComplexNumber sixteen = new ComplexNumber(16, 0);
	
	static ComplexNumber iOne = new ComplexNumber(0, 1);
	static ComplexNumber iTwo = new ComplexNumber(0, 2);
	
	static ComplexNumber random1 = new ComplexNumber(1.4, 0.4);
	static ComplexNumber random2 = new ComplexNumber(0.8, 0.8);
	
	@Test
	public void testOperationsDeBase() {
		testAdd();
		testSub();
		testMul();
		testPower();
		testDiv();
		testAbs();

		testSelfAdd();
		testSelfSub();
		testSelfMul();
		testSelfPower();
		testSelfDiv();
	}

	@Test
	public void testAdd() {
		// 1+1 = 2
		assertEquals(new ComplexNumber(2, 0).getR(), one.add(one).getR(), 0);
		// i+i  = 2i
		assertEquals(new ComplexNumber(0, 3).getI(), iOne.add(iTwo).getI(), 0);
	}
	
	@Test
	public void testSub() {
		// 2-1 = 1
		assertEquals(new ComplexNumber(1, 0).getR(), two.minus(one).getR(), 0);
		// 2^4-1 = 15
		assertEquals(new ComplexNumber(15, 0).getR(), two.pow(4).minus(one).getR(), 0);
		// 2-8/4 = 0
		assertEquals(new ComplexNumber(0, 0).getR(), two.minus(four.div(height)).getR(), 0);
	}
	
	@Test
	public void testMul() {
		// 2*2 = 4
		assertEquals(new ComplexNumber(4, 0).getR(), two.mul(two).getR(), 0); // assertEquals avec des nombres flottant fonctionne de la manière suivante : résultat attendu puis resultat à tester et la différence
	}
	
	@Test
	public void testPower() {
		// 2^2 = 4
		assertEquals(new ComplexNumber(4, 0).getR(), two.pow(2).getR(), 0);
		// 2^3 = 8
		assertEquals(new ComplexNumber(8, 0).getR(), two.pow(3).getR(), 0);
		// 2^4 = 16
		assertEquals(new ComplexNumber(16, 0).getR(), two.pow(4).getR(), 0);
		// i*i = -1
		assertEquals(new ComplexNumber(-1, 0).getR(), iOne.pow(2).getR(), 0);
	}
	
	@Test
	public void testDiv() {
		// 16/2 = 8
		assertEquals(new ComplexNumber(8, 0).getR(), two.div(sixteen).getR(), 0);
		// 8/2 = 4
		assertEquals(new ComplexNumber(4, 0).getR(), two.div(height).getR(), 0);
		// 1.4+0.4i/0.8+0.8i = 1.125 - 0.652i
		assertEquals(new ComplexNumber(1.125, -0.625).getI(), random2.div(random1).getI(), 0.01);
		assertEquals(new ComplexNumber(1.125, -0.625).getR(), random2.div(random1).getR(), 0.01);
	}
	
	@Test
	public void testAbs() {
		// |-2| = 2
		assertEquals(new ComplexNumber(2, 0).getR(), minusTwo.abs(), 0);
	}

	@Test
	public void testSelfAdd() {
		// 1+1 = 2
		one.selfAdd(one);
		assertEquals(new ComplexNumber(2, 0).getR(), one.getR(), 0);
		one = new ComplexNumber(1, 0);

		// 2+2 = 4
		two.selfAdd(two);
		assertEquals(new ComplexNumber(4, 0).getR(), two.getR(), 0);
		two = new ComplexNumber(2, 0);

	}

	@Test
	public void testSelfSub() {
		// 1-1 = 0
		one.selfMinus(one);
		assertEquals(new ComplexNumber(0, 0).getR(), one.getR(), 0);
		one = new ComplexNumber(1, 0);

		// 2-1 = 1
		two.selfMinus(one);
		assertEquals(new ComplexNumber(1, 0).getR(), two.getR(), 0);
		two = new ComplexNumber(2, 0);
	}

	@Test
	public void testSelfMul() {
		// 1*8 = 8
		one.selfMul(height);
		assertEquals(new ComplexNumber(8, 0).getR(), one.getR(), 0);
		one = new ComplexNumber(1, 0);

		// 2*-2 = -4
		two.selfMul(minusTwo);
		assertEquals(new ComplexNumber(-4, 0).getR(), two.getR(), 0);
		two = new ComplexNumber(2, 0);
	}

	@Test
	public void testSelfPower() {
		// 2^3 = 8
		two.selfPow(3);
		assertEquals(new ComplexNumber(8, 0).getR(), two.getR(), 0);
		two = new ComplexNumber(2, 0);

		// 2^4 = 16
		two.selfPow(4);
		assertEquals(new ComplexNumber(16, 0).getR(), two.getR(), 0);
		two = new ComplexNumber(2, 0);
	}

	@Test
	public void testSelfDiv() {
		// 16/2 = 8
		two.selfDiv(sixteen);
		assertEquals(new ComplexNumber(8, 0).getR(), two.getR(), 0);
		assertEquals(new ComplexNumber(8, 0).getI(), two.getI(), 0);
		two = new ComplexNumber(2, 0);

		// 8/2 = 4
		two.selfDiv(height);
		assertEquals(new ComplexNumber(4, 0).getR(), two.getR(), 0);
		assertEquals(new ComplexNumber(4, 0).getI(), two.getI(), 0);
		two = new ComplexNumber(2, 0);

		// 1.4+0.4i/0.8+0.8i = 1.125 - 0.652i
		random2.selfDiv(random1);
		assertEquals(new ComplexNumber(1.125, -0.625).getI(), random2.getI(), 0.01);
		random2 = new ComplexNumber(0.8, 0.8);
		random2.selfDiv(random1);
		assertEquals(new ComplexNumber(1.125, -0.625).getR(), random2.getR(), 0.01);
		random2 = new ComplexNumber(0.8, 0.8);
	}
}
