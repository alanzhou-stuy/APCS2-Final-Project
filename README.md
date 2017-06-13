# APCS2-Final-Project

## Customizable Tetris (w/ Very Basic AI)

An attempted recreation of Tetris and a computer AI in Java. Visuals are rendered in Processing, while the ControlP5 library is used for several GUI components. 

### Features:
- Works like Tetris. Store a block and place it down later.
- Resize the grid by changing the number of rows or columns.
- Level progresses as more lines are cleared, which increases the speed of tile drops. 
- Levels up as you clear 4 lines for each level; higher score for higher level.
- Restarts automatically when you lose.
- Keeps track of the highest score.
- Has a player mode, but also a computer *AI* mode.
- AI utilizies basic heuristics, gathering height, hole, 'bumpiness', and line clear data

### How to play:
1. Select difficulty, and speed levels in the sliders on the left side of the screen
2. Change board size (# of rows, # of columns) to your likings. Larger boards may be easier to play on!
3. Press *START* to allow for tiles to start falling
4. Press *shift* to save tiles
5. Press *space* to drop tiles quickly

### Features in development:
- Grid analyzer to find the relative placement of tiles as to determine those to provide the player
- Randomly generated Tetris shapes for greater variety and difficulty
- Computer to play the game

### Unresolved bugs:
- Game crashes if you try to move a tile too fast before it spawns.
- Computer doesn't rotate tiles
- Scoring for computer doesn't update
- I-block may not trigger a game over
- Changing rows and columns in mid game may cause a crash when spawning blocks

### Compilation:
**Requires Java 8**

**Linux**:\
Use `./run.sh`

**Windows (No compilation, only run)**:\
Use `run.bat`\
or\
`java -jar Demo.jar`

#### Contributors:
Jeffrey Luo\
Alan Zhou\
*APCS-2 Mr. Konstantinovich (Pd. 10)*
