package main;

public class Square {
	int xCor;
	int yCor;
	int color;
	
	public Square() {		
	}
	
	public Square(int xCor,int yCor,int color){
		this.xCor = xCor;
		this.yCor = yCor;
		this.color = color;
	}
	
	public void setColor(int x) {
		color = x;	
	}
	
	public void setXCor(int x) {
		xCor = x;
	}
		
	public void setYCor(int x) {
		yCor = x;
	}
	
	public int getColor() {
		return color;
	}
	
	public int getXCor() {
		return xCor;
	}
	
	public int getYCor() {
		return yCor;
	}

}
 
