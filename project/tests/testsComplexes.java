package project.tests;

import org.junit.Test;
import project.models.maths.ComplexNumberBigD;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testsComplexes {
	static ComplexNumberBigD one = new ComplexNumberBigD(new BigDecimal(1), new BigDecimal(0));
	static ComplexNumberBigD two = new ComplexNumberBigD(new BigDecimal(2), new BigDecimal(0));
	static ComplexNumberBigD minusTwo = new ComplexNumberBigD(new BigDecimal(-2), new BigDecimal(0));
	static ComplexNumberBigD four = new ComplexNumberBigD(new BigDecimal(4), new BigDecimal(0));
	static ComplexNumberBigD height = new ComplexNumberBigD(new BigDecimal(8), new BigDecimal(0));
	static ComplexNumberBigD sixteen = new ComplexNumberBigD(new BigDecimal(16), new BigDecimal(0));
	
	static ComplexNumberBigD iOne = new ComplexNumberBigD(new BigDecimal(0), new BigDecimal(1));
	static ComplexNumberBigD iTwo = new ComplexNumberBigD(new BigDecimal(0), new BigDecimal(2));
	
	static ComplexNumberBigD random1;
	static {
		BigDecimal real1 = new BigDecimal("1.4").setScale(5, RoundingMode.HALF_UP);
		BigDecimal imaginary1 = new BigDecimal("0.4").setScale(5, RoundingMode.HALF_UP);
		random1 = new ComplexNumberBigD(real1, imaginary1);
	}

	static ComplexNumberBigD random2;
	static {
		BigDecimal real2 = new BigDecimal("0.8").setScale(5, RoundingMode.HALF_UP);
		BigDecimal imaginary2 = new BigDecimal("0.8").setScale(5, RoundingMode.HALF_UP);
		random2 = new ComplexNumberBigD(real2, imaginary2);
	}

	@Test
	public void testBasicOperations() {
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
		assertEquals(new ComplexNumberBigD(new BigDecimal(2), new BigDecimal(0)).getR(), one.add(one).getR());
		// i+i  = 2i
		assertEquals(new ComplexNumberBigD(new BigDecimal(0), new BigDecimal(3)).getI(), iOne.add(iTwo).getI());
	}
	
	@Test
	public void testSub() {
		// 2-1 = 1
		assertEquals(new ComplexNumberBigD(new BigDecimal(1), new BigDecimal(0)).getR(), two.minus(one).getR());
		// 2^4-1 = 15
		assertEquals(new ComplexNumberBigD(new BigDecimal(15), new BigDecimal(0)).getR(), two.pow(4).minus(one).getR());
		// 2-8/4 = 0
		assertEquals(new ComplexNumberBigD(new BigDecimal(0), new BigDecimal(0)).getR(), two.minus(four.div(height)).getR());
	}
	
	@Test
	public void testMul() {
		// 2*2 = 4
		assertEquals(new ComplexNumberBigD(new BigDecimal(4), new BigDecimal(0)).getR(), two.mul(two).getR());
	}
	
	@Test
	public void testPower() {
		// 2^2 = 4
		assertEquals(new ComplexNumberBigD(new BigDecimal(4), new BigDecimal(0)).getR(), two.pow(2).getR());
		// 2^3 = 8
		assertEquals(new ComplexNumberBigD(new BigDecimal(8), new BigDecimal(0)).getR(), two.pow(3).getR());
		// 2^4 = 16
		assertEquals(new ComplexNumberBigD(new BigDecimal(16), new BigDecimal(0)).getR(), two.pow(4).getR());
		// i*i = -1
		assertEquals(new ComplexNumberBigD(new BigDecimal(-1), new BigDecimal(0)).getR(), iOne.pow(2).getR());
	}
	
	@Test
	public void testDiv() {
		// 16/2 = 8
		assertEquals(new ComplexNumberBigD(new BigDecimal(8), new BigDecimal(0)).getR(), two.div(sixteen).getR());
		// 8/2 = 4
		assertEquals(new ComplexNumberBigD(new BigDecimal(4), new BigDecimal(0)).getR(), two.div(height).getR());
		// 1.4+0.4i/0.8+0.8i = 1.125 - 0.652i
		assertEquals(new ComplexNumberBigD(new BigDecimal("1.125"), new BigDecimal("-0.625")).getI(), random2.div(random1).getI());
		assertEquals(new ComplexNumberBigD(new BigDecimal("1.125"), new BigDecimal("-0.625")).getR(), random2.div(random1).getR());
	}

	@Test
	public void testAbs() {
		// |-2| = 2
		assertEquals(new ComplexNumberBigD(new BigDecimal(2), new BigDecimal(0)).getR(), minusTwo.abs());
	}

	@Test
	public void testSelfAdd() {
		// 1+1 = 2
		one.selfAdd(one);
		assertEquals(new ComplexNumberBigD(new BigDecimal(2), new BigDecimal(0)).getR(), one.getR());
		one = new ComplexNumberBigD(new BigDecimal(1), new BigDecimal(0));

		// 2+2 = 4
		two.selfAdd(two);
		assertEquals(new ComplexNumberBigD(new BigDecimal(4), new BigDecimal(0)).getR(), two.getR());
		two = new ComplexNumberBigD(new BigDecimal(2), new BigDecimal(0));

	}

	@Test
	public void testSelfSub() {
		// 1-1 = 0
		one.selfSubstract(one);
		assertEquals(new ComplexNumberBigD(new BigDecimal(0), new BigDecimal(0)).getR(), one.getR());
		one = new ComplexNumberBigD(new BigDecimal(1), new BigDecimal(0));

		// 2-1 = 1
		two.selfSubstract(one);
		assertEquals(new ComplexNumberBigD(new BigDecimal(1), new BigDecimal(0)).getR(), two.getR());
		two = new ComplexNumberBigD(new BigDecimal(2), new BigDecimal(0));
	}

	@Test
	public void testSelfMul() {
		// 1*8 = 8
		one.selfMul(height);
		assertEquals(new ComplexNumberBigD(new BigDecimal(8), new BigDecimal(0)).getR(), one.getR());
		one = new ComplexNumberBigD(new BigDecimal(1), new BigDecimal(0));

		// 2*-2 = -4
		two.selfMul(minusTwo);
		assertEquals(new ComplexNumberBigD(new BigDecimal(-4), new BigDecimal(0)).getR(), two.getR());
		two = new ComplexNumberBigD(new BigDecimal(2), new BigDecimal(0));
	}

	@Test
	public void testSelfPower() {
		// 2^3 = 8
		two.selfPow(3);
		assertEquals(new ComplexNumberBigD(new BigDecimal(8), new BigDecimal(0)).getR(), two.getR());
		assertEquals(new ComplexNumberBigD(new BigDecimal(8), new BigDecimal(0)).getI(), BigDecimal.ZERO);
		two = new ComplexNumberBigD(new BigDecimal(2), new BigDecimal(0));

		// 2^4 = 16
		two.selfPow(4);
		assertEquals(new ComplexNumberBigD(new BigDecimal(16), new BigDecimal(0)).getR(), two.getR());
		assertEquals(new ComplexNumberBigD(new BigDecimal(8), new BigDecimal(0)).getI(), BigDecimal.ZERO);
		two = new ComplexNumberBigD(new BigDecimal(2), new BigDecimal(0));

		// 2i^2 = -4
		iTwo.selfPow(2);
		assertEquals(new ComplexNumberBigD(new BigDecimal(-4), new BigDecimal(0)).getR(), iTwo.getR());
		assertEquals(BigDecimal.ZERO, iTwo.getI());
		iTwo = new ComplexNumberBigD(new BigDecimal(0), new BigDecimal(2));

		// 2i^4 = 16
		iTwo.selfPow(4);
		assertEquals(new BigDecimal(16), iTwo.getR());
		assertEquals(BigDecimal.ZERO, iTwo.getI());
		iTwo = new ComplexNumberBigD(new BigDecimal(0), new BigDecimal(2));
	}

	@Test
	public void testSelfDiv() {
		// 16/2 = 8
		two.selfDiv(sixteen);
		assertEquals(new ComplexNumberBigD(new BigDecimal(8), new BigDecimal(0)).getR(), two.getR());
		assertEquals(new ComplexNumberBigD(new BigDecimal(8), new BigDecimal(0)).getI(), two.getI());
		two = new ComplexNumberBigD(new BigDecimal(2), new BigDecimal(0));

		// 8/2 = 4
		two.selfDiv(height);
		assertEquals(new ComplexNumberBigD(new BigDecimal(4), new BigDecimal(0)).getR(), two.getR());
		assertEquals(new ComplexNumberBigD(new BigDecimal(4), new BigDecimal(0)).getI(), two.getI());
		two = new ComplexNumberBigD(new BigDecimal(2), new BigDecimal(0));

		// 1.4+0.4i/0.8+0.8i = 1.125 - 0.652i
		random2.selfDiv(random1);
		assertEquals(new ComplexNumberBigD(new BigDecimal("1.125"), new BigDecimal("-0.625")).getI(), random2.getI());
		random2 = new ComplexNumberBigD(new BigDecimal("0.8"), new BigDecimal("0.8"));
		random2.selfDiv(random1);
		assertEquals(new ComplexNumberBigD(new BigDecimal("1.125"), new BigDecimal("-0.625")).getR(), random2.getR());
		random2 = new ComplexNumberBigD(new BigDecimal("0.8"), new BigDecimal("0.8"));
	}
}
