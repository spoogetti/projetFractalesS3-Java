package project.models.maths;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

// 11/11/2017
public class ComplexNumberBigD {
	static final BigDecimal TWO = BigDecimal.valueOf(2);

	static final int SCALE = 10;
	static final MathContext MC = new MathContext(SCALE);

	private BigDecimal real;
	private BigDecimal imaginary;
	
	// getter & setter
	public BigDecimal getR() {
		return real.stripTrailingZeros();
	}
	
	public BigDecimal getI() {
		return imaginary.stripTrailingZeros();
	}
	
	public ComplexNumberBigD(BigDecimal reel, BigDecimal imaginary) {
		this.real = reel;
		this.imaginary = imaginary;
	}
	
	public ComplexNumberBigD add(ComplexNumberBigD c) {
		return new ComplexNumberBigD(real.add(c.real), imaginary.add(c.imaginary));
	}
	
	public ComplexNumberBigD minus(ComplexNumberBigD c) {
		return new ComplexNumberBigD(real.subtract(c.real), imaginary.subtract(c.imaginary));
	}
		
	//	multiplication :
	//		a+ai * b+bi
	//		real part :
	//		a * b - ai * bi
	//		imaginary part :
	//		a * bi + ai * b
		
	public ComplexNumberBigD mul(ComplexNumberBigD c) {
		BigDecimal realPart 	 = real.multiply(c.real).subtract(imaginary.multiply(c.imaginary));
		BigDecimal imaginaryPart = real.multiply(c.imaginary).add(imaginary.multiply(c.real));
		return new ComplexNumberBigD(realPart, imaginaryPart);
	}
	
	public ComplexNumberBigD pow(int power) {
		ComplexNumberBigD total = new ComplexNumberBigD(real, imaginary);
		for(int i=0; i<power-1; i++) {
			total = total.mul(this);
		}
		return total;
	}

	public ComplexNumberBigD div(ComplexNumberBigD c) {
		BigDecimal realPart = c.real.multiply(real).add(c.imaginary.multiply(imaginary));
		BigDecimal imaginaryPart = c.imaginary.multiply(real).subtract(c.real.multiply(imaginary));
		BigDecimal denominator = real.multiply(real).add(imaginary.multiply(imaginary));

		BigDecimal newRealPart = realPart.divide(denominator, RoundingMode.HALF_UP).stripTrailingZeros();
		BigDecimal newImaginaryPart = imaginaryPart.divide(denominator, RoundingMode.HALF_UP).stripTrailingZeros();

		return new ComplexNumberBigD(newRealPart, newImaginaryPart);
	}

	// gain of time if no complex is sent back (less objects created)

	public void selfAdd(ComplexNumberBigD c) {
		real = real.add(c.real);
		imaginary = imaginary.add(c.imaginary);
	}

	public void selfSubstract(ComplexNumberBigD c) {
		real = real.subtract(c.real);
		imaginary = imaginary.subtract(c.imaginary);
	}

	public void selfMul(ComplexNumberBigD c) {
		BigDecimal newReal 		 = real.multiply(c.real).subtract(imaginary.multiply(c.imaginary));
		BigDecimal newImaginary	 = real.multiply(c.imaginary).add(imaginary.multiply(c.real));

		real = newReal;
		imaginary = newImaginary;
	}

	public void selfPow(int power) {
		ComplexNumberBigD clone = clone();
		for(int i=0; i<power-1; i++) {
			selfMul(clone);
		}
	}

	public void selfDiv(ComplexNumberBigD c) {
		BigDecimal realPart = c.real.multiply(real).add(c.imaginary.multiply(imaginary));
		BigDecimal imaginaryPart = c.imaginary.multiply(real).subtract(c.real.multiply(imaginary));
		BigDecimal denominator = real.multiply(real).add(imaginary.multiply(imaginary));

		real = realPart.divide(denominator, RoundingMode.HALF_UP).stripTrailingZeros();
		imaginary = imaginaryPart.divide(denominator, RoundingMode.HALF_UP).stripTrailingZeros();
	}

	public BigDecimal abs() {
		BigDecimal sidesSquared = real.pow(2).add(imaginary.pow(2));
		if(sidesSquared.equals(BigDecimal.ZERO))
			return BigDecimal.ZERO;
		else
			return sqrt(sidesSquared).stripTrailingZeros();
	}
	
	public String toString() {
		return real + " + " + imaginary + "i";
	}

	public void setComplex(BigDecimal real, BigDecimal imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public ComplexNumberBigD clone() {
		return new ComplexNumberBigD(real, imaginary);
	}

	public static BigDecimal sqrt(BigDecimal A) {
		BigDecimal x0 = new BigDecimal("0");
		BigDecimal x1 = BigDecimal.valueOf(Math.sqrt(A.doubleValue()));
		while (!x0.equals(x1)) {
			x0 = x1;
			x1 = A.divide(x0, SCALE, RoundingMode.HALF_UP);
			x1 = x1.add(x0);
			x1 = x1.divide(TWO, SCALE, RoundingMode.HALF_UP);
		}
		return x1;
	}

//	public static BigDecimal sqrt(BigDecimal A) {
//		try {
//			return A.sqrt(MC);
//		} catch (Exception e) {
//			System.err.println(e);
//			return BigDecimal.ZERO;
//		}
//	}


}
