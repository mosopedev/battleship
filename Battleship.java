// -----------------------------------------------------
// Assignment 1
// © Adeyemi Oluwamosope
// Written by: Adeyemi Oluwamosope #40293741
// -----------------------------------------------------

package battleship;

import java.util.Scanner;

public class Battleship {
    public static void main(String args[]) {
        System.out.println("Hi, lets play battleship");

        Board board = new Board();
        board.printBoard();

        board.setHumanShips();
        board.setHumanGrenades();

        board.setComputerElements('S', "Computer"); // set computer ships
        board.setComputerElements('G', "Computer"); // set computer grenade

        board.play();

        board.printBoard();
    }
}

class Board {
    private Cell[][] battleshipBoard = new Cell[8][8];
    Scanner scanner = new Scanner(System.in);
    private int humanShipsSank = 0;
    private int computerShipsSank = 0;
    private int numberOfPlaysPerTurn = 1;

    private String lastPlayer; // the last person to play in a turn
    private int currentPlayer = 0; // 0 for Human, 1 for Computer

    // private boolean isHumanTurn;
    private boolean humanHasBan;
    private boolean computerHasBan;

    private int[] formatUserInout(String input) {
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

    public void printBoard() {
        for (int i = 0; i < battleshipBoard.length; i++) {
            for (int j = 0; j < battleshipBoard[i].length; j++) {
                if (battleshipBoard[i][j] == null || battleshipBoard[i][j].getHasBeenCalled() == false) {
                    System.out.print("_ ");
                } else if (battleshipBoard[i][j] != null && battleshipBoard[i][j].getHasBeenCalled() == true) {
                    System.out.print(battleshipBoard[i][j].getType() + " ");
                }
                // System.out.print(battleshipBoard[i][j] != null ?
                // battleshipBoard[i][j].getHasBeenCalled() == true ?
                // battleshipBoard[i][j].getType() : "*" : "-");
            }
            System.out.println();
        }
    }

    public void setComputerElements(char type, String owner) {
        System.out.println("Computer setting Grenades and Ships");
        int counter = 0;

        int row = (int) Math.round(Math.random() * 7);
        int column = (int) Math.round(Math.random() * 7);

        System.out.println("random coordinate " + row + " : " + column);
        int numberOfElements = 0;

        if (type == 'S' || type == 's')
            numberOfElements = 6;
        if (type == 'G' || type == 'g')
            numberOfElements = 4;

        System.out.println(numberOfElements);

        while (counter < numberOfElements) {
            if (battleshipBoard[row][column] != null) {
                System.out.println("Changing random coordinate");
                row = (int) Math.round(Math.random() * 7);
                column = (int) Math.round(Math.random() * 7);

                System.out.println("random coordinate " + row + " : " + column);
            } else {
                System.out.println("Position set in coordinate" + row + " : " + column);
                battleshipBoard[row][column] = new Cell(type, owner, row, column);
                counter++;
            }
        }

        // System.out.println("OK, the computer placed its ships and grenades at random.
        // Let’s play.");
    }

    public void setHumanShips() {
        int humanShipCounter = 0;

        // Place human ships
        while (humanShipCounter < 6) {
            System.out.println("Enter the coordinates of your ship #" + (humanShipCounter + 1));

            String input = scanner.nextLine();

            int[] coordinate = this.formatUserInout(input);
            int row = coordinate[0];
            int column = coordinate[1];

            if (column > 7 || row > 7)
                System.out.println("sorry, coordinates outside the grid. try again.");
            else {
                System.out.println(row + " : " + column);

                if (battleshipBoard[row][column] != null) {
                    System.out.println("sorry, coordinates already used. try again.");
                } else {
                    battleshipBoard[row][column] = new Cell('s', "Human", row, column);
                    humanShipCounter++;
                }
            }
        }
    }

    public void setHumanGrenades() {
        int humanGrenadeCounter = 0;

        while (humanGrenadeCounter < 4) {
            System.out.println("Enter the coordinates of your grenades #" + (humanGrenadeCounter + 1));

            String input = scanner.nextLine();

            int[] coordinate = this.formatUserInout(input);
            int row = coordinate[0];
            int column = coordinate[1];

            if (column > 7 || row > 7)
                System.out.println("sorry, coordinates outside the grid. try again.");
            else {
                System.out.println(row + " : " + column);

                if (battleshipBoard[row][column] != null) {
                    System.out.println("sorry, coordinates already used. try again.");
                } else {
                    battleshipBoard[row][column] = new Cell('g', "Human", row, column);
                    humanGrenadeCounter++;
                }
            }
        }
    }

    private int humanGamePlay() {
        // Human's game play
        System.out.println("position of your rocket: ");
        String input = scanner.nextLine();

        int[] coordinate = this.formatUserInout(input);
        int row = coordinate[0];
        int column = coordinate[1];

        if (this.battleshipBoard[row][column] == null && column < 8 && row < 8) {
            // Position is valid but no ship or grenade was put there

            this.battleshipBoard[row][column] = new Cell('*', "None", row, column);
            this.battleshipBoard[row][column].setHasBeenCalled(true);

        } else if (this.battleshipBoard[row][column].getHasBeenCalled() == false) {
            // ship hit
            if (this.battleshipBoard[row][column].getType() == 'S'
                    || this.battleshipBoard[row][column].getType() == 's') {
                this.battleshipBoard[row][column].setHasBeenCalled(true);

                System.out.println("ship hit.");
                this.humanShipsSank++; // increase human's score
                return 0;

            } else if (this.battleshipBoard[row][column].getType() == 'G'
                    || this.battleshipBoard[row][column].getType() == 'g') {
                System.out.println("boom! grenade. ");
                this.battleshipBoard[row][column].setHasBeenCalled(true);

                return 2;
            }
        } else {
            System.out.println("nothing. position already called");
            return 0;
        }
        return 0;
    }

    private int computerGamePlay() {
        int row = (int) Math.round(Math.random() * 7);
        int column = (int) Math.round(Math.random() * 7);
        System.out.println("position of my rocket: " + this.formatComputerColumn(column) + row);

        if (this.battleshipBoard[row][column] == null && column < 8 && row < 8) {
            // Position is valid but no ship or grenade was put there

            this.battleshipBoard[row][column] = new Cell('*', "None", row, column);
            this.battleshipBoard[row][column].setHasBeenCalled(true);

            System.out.println("nothing.");
        } else if (this.battleshipBoard[row][column].getHasBeenCalled() == false) {
            // ship hit
            if (this.battleshipBoard[row][column].getType() == 'S'
                    || this.battleshipBoard[row][column].getType() == 's') {
                this.battleshipBoard[row][column].setHasBeenCalled(true);
                System.out.println("ship hit.");

                this.computerShipsSank++; // increase computer's score
                return 0;
            } else if (this.battleshipBoard[row][column].getType() == 'G'
                    || this.battleshipBoard[row][column].getType() == 'g') {
                System.out.println("boom! grenade. ");
                this.battleshipBoard[row][column].setHasBeenCalled(true);
                return 2;
            }
        } else {
            System.out.println("position already called");
            return 0;
        }

        return 0;
    }

    public void play() {
        while (this.humanShipsSank < 6 && this.computerShipsSank < 6) {
            if (currentPlayer == 0) {
                int nextPlays = 0;

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
                this.numberOfPlaysPerTurn = nextPlays;
            } else {

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
            System.out.print(" You Win!");
        } else {
            System.out.print(" You Win!");
        }
    }

}

class Cell {
    private char type;
    private String owner;
    private boolean hasBeenCalled;
    private int[] coordinate = new int[2]; // position in cell

    public Cell(char type, String owner, int row, int column) {
        this.type = type;
        this.owner = owner;
        this.coordinate[0] = row;
        this.coordinate[1] = column;
        this.hasBeenCalled = false;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean getHasBeenCalled() {
        return hasBeenCalled;
    }

    public void setHasBeenCalled(boolean hasBeenCalled) {
        this.hasBeenCalled = hasBeenCalled;
    }

    public void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }
}
