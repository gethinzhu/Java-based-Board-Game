# Board Game Implementation in Java


## Features
This project developed a Java-based two-player grid-based board game, implementing a customizable game system with boards ranging from 1x1 to 100x100.
It includes move validation to prevent invalid actions (null, out-of-bounds, or occupied positions), a player-switching mechanism between White and Black, 
and win detection for consecutive pieces horizontally or vertically (adjusted for different size boards). 
The system also features deep copy functionality to ensure game state integrity and independence between copies.  <br> ！！！please neglect the .class files.！！！

## Installation
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/gethinzhu/Java-based-Board-Game.git
   cd board-game-java
   ```
2. **Ensure Java 17+**:
   - Check your Java version:
     ```bash
     java -version
     ```
   - Install Java 17 if needed (e.g., from [AdoptOpenJDK](https://adoptopenjdk.net/) or your package manager).
3. **Compile the Project**:
   ```bash
   javac game/*.java game/tests/*.java
   ```

## Usage
- **Run the Game**:
  - Execute tests for demonstration:
    ```bash
    java game.tests.GameTest
    ```
  - Output displays game states and test results.
- **Manual Play** (if integrated with `PlayVsAI`):
  - Compile with AI support (if available):
    ```bash
    javac ai/*.java game/*.java game/tests/*.java
    java ai.PlayVsAI
    ```
  - Follow prompts to input moves (e.g., "0 0" for row and column).

## Testing
- **Unit Tests**: Located in `game/tests/GameTest.java`.
  - Tests validate constructor behavior, initial state, move operations, win conditions, draws, and deep copy functionality.
  - Run tests with:
    ```bash
    java game.tests.GameTest
    ```
- **Coverage**: Achieves over 95% test coverage, tested across various board sizes and edge cases.
- **Debugging**: Use `System.out.println(game.getGrid().toString())` to inspect game states.

## Contributing
1. **Fork the Repository**.
2. **Create a Feature Branch**:
   ```bash
   git checkout -b feature-name
   ```
3. **Commit Changes**:
   ```bash
   git commit -m "Add feature-name"
   ```
4. **Push to the Branch**:
   ```bash
   git push origin feature-name
   ```
5. **Open a Pull Request**.
- **Guidelines**: Include unit tests and follow Java coding conventions.
- **Issues**: Report bugs or suggest features via the [Issues tab](https://github.com/yourusername/board-game-java/issues).
