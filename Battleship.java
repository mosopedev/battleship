/**
 * COMP 6481 (Winter 2024) Assignment #1
 * Due Date: Sunday, February 11, 2024 by 11:59PM
 * 
 * © Oluwamosope Adeyemi. ID: 40293741
 * © Jerome Kithinji. ID: 40280348
 * 
 * Written by: 
 * Jerome Kithinji. ID: 40280348
 * Oluwamosope Adeyemi. ID: 40293741
 */

package battleship;

/**
 * The Battleship class is the main class for the battleship board game. It
 * creates an object of the Board and calls the respective methods to place
 * human's and computer's ships and grenades on the board.
 * The game is played between a human and the computer.
 * 
 * The board is an 8 by 8 grid. Each player is required to place 6 ships and 4
 * grenades across the board.
 * Once all ships and grenades have been placed the game starts, and each player
 * is given by default 1 play per game turn.
 * 
 * Once the game starts players take turns launching a rocket at a coordinate
 * (row, column) on the board.
 * If the rocket hits a ship, the ship skins. However, if the rocket hits a
 * grenade the player loses his chance to play in the next turn.
 * 
 * The goal of the game is to sink all of your opponent’s ships before your opponent sinks yours. 
 */

public class Battleship {
    /**
     * @param args
     */
    public static void main(String args[]) {
        System.out.println("Hi, lets play battleship");

        Board board = new Board();
        board.printBoard();

        board.setHumanShips();
        board.setHumanGrenades();

        board.setComputerShips();
        board.setComputerGrenades();

        System.out.println("OK, the computer placed its ships and grenades at random. Let’s play.");

        board.startGame();
    }
}