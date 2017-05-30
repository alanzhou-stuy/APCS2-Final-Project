package main;

import processing.core.PApplet;

public abstract class RectangularElement extends PApplet implements Displayable {
	public PApplet pApplet;
	public int[] bgColor;
	public int rectWidth, rectHeight, sideMargin;

	public RectangularElement(PApplet pApplet) {
		this.pApplet = pApplet;
	}

	public void setBackgroundColor(int[] color) {
		this.bgColor = color;
	}

	public int getRectHeight() {
		return rectHeight;
	}

	public void setRectHeight(int rectHeight) {
		this.rectHeight = rectHeight;
	}

	public int getRectWidth() {
		return rectWidth;
	}

	public void setRectWidth(int rectWidth) {
		this.rectWidth = rectWidth;
	}

	public int getSideMargin() {
		return sideMargin;
	}

	public void setSideMargin(int sideMargin) {
		this.sideMargin = sideMargin;
	}
}