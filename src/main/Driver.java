package main;

import java.util.*;

/**
 * Driver class for project; will initialize all classes and support graphics
 */
public class Driver {
	private LinkedList<Block> newBlocks;

	public Driver() {
		newBlocks = new LinkedList<Block>();
	}
	
	public void add(Block b){
		newBlocks.add(b);
	}

	public void displayNewBlock() {
	};
}