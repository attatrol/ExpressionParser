package attatrol.exparser.tokens;

public class Constant implements Token {

	private double value;

	public double getValue() {
		return value;
	}

	public Constant(double value) {
		this.value = value;
	}

}
