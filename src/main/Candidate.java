package main;

public class Candidate implements Comparable<Candidate> {
	public int strength;
	public int numRotations;
	public int pivotYIndex;
	public int pivotXIndex;

	public int resultingAH;
	public int resultingLC;
	public int resultingH;
	public int resultingB;
	public Tile t;

	public Candidate(Tile t, int numRotations, int pivotY, int pivotX, int strength) {
		this.t = t;
		this.numRotations = numRotations;
		this.pivotYIndex = pivotY;
		this.pivotXIndex = pivotX;
		this.strength = strength;
	}

	@Override
	public int compareTo(Candidate o) {
		return o.strength - this.strength;
	}

	public void calculateStrength() {
		strength = (int) (-50 * resultingAH + 176 * resultingLC - 35 * resultingH - 20 * resultingB);
	}
}