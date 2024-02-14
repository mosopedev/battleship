/**
 * COMP 6481 (Winter 2024) Assignment #1
 * Due Date: Sunday, February 11, 2024 by 11:59PM
 * 
 * © Oluwamosope Adeyemi. ID: 40293741
 * © Jerome Kithinji. ID: 40280348
 * 
 * Written by: 
 * Jerome Kithinji. ID:
 * Oluwamosope Adeyemi. ID: 40293741
 */

package battleship;
import java.util.Scanner;

/**
 * The Board class represents the game board for the Battleship game, including
 * player ships, grenades, and methods handling game logic.
 */
public class Board {
    private Cell[][] battleshipBoard = new Cell[8][8]; // The battleship game board

    private Scanner scanner = new Scanner(System.in);

    private int humanShipsSank = 0; // Number of opponent ships human player has sank
    private int computerShipsSank = 0; // Number of opponents ships computer has sank
    private int numberOfPlaysPerTurn = 1; // Number of plays a player is allowed to play per turn
    private int currentPlayer = 0; // 0 for Human, 1 for Computer

    /**
     * Takes in user's inputted alphanumeric cell coordinate and converts it to
     * valid indexes.
     *
     * @param input The user input representing a cell coordinate (e.g., A3)
     * @return An array of integers representing the row and column index of a cell
     *         on the Battleship grid.
     */
    private int[] formatUserInput(String input) {
        String[] splitInput = input.split("");

        int row = Integer.parseInt(input.split("")[1]);
        int column = switch (splitInput[0].toUpperCase()) {
            case "A" -> 0;
            case "B" -> 1;
            case "C" -> 2;
            case "D" -> 3;
            case "E" -> 4;
            case "F" -> 5;
            case "G" -> 6;
            case "H" -> 7;
            default -> 8;
        };
        int[] coordinates = { row, column };
        return coordinates;
    }

    /**
     * Takes in the column index of a cell and checks it against its corresponding
     * Alphabet.
     *
     * @param column The column index of a cell
     * @return The corresponding alphabet for a cell's column index.
     */
    private String formatComputerColumn(int column) {
        String formattedColumn = switch (column) {
            case 0 -> "A";
            case 1 -> "B";
            case 2 -> "C";
            case 3 -> "D";
            case 4 -> "E";
            case 5 -> "F";
            case 6 -> "G";
            case 7 -> "H";
            default -> "x";
        };

        return formattedColumn;
    }

    /**
     * Prints to the console the current state of the battleship board.
     */
    public void printBoard() {
        for (int i = 0; i < battleshipBoard.length; i++) {
            for (int j = 0; j < battleshipBoard[i].length; j++) {
                if (battleshipBoard[i][j] == null || battleshipBoard[i][j].getHasBeenCalled() == false) {
                    System.out.print("_ ");
                } else if (battleshipBoard[i][j] != null && battleshipBoard[i][j].getHasBeenCalled() == true) {
                    System.out.print(battleshipBoard[i][j].getType() + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Randomly sets the computer's ships on the battleship board.
     * Utilizes java's Math.random method to generate rows and column indexes.
     */
    public void setComputerShips() {
        int counter = 0;
        final int REQUIRED_NUMBER_OF_SHIPS = 6;

        int row = (int) Math.round(Math.random() * 7);
        int column = (int) Math.round(Math.random() * 7);

        // Place 6 ships
        while (counter < REQUIRED_NUMBER_OF_SHIPS) {

            // if random position generated is invalid. Generate a new one
            if (battleshipBoard[row][column] != null) {
                row = (int) Math.round(Math.random() * 7);
                column = (int) Math.round(Math.random() * 7);
            } else {
                battleshipBoard[row][column] = new Cell('S', "Computer", row, column);
                counter++;
            }
        }
    }

    /**
     * Sets computer's grenade on the battleship board.
     * 
     * @return void
     */
    public void setComputerGrenades() {
        int counter = 0;
        final int REQUIRED_NUMBER_OF_GRENADES = 4;

        int row = (int) Math.round(Math.random() * 7);
        int column = (int) Math.round(Math.random() * 7);

        // Place 4 grenades
        while (counter < REQUIRED_NUMBER_OF_GRENADES) {

            // if random position generated is invalid. Generate a new one
            if (battleshipBoard[row][column] != null) {
                row = (int) Math.round(Math.random() * 7);
                column = (int) Math.round(Math.random() * 7);
            } else {
                battleshipBoard[row][column] = new Cell('S', "Computer", row, column);
                counter++;
            }
        }
    }

    /**
     * Sets the ships for the human player on the battleship board.
     * 
     * @return void
     */
    public void setHumanShips() {
        int humanShipCounter = 0;
        final int REQUIRED_NUMBER_OF_SHIPS = 6;

        // Place 6 human ships
        while (humanShipCounter < REQUIRED_NUMBER_OF_SHIPS) {

            // Request ship coordinate from human player
            System.out.println("Enter the coordinates of your ship #" + (humanShipCounter + 1));
            String input = scanner.nextLine();

            int[] coordinate = this.formatUserInput(input);
            int row = coordinate[0];
            int column = coordinate[1];

            if (column > 7 || row > 7)
                System.out.println("sorry, coordinates outside the grid. try again.");

            // Cell coordinate is already occupied
            else if (battleshipBoard[row][column] != null) {
                System.out.println("sorry, coordinates already used. try again.");
            }

            // Create new cell object and set human's ship in coordinate
            else {
                battleshipBoard[row][column] = new Cell('s', "Human", row, column);
                humanShipCounter++;
            }
        }
    }

    /**
     * Sets the grenades for the human player on the battleship board.
     * 
     * @return void.
     */
    public void setHumanGrenades() {
        int humanGrenadeCounter = 0;
        final int REQUIRED_NUMBER_OF_GRENADES = 4;

        while (humanGrenadeCounter < REQUIRED_NUMBER_OF_GRENADES) {
            System.out.println("Enter the coordinates of your grenades #" + (humanGrenadeCounter + 1));

            String input = scanner.nextLine();

            int[] coordinate = this.formatUserInput(input);
            int row = coordinate[0];
            int column = coordinate[1];

            if (column > 7 || row > 7) {
                System.out.println("sorry, coordinates outside the grid. try again.");
            } else if (battleshipBoard[row][column] != null) {
                System.out.println("sorry, coordinates already used. try again.");
            } else {
                battleshipBoard[row][column] = new Cell('g', "Human", row, column);
                humanGrenadeCounter++;
            }
        }
    }

    /**
     * Handles the human's turn in the game.
     * It checks if the rocket (the position called) hits a ship or a grenade and applies the rules of the game.
     *
     * @return An integer representing the result of the game play.
     *         <br>
     *         If player hits a ship, player doesn't skip any plays in the next turn i.e 0 is returned. 
     *         <br>
     *         If player hits a grenade, player will skip a play in the next turn, allowing
     *         opponent to play twice during the next turn. i.e 2 is returned.
     */
    private int humanGamePlay() {
        System.out.println("position of your rocket: ");
        String input = scanner.nextLine();

        int[] coordinate = this.formatUserInput(input);
        int row = coordinate[0];
        int column = coordinate[1];

        // Checks if coordinate provided is valid and within the grid but has no ship or grenade
        // in it.
        if (this.battleshipBoard[row][column] == null && column < 8 && row < 8) {
            System.out.println("nothing.");

            this.battleshipBoard[row][column] = new Cell('*', "None", row, column);
            this.battleshipBoard[row][column].setHasBeenCalled(true);
        }
        // ship hit
        else if (this.battleshipBoard[row][column].getHasBeenCalled() == false
                && this.battleshipBoard[row][column].getType() == 'S'
                || this.battleshipBoard[row][column].getType() == 's') {

            System.out.println("ship hit.");

            this.battleshipBoard[row][column].setHasBeenCalled(true);
            this.humanShipsSank++; // increase human's score
            return 0;
        } else if (this.battleshipBoard[row][column].getHasBeenCalled() == false
                && this.battleshipBoard[row][column].getType() == 'G'
                || this.battleshipBoard[row][column].getType() == 'g') {

            System.out.println("boom! grenade. ");

            this.battleshipBoard[row][column].setHasBeenCalled(true);
            return 2;
        } else {
            System.out.println("nothing. position already called");
            return 0;
        }
        return 0;
    }

   /**
     * Handles the computer's turn in the game.
     * It checks if the rocket (the position called) hits a ship or a grenade and applies the rules of the game.
     *
     * @return An integer representing the result of the game play.
     *         <br>
     *         If player hits a ship, player doesn't skip any plays in the next turn i.e 0 is returned. 
     *         <br>
     *         If player hits a grenade, player will skip a play in the next turn, allowing
     *         opponent to play twice during the next turn. i.e 2 is returned.
     */
    private int computerGamePlay() {
        int row = (int) Math.round(Math.random() * 7);
        int column = (int) Math.round(Math.random() * 7);

        System.out.println("position of my rocket: " + this.formatComputerColumn(column) + row);

        if (this.battleshipBoard[row][column] == null && column < 8 && row < 8) { // Cell position is valid but no ship
                                                                                  // or grenade was put there
            System.out.println("nothing.");

            this.battleshipBoard[row][column] = new Cell('*', "None", row, column);
            this.battleshipBoard[row][column].setHasBeenCalled(true);

        }
        // Cell position is valid and holds a Ship.
        else if (this.battleshipBoard[row][column].getHasBeenCalled() == false
                && this.battleshipBoard[row][column].getType() == 'S'
                || this.battleshipBoard[row][column].getType() == 's') {

            System.out.println("ship hit.");
            this.battleshipBoard[row][column].setHasBeenCalled(true);
            this.computerShipsSank++; // increase computer's score

            return 0;
        }
        // Cell position is valid and holds a Grenade.
        else if (this.battleshipBoard[row][column].getHasBeenCalled() == false
                && this.battleshipBoard[row][column].getType() == 'G'
                || this.battleshipBoard[row][column].getType() == 'g') {

            System.out.println("boom! grenade. ");
            this.battleshipBoard[row][column].setHasBeenCalled(true);

            return 2;
        } else {
            System.out.println("Nothing, position already called.");
            return 0;
        }

        return 0;
    }

    /**
     * Starts the Battleship game and continues until one player sinks all
     * opponent's ships.
     */
    public void startGame() {
        while (this.humanShipsSank < 6 && this.computerShipsSank < 6) {
            
            // Checks if it's human's turn to play. 
            if (currentPlayer == 0) {
                int nextPlays = 0;

                // loops for the number of plays player is expected to play in this turn.
                for (int i = 0; i < this.numberOfPlaysPerTurn; i++) {
                    int gamePlayResult = this.humanGamePlay();
                    this.printBoard();

                    if (gamePlayResult == 0) {
                        nextPlays = 1;
                    } else if (gamePlayResult == 2) {
                        nextPlays += gamePlayResult;
                    }
                }

                currentPlayer = 1;
                this.numberOfPlaysPerTurn = nextPlays; // The number of plays the next player (opponent) is expected to play in his or her next turn.
            } 
            // Computer's turn to play. 
            else {
                int nextPlays = 0;

                for (int i = 0; i < this.numberOfPlaysPerTurn; i++) {
                    int gamePlayResult = this.computerGamePlay();
                    this.printBoard();

                    if (gamePlayResult == 0) {
                        nextPlays = 1;
                    } else if (gamePlayResult == 2) {
                        nextPlays += gamePlayResult;
                    }
                }

                currentPlayer = 0;
                this.numberOfPlaysPerTurn = nextPlays;
            }
        }

        if (this.humanShipsSank > this.computerShipsSank) {
            System.out.println("You Win!");
        } else {
            System.out.println("I Win!");
        }
    }
}
