package utils;

public class Pair {

	private int x;
	private int y;
	
	public Pair() {
		x = 0;
		y = 0;
	}
	
	
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean equals(Pair comparePair) {
		return (this.getX() == comparePair.getX() && this.getY() == comparePair.getY())? true : false;
	}
	
}
