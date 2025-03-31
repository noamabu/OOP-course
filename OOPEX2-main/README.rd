# Bricker Game Manager

## Overview
Bricker Game Manager is a simple brick-breaking game built using Java and the **danogl** game framework. The game features a bouncing ball, a paddle controlled by the user, and multiple rows of bricks to break. The goal is to clear all the bricks without losing all lives.

## Features
- **Ball Mechanics**: A ball moves across the screen, bouncing off walls, the paddle, and bricks.
- **Paddle Control**: The player controls a paddle to keep the ball in play.
- **Brick Collision System**: Bricks disappear when hit by the ball.
- **Lives System**: The player has a limited number of lives before losing the game.
- **Win/Loss Conditions**: The game ends when all bricks are destroyed (win) or when all lives are lost (lose).
- **Restart Feature**: Prompts the player to restart the game after winning or losing.

## Installation & Setup
### Prerequisites
- Java Development Kit (JDK) 8 or later
- **danogl** game framework (Ensure it is included in your project)

### Running the Game
1. Clone the repository or download the source files.
2. Navigate to the project directory.
3. Compile and run the main Java file:
   ```sh
   javac bricker/main/BrickerGameManager.java
   java bricker.main.BrickerGameManager
   ```
   Alternatively, if using an IDE (like IntelliJ IDEA or Eclipse), open the project and run `BrickerGameManager.java`.

## How to Play
- **Move Paddle**: Use the keyboard arrow keys to move the paddle left and right.
- **Win Condition**: Break all bricks to win.
- **Lose Condition**: Lose all lives by letting the ball fall below the paddle.
- **Restart Game**: A prompt will appear after winning or losing, allowing you to restart.

## Project Structure
```
BrickerGameManager.java  # Main game manager class
├── bricker/
│   ├── brick_strategies/ # Collision and brick interaction logic
│   ├── gameobjects/      # Ball, Paddle, Bricks, Hearts, and UI elements
│   ├── main/             # Main game entry point
│
├── assets/  # Game assets (images, sounds)
```

## Dependencies
- **danogl**: Used for game rendering, collision detection, and input handling.

## Future Improvements
- Adding power-ups that modify ball or paddle behavior.
- Implementing multiple levels with increasing difficulty.
- Enhancing graphics and animations.

## License
This project is for educational purposes and can be freely modified and distributed.

---
Enjoy playing Bricker! 🏗️🎾
