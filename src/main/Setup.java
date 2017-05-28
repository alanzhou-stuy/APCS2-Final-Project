package main;

import processing.core.*;

public class Setup extends PApplet {
	PApplet pApplet;
	
	public Setup(PApplet pApplet){
		this.pApplet = pApplet;
	}

	public void createGrid(){
		pApplet.rect(10, 10, 100, 100);
	}
}