# APCS2-Final-Project

# Customizable Tetris (w/ Very Basic AI)

## Features:
- Works like Tetris. Able to store a block (Cannot see the stored block).
- Resize the grid by changing the # of rows or # of columns.
- Change the level, which changes the speed.
- Levels up as you clear 4 lines for each level, higher score for higher level.
- Restarts automatically when you lose.
- Keeps track of the highest score.
- Has a player mode, but also an AI mode.
- AI utilizies basic heuristics, gathering height, hole, 'bumpiness', and line clear data

## How to play:
1. Select difficulty, and speed levels in the sliders on the left side of the screen
2. Change board size (# of rows, # of columns) to your likings. Larger boards may be easier to play on!
3. Press *START* to allow for tiles to start falling
4. Press *shift* to save tiles
5. Press *space* to drop tiles quickly

## Features in development:
- Grid analyzer to find the relative placement of tiles as to determine those to provide the player
- Randomly generated Tetris shapes for greater variety and difficulty
- Computer to play the game

## Unresolved bugs:
- Game crashes if you try to move a tile too fast before it spawns.
- Computer doesn't rotate tiles
- Scoring for computer doesn't update
- I-block may not trigger a game over
- Changing rows and columns in mid game may cause a crash when spawning blocks

## Compilation:
**Requires Java 8**

**Linux**:\
Use `./run.sh`

**Windows**:\
Use `run.bat`

#### Contributors:
Jeffrey Luo\
Alan Zhou\
*APCS-2 Mr. Konstantinovich (Pd. 10)*
