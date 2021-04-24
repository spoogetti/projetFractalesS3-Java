package project.models.maths;

// 11/11/2017
public class ComplexNumberFlt {
	private float real;
	private float imaginary;

	// getter & setter
	public float getR() {
		return real;
	}

	public float getI() {
		return imaginary;
	}

	public ComplexNumberFlt(float reel, float imaginary) {
		this.real = reel;
		this.imaginary = imaginary;
	}

	public ComplexNumberFlt add(ComplexNumberFlt c) {
		return new ComplexNumberFlt(real + c.real, imaginary + c.imaginary);
	}

	public ComplexNumberFlt substract(ComplexNumberFlt c) {
		return new ComplexNumberFlt(real - c.real, imaginary - c.imaginary);
	}

	//	multiplication :
	//		a+ai * b+bi
	//		real part :
	//		a * b - ai * bi
	//		imaginary part :
	//		a * bi + ai * b

	public ComplexNumberFlt mul(ComplexNumberFlt c) {
		float realPart 	 = real * c.real - imaginary * c.imaginary;
		float imaginaryPart = real * c.imaginary + imaginary * c.real;
		return new ComplexNumberFlt(realPart, imaginaryPart);
	}

	public ComplexNumberFlt pow(int power) {
		ComplexNumberFlt total = new ComplexNumberFlt(real, imaginary);
		for(int i=0; i<power-1; i++) {
			total = total.mul(this);
		}
		return total;
	}

	public ComplexNumberFlt div(ComplexNumberFlt c) {
		float realPart = c.real * real + c.imaginary * imaginary;
		float imaginaryPart = c.imaginary * real - c.real * imaginary;
		float denominator = real * real + imaginary * imaginary;

		float newRealPart = realPart / denominator;
		float newImaginaryPart = imaginaryPart / denominator;

		return new ComplexNumberFlt(newRealPart, newImaginaryPart);
	}

	// gain of time if no complex is sent back (less objects created)

	public void selfAdd(ComplexNumberFlt c) {
		real += c.real;
		imaginary += c.imaginary;
	}

	public void selfSubstract(ComplexNumberFlt c) {
		real -= c.real;
		imaginary -= c.imaginary;
	}

	public void selfMul(ComplexNumberFlt c) {
		float newReal 		 = real * c.real - imaginary * c.imaginary;
		float newImaginary	 = real * c.imaginary + imaginary * c.real;

		real = newReal;
		imaginary = newImaginary;
	}

	public void selfPow(int power) {
		ComplexNumberFlt clone = clone();
		for(int i=0; i<power-1; i++) {
			selfMul(clone);
		}
	}

	public void selfDiv(ComplexNumberFlt c) {
		float realPart = c.real * real + c.imaginary + imaginary;
		float imaginaryPart = c.imaginary * real - c.real * imaginary;
		float denominator = real * real + imaginary * imaginary;

		real = realPart / denominator;
		imaginary = imaginaryPart / denominator;
	}

	public float abs() {
		return (float) Math.hypot(real, imaginary);
	}

	public String toString() {
		return real + " + " + imaginary + "i";
	}

	public void setComplex(float real, float imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public ComplexNumberFlt clone() {
		return new ComplexNumberFlt(real, imaginary);
	}
}
