**Galaga Game**
***************************
Basic features:

1st - Use arrow keys to move the ship.

2nd - Display at the bottom left corner how many ships player has to kill to meet the boss and player's current score and HP info.

3rd - Enemies move in formation across the screen. Once in a while a ship leaves the formation.

4th - Eventually all the fighters will move down the screen. At some point the fighters start firing bullets down the screen.

5th - The player has 100 health points in total. If a bullet or fighter collides with the spaceship the player drops certain health point (50 for collides and 30 for bullets). The player has three lives before the
game is over.

6th - The ship can also fire bullets with the F key. If the bullet hits a ship then the player gain 10 points.

7th - The game increase in difficulty for every 50 points player earns. The formation will contain more fighters for the next wave.

8th - The player won the game if the boss is killed. The player can check top 10 scores from the main menu. 
The scores are saved to a file and will be loaded when player check them. 
If the game runs for the first time and no score file has been created, the IO error is handled by a try catch block.

9th - Use sprite textures (images) to represent the ships and enemies.

10th- Implement the MVC model.


*************************
Advanced features:

1st - Multiple modes. The game has three modes controlled by key listeners: main menu mode; game mode; game-over mode.

2th - Advanced movements. The formation will first go down the screen, then do back and forth movement within a certain boundary, 
and finally it will maintain horizontal movements. 
Once in a while, one of the the ships will leave the formation to do its own movement. Eventually all ships will go down the screen.

3rd - Sound effects. Sound effects during game start, collision, firing, getting shots and Boss appearing.

4th - Animation. If the ship is hit by a fighter or bullet an explosion animation appears.

5th - Special level. After the player scores 100 points, a Boss will appear. It has more HP, faster movement, faster shooting speed and stronger bullets.
No new fighters will appear since the boss come. The boss HP is displayed on screen. 
Boss will worth 100 points.

***********
Known bugs:

1st - Sometimes if player fires continuously without releasing the F key, the sounds player
will pop up error messages "unable to read a line".

