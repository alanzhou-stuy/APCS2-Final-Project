package main;

import java.io.Serializable;
import processing.core.PApplet;

/**
 * Main class for Tetris "block"
 */
public class Block {
	private float posX, posY;
	private int[][] shape;
	private String color;
	/*
	 * 'type' corresponds to different tetris block
	 */
	public Block(int[][] shape, String color, float posX, float posY) {
		this.shape = shape;
		this.color = color;
		this.posX = posX;
		this.posY = posY;
	}

	public void updatePos(float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
	}
}