# APCS2-Final-Project

<h3> Customizable Tetris (w/ Very Basic AI) </h3>

<hr>
<h4>Features:</h4>
<ul>
<li>Works like Tetris. Able to store a block (Cannot see the stored block).</li>
<li>Resize the grid by changing the # of rows or # of columns.</li>
<li>Change the level, which changes the speed.</li>
<li>Levels up as you clear 4 lines for each level, higher score for higher level.</li>
<li>Restarts automatically when you lose.</li>
<li>Keeps track of the highest score.</li>
<li>Has a player mode, but also an AI mode.</li>
<li>AI utilizies basic heuristics, gathering height, hole, 'bumpiness', and line clear data</li>
</ul>
<hr>

<h4>How to play:</h4>
<ol>
<li>Select difficulty, and speed levels in the sliders on the left side of the screen.</li>
<li>Change board size (# of rows, # of columns) to your likings. Larger boards may be easier to play on!
<li>Press START to allow for tiles to start falling</li>
<li>Press SHIFT to save tiles</li>
<li>Press SPACE to drop tiles quickly</li>
</ol>

<h4>Features in development</h4>
<ul>
<li>Grid analyzer to find the relative placement of tiles as to determine those to provide the player</li>
<li>Randomly generated Tetris shapes for greater variety and difficulty</li>
<li>Computer to play the game</li>
</ul>

<h4>Unresolved bugs:</h4>
<ul>
<li>Game crashes if you try to move a tile too fast before it spawns. </li>
<li>Computer doesn't rotate tiles</li>
<li>Scoring for computer doesn't update</li>
<li>I-block may not trigger a game over</li>
<li>Changing rows and columns in mid game may cause a crash when spawning blocks</li>
</ul>

<h4>Compilation:</h4>
**Requires Java 8**
<b>Linux: </b>
Use ```run.sh```
<b>Windows: </b>
Use ```java -jar Demo.jar```

<h4>Contributors:</h4>
<br>
Jeffrey Luo
Alan Zhou
