package main;

public class Tile {
	int[][] tiles;
	int color;
	
	public Tile() {
		tiles = new int[4][4];
	}
	
	public Tile(String type) {
		if (type.equals("I")) {
				setIBlock();
		}
		else if (type.equals("J")) {
				setJBlock();
		}
		else if (type.equals("L")) {
				setLBlock();
		}
		else if (type.equals("O")) {
				setOBlock();
		}
		else if (type.equals("S")) {
				setSBlock();
		}
		else if (type.equals("T")) {
				setTBlock();
		}
		else if (type.equals("Z")) {
				setZBlockV();
		}
	}
	
	//bottom aligned 
	public void setIBlock() {
		tiles[3][0] = 1;
		tiles[3][1] = 1;
		tiles[3][2] = 1;
		tiles[3][3] = 1;
	}
	
	//left
	public void setJBlock() {
		tiles[2][0] = 1;
		tiles[3][0] = 1;
		tiles[3][1] = 1;
		tiles[3][2] = 1;
	}
	
	//right
	public void setLBlock() {
		tiles[2][3] = 1;
		tiles[3][3] = 1;
		tiles[3][2] = 1;
		tiles[3][1] = 1;
	}
	
	//bottom center
	public void setOBlock() {
		tiles[2][1] = 1;
		tiles[2][2] = 1;
		tiles[3][1] = 1;
		tiles[3][2] = 1;
	}
	
	//bottom right
	public void setSBlock() {
	
	}

	public void setTBlock() {
		
	}
	
	public void setIBlock() {
		
	}
	
	public void setZBlock() {
		
	}
	
	public void rotateLeft() {
		
	}
	
	public void rotateRight() {
	
	}
	}
	
	
}
