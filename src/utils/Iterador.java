package utils;

public class Iterador {
	private int i;

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
		if(this.i == 4) {
			this.i = 0;
		}
	}
}
