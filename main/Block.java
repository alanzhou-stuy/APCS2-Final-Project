/**
 * Main class for Tetris "block"
 */

public class Block {
    private float posX, posY;

    /*
      'type' corresponds to different tetris block
    */
    public Block(int type){
    }

    public void updatePos(float posX, float posY){
	this.posX = posX;
	this.posY = posY;
    }    
}
