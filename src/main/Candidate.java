package main;

public class Candidate {
	public double strength;
	public int numRotations;
	public int pivotXIndex;
	public int pivotYIndex;
	public Tile t;

	public Candidate(Tile t, int numRotations, int pivotX, int pivotY) {
		this.t = t;
		this.numRotations = numRotations;
		this.pivotXIndex = pivotX;
		this.pivotYIndex = pivotY;
	}
}