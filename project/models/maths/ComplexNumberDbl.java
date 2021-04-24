package project.models.maths;

// 11/11/2017
public class ComplexNumberDbl {
	private double real;
	private double imaginary;

	// getter & setter
	public double getR() {
		return real;
	}

	public double getI() {
		return imaginary;
	}

	public ComplexNumberDbl(double reel, double imaginary) {
		this.real = reel;
		this.imaginary = imaginary;
	}
	
	public ComplexNumberDbl add(ComplexNumberDbl c) {
		return new ComplexNumberDbl(real + c.real, imaginary + c.imaginary);
	}
	
	public ComplexNumberDbl substract(ComplexNumberDbl c) {
		return new ComplexNumberDbl(real - c.real, imaginary - c.imaginary);
	}
		
	//	multiplication :
	//		a+ai * b+bi
	//		real part :
	//		a * b - ai * bi
	//		imaginary part :
	//		a * bi + ai * b
		
	public ComplexNumberDbl mul(ComplexNumberDbl c) {
		double realPart 	 = real * c.real - imaginary * c.imaginary;
		double imaginaryPart = real * c.imaginary + imaginary * c.real;
		return new ComplexNumberDbl(realPart, imaginaryPart);
	}
	
	public ComplexNumberDbl pow(int power) {
		ComplexNumberDbl total = new ComplexNumberDbl(real, imaginary);
		for(int i=0; i<power-1; i++) {
			total = this.mul(total);
		}
		return total;
	}

	public ComplexNumberDbl div(ComplexNumberDbl c) {
		double realPart = c.real * real + c.imaginary * imaginary;
		double imaginaryPart = c.imaginary * real - c.real * imaginary;
		double denominator = real * real + imaginary * imaginary;

		double newRealPart = realPart / denominator;
		double newImaginaryPart = imaginaryPart / denominator;

		return new ComplexNumberDbl(newRealPart, newImaginaryPart);
	}

	// gain of time if no complex is sent back (less objects created)

	public void selfAdd(ComplexNumberDbl c) {
		real += c.real;
		imaginary += c.imaginary;
	}

	public void selfSubstract(ComplexNumberDbl c) {
		real -= c.real;
		imaginary -= c.imaginary;
	}

	public void selfMul(ComplexNumberDbl c) {
		double newReal 		 = real * c.real - imaginary * c.imaginary;
		double newImaginary	 = real * c.imaginary + imaginary * c.real;

		real = newReal;
		imaginary = newImaginary;
	}

	public void selfPow(int power) {
		ComplexNumberDbl clone = clone();
		for(int i=0; i<power-1; i++) {
			selfMul(clone);
		}
	}

	public void selfDiv(ComplexNumberDbl c) {
		double realPart = c.real * real + c.imaginary + imaginary;
		double imaginaryPart = c.imaginary * real - c.real * imaginary;
		double denominator = real * real + imaginary * imaginary;

		real = realPart / denominator;
		imaginary = imaginaryPart / denominator;
	}

	public double abs() {
		return Math.hypot(real, imaginary);
	}
	
	public String toString() {
		return real + " + " + imaginary + "i";
	}

	public void setComplex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public ComplexNumberDbl clone() {
		return new ComplexNumberDbl(real, imaginary);
	}
}
