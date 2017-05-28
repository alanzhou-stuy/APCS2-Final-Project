public class Setup {
  int numRows, windowWidth;
  int tileSize;
  Driver d;

  public Setup(int numRows, int windowWidth){
    this.numRows = numRows;
    this.windowWidth = windowWidth;
    d = new Driver();
    d.add(new Block(new int[1][0][1][0],"red",100,100));
  }
  
  void setupGrid(){
    fill(200,200,200);
    rect(40,40,40,40);
  }
}