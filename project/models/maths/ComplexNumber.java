package project.models.maths;

// 11/11/2017
public class ComplexNumber {
	private double real;
	private double imaginary;
	
	// getter & setter
	public double getR() {
		return real;
	}
	
	public double getI() {
		return imaginary;
	}
	
	public ComplexNumber(double reel, double imaginary) {
		this.real = reel;
		this.imaginary = imaginary;
	}
	
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}
	
	public ComplexNumber minus(ComplexNumber c) {
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}
		
	//	multiplication :
	//		a+ai * b+bi
	//		real part :
	//		a.re * b.re - a.im * b.im
	//		imaginary part :
	//		a.re * b.im + a.im * b.re
		
	public ComplexNumber mul(ComplexNumber c) {
		double realPart 	 = real * c.real - imaginary * c.imaginary;
		double imaginaryPart = imaginary * c.real + real * c.imaginary;
		return new ComplexNumber(realPart, imaginaryPart);
	}
	
	public ComplexNumber pow(int power) {
		ComplexNumber total = new ComplexNumber(real, imaginary);
		for(int i=0; i<power-1; i++) {
			total = total.mul(this);
		}
		return total;
	}

	public ComplexNumber div(ComplexNumber c) {
		double realPart = (c.real * real + c.imaginary * imaginary);
		double imaginaryPart = (c.imaginary * real - (c.real * imaginary));
		double denominator = (real * real + imaginary * imaginary);
		return new ComplexNumber(realPart/denominator, imaginaryPart/denominator);
	}

	// gain of time if no complex is sent back (less objects created)

	public void selfAdd(ComplexNumber c) {
		this.real += c.real;
		this.imaginary += c.real;
	}

	public void selfMinus(ComplexNumber c) {
		this.real -= c.real;
		this.imaginary -= c.imaginary;
	}

	public void selfMul(ComplexNumber c) {
		this.real = real * c.real - imaginary * c.imaginary;
		this.imaginary = imaginary * c.real + this.real * c.imaginary;
	}

	public void selfPow(int power) {
		ComplexNumber clone = this.clone();
		for(int i=0; i<power-1; i++) {
			this.selfMul(clone);
		}
	}

	public void selfDiv(ComplexNumber c) {
		double realPart = (c.real * real + c.imaginary * imaginary);
		double imaginaryPart = (c.imaginary * real - (c.real * imaginary));
		double denominator = (real * real + imaginary * imaginary);
		this.real = realPart/denominator;
		this.imaginary = imaginaryPart/denominator;
	}

	public double abs() {
		return Math.hypot(real, imaginary);
	}
	
	public String toString() {
		return real + " + i" + imaginary;
	}

	public void setComplex(double a, double b) {
		this.real = a;
		this.imaginary = b;
	}

	public ComplexNumber clone() {
		return new ComplexNumber(this.real, this.imaginary);
	}
}
